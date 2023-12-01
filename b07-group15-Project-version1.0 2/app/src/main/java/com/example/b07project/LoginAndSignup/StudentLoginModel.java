package com.example.b07project.LoginAndSignup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;

public class StudentLoginModel {
    public interface StudentLoginViewInterface {
        void onLoginSuccess(String username);
        void onLoginFailure();
    }
    private DatabaseReference mDatabase;

    public StudentLoginModel() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void authenticateUser(String username, String password, OnUserAuthenticationListener listener) {
        String hashedPassword = hashPassword(password);

        mDatabase.child("students").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Student student = dataSnapshot.getValue(Student.class);
                if (student != null && student.passwordHash.equals(hashedPassword)) {
                    listener.onSuccess(username);
                } else {
                    listener.onFailure();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
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


    public interface OnUserAuthenticationListener {
        void onSuccess(String username);
        void onFailure();
    }
}
