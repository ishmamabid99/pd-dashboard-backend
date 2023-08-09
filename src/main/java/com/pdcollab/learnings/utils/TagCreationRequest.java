package com.pdcollab.learnings.utils;

import com.pdcollab.learnings.model.Tag;

public class TagCreationRequest {
    private Tag tag;
    private String proficiency;

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }
}
