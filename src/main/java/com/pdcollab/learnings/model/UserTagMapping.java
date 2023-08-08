package com.pdcollab.learnings.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_tag_mapping")
public class UserTagMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "proficiency")
    private String proficiency;


}
