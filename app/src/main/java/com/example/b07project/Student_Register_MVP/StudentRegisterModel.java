package com.example.b07project.Student_Register_MVP;

import com.example.b07project.main.Student;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;

public class StudentRegisterModel {
    private DatabaseReference mDatabase;

    public StudentRegisterModel() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void checkUsernameUnique(String username, OnUsernameCheckListener listener) {
        mDatabase.child("students").child(username).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    listener.onUsernameTaken();
                } else {
                    listener.onUsernameAvailable();
                }
            } else {
                listener.onFailure();
            }
        });
    }

    public void registerNewStudent(String username, String fullName, String password, OnRegistrationCompleteListener listener) {
        String hashedPassword = hashPassword(password);
        Student student = new Student(username, fullName, hashedPassword);
        mDatabase.child("students").child(username).setValue(student)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onSuccess();
                    } else {
                        listener.onFailure();
                    }
                });
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error hashing password", ex);
        }
    }

    public interface OnUsernameCheckListener {
        void onUsernameAvailable();
        void onUsernameTaken();
        void onFailure();
    }

    public interface OnRegistrationCompleteListener {
        void onSuccess();
        void onFailure();
    }
}
