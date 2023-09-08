package com.pdcollab.auth.security.jwt;

public class JwtDataModel {
    private String username;
    private Long id;


    public JwtDataModel(){

    }
    public JwtDataModel(String username, Long id) {
        this.username = username;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "JwtDataModel [username-" + this.getUsername() + ", id=" + this.getId();
    }
}
