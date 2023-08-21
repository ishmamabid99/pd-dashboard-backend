package com.pdcollab.blogs.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pdcollab.comments.model.BlogComment;
import com.pdcollab.learnings.model.Tag;
import com.pdcollab.auth.model.User;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "blogs")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;

    @Column(name = "date")
    private Date date;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private List<BlogComment> blogComments;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private List<BlogImage> images;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "blog_tags", joinColumns = {@JoinColumn(name = "blog_id"),}, inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<Tag> tags;

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<BlogImage> getImages() {
        return images;
    }

    public void setImages(List<BlogImage> images) {
        this.images = images;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<BlogComment> getBlogComments() {
        return blogComments;
    }

    public void setBlogComments(List<BlogComment> blogComments) {
        this.blogComments = blogComments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
