package com.pdcollab.learnings.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pdcollab.users.model.User;
import jakarta.persistence.*;

@Entity
@Table(name = "user_tag_mappings")
public class UserTagMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "tagname")
    private String tagName;

    @Column(name = "proficiency")
    private String proficiency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTagname() {
        return tagName;
    }

    public void setTagname(String tagName) {
        this.tagName = tagName;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
