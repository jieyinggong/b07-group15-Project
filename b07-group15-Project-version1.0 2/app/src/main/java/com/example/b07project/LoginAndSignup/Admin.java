package com.example.b07project.LoginAndSignup;

public class Admin {
    public String username;
    public String fullName; // Optional for login, but should match the Admin class in AdminRegisterActivity
    public String passwordHash;

    public Admin() {
        // Default constructor is needed to map Firebase data to User object
    }

    public Admin(String username, String fullName, String passwordHash) {
        this.username = username;
        this.fullName = fullName;
        this.passwordHash = passwordHash;
    }
}