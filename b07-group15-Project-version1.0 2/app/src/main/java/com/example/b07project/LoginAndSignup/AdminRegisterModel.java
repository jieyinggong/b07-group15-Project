package com.example.b07project.LoginAndSignup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AdminRegisterModel {
    public interface AdminRegisterInterface {
        void showUsernameTaken();
        void showRegistrationSuccess();
        void showRegistrationFailure();
    }
    private DatabaseReference mDatabase;

    public AdminRegisterModel() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void checkUsernameUnique(String username, OnUsernameCheckListener listener) {
        mDatabase.child("admins").child(username).get().addOnCompleteListener(task -> {
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

    public void registerNewAdmin(String username, String fullName, String password, OnRegistrationCompleteListener listener) {
        String hashedPassword = hashPassword(password);
        Admin admin = new Admin(username, fullName, hashedPassword);
        mDatabase.child("admins").child(username).setValue(admin)
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
            digest.reset();
            digest.update(password.getBytes("utf8"));
            return String.format("%064x", new BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException("Unable to hash password", e);
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
