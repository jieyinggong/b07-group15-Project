package com.example.b07project.LoginAndSignup;

public class Student {
    public String username;
    public String fullName;
    public String passwordHash;

    public Student() {
    }

    public Student(String username, String fullName, String passwordHash) {
        this.username = username;
        this.fullName = fullName;
        this.passwordHash = passwordHash;
    }
}