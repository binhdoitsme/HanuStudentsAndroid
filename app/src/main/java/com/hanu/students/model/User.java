package com.hanu.students.model;

import lombok.Getter;

@Getter
public class User {
    private final String username;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
