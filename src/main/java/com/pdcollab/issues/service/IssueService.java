package com.pdcollab.issues.service;

import com.pdcollab.issues.model.Issue;
import com.pdcollab.issues.model.IssueImage;
import com.pdcollab.issues.repository.IssueImageRepository;
import com.pdcollab.issues.repository.IssueRepository;
import com.pdcollab.learnings.model.Tag;
import com.pdcollab.learnings.repository.TagRepository;
import com.pdcollab.users.model.User;
import com.pdcollab.users.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class IssueService {

    @Value("${upload.base-dir}")
    private String baseUploadDir;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    private final IssueImageRepository issueImageRepository;

    private final TagRepository tagRepository;

    @Autowired
    public IssueService(IssueRepository issueRepository, UserRepository userRepository, IssueImageRepository issueImageRepository, TagRepository tagRepository) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
        this.issueImageRepository = issueImageRepository;
        this.tagRepository = tagRepository;
    }


    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    @Transactional
    public Issue createIssueForUser(long userId, String title, String content, List<String> tags, MultipartFile[] imageFiles) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found!!!"));
        Issue issue = new Issue();
        issue.setTitle(title);
        issue.setContent(content);
        Date date = new Date();
        issue.setDate(date);
        issue.setUser(user);
        if (imageFiles != null) {
            List<IssueImage> issueImages = new LinkedList<>();
            for (MultipartFile imageFile : imageFiles) {
                IssueImage issueImage = new IssueImage();
                issueImage.setIssue(issue);
                issueImage.setFileName(imageFile.getOriginalFilename());
                File directory = new File(baseUploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                String filePath = baseUploadDir + "/" + imageFile.getOriginalFilename();
                Files.copy(imageFile.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
                issueImages.add(issueImage);
                issueImageRepository.save(issueImage);
            }
            issue.setIssueImages(issueImages);
        }
        issueRepository.save(issue);
        List<Tag> newTags = null;
        for (String tagName : tags) {
            Tag existingTag = tagRepository.findTagByTitle(tagName);
            newTags = new LinkedList<>();
            if (existingTag == null || existingTag.getTitle().isEmpty()) {
                Tag newTag = new Tag();
                newTag.setTitle(tagName);
                List<Issue> issues = new LinkedList<>();
                issues.add(issue);
                newTag.setIssues(issues);
                tagRepository.save(newTag);
            } else {
                List<Issue> issues = existingTag.getIssues();
                issues.add(issue);
                tagRepository.save(existingTag);
                newTags.add(existingTag);
            }
        }
        issue.setTags(newTags);
        return issueRepository.save(issue);
    }

    public UrlResource getImageResource(String fileName) throws IOException {
        Path file = Paths.get(baseUploadDir).resolve(fileName);
        UrlResource resource = new UrlResource(file.toUri());
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else return null;
    }

    public List<Issue> getIssueByUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found!!!"));
        return user.getIssues();
    }

    public Issue updateIssue(long id, String title, String content) {
        Issue existingIssue = issueRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Issue not found"));
        existingIssue.setTitle(title);
        existingIssue.setContent(content);
        return issueRepository.save(existingIssue);
    }

}
