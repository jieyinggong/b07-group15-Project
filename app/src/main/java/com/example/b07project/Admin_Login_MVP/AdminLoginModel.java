package com.example.b07project.Admin_Login_MVP;

import com.example.b07project.main.Admin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AdminLoginModel {
    private DatabaseReference mDatabase;

    public AdminLoginModel() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void authenticateAdmin(String username, String password, OnAdminAuthenticationListener listener) {
        String hashedPassword = hashPassword(password);

        mDatabase.child("admins").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Admin admin = dataSnapshot.getValue(Admin.class);
                if (admin != null && admin.passwordHash.equals(hashedPassword)) {
                    listener.onSuccess();
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
            digest.reset();
            digest.update(password.getBytes("utf8"));
            return String.format("%064x", new BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException("Unable to hash password", e);
        }
    }

    public interface OnAdminAuthenticationListener {
        void onSuccess();
        void onFailure();
    }
}
