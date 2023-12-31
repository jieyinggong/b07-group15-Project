package com.example.b07project.origin_login_and_signup.Student_Starter;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.b07project.Dashboard.Admin_dashboardActivity;
import com.example.b07project.R;
import com.example.b07project.main.Admin;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AdminRegisterActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private EditText usernameField, nameField, passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_stater_register);

        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Link the EditText fields
        usernameField = findViewById(R.id.usernameEditText);
        nameField = findViewById(R.id.fullNameEditText);
        passwordField = findViewById(R.id.passwordEditText);

        // Add listener to the sign up button
        findViewById(R.id.sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        findViewById(R.id.back_bar).setOnClickListener(v -> finish());
    }

    private void registerUser() {
        String username = usernameField.getText().toString().trim();
        String fullName = nameField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        if (username.isEmpty() || fullName.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_LONG).show();
            return;
        }

        // Check if username is unique
        checkUsernameUnique(username, fullName, password);
    }

    private void checkUsernameUnique(String username, String fullName, String password) {
        mDatabase.child("admins").child(username).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    Toast.makeText(AdminRegisterActivity.this, "Username already taken. Please choose another.", Toast.LENGTH_LONG).show();
                } else {
                    // Username is unique, proceed to register the admin
                    registerNewAdmin(username, fullName, password);
                }
            } else {
                Toast.makeText(AdminRegisterActivity.this, "Failed to check username uniqueness.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerNewAdmin(String username, String fullName, String password) {
        String hashedPassword = hashPassword(password);

        Admin admin = new Admin(username, fullName, hashedPassword);
        mDatabase.child("admins").child(username).setValue(admin)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(AdminRegisterActivity.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdminRegisterActivity.this, Admin_dashboardActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(AdminRegisterActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            digest.update(password.getBytes(StandardCharsets.UTF_8));
            return String.format("%064x", new BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to hash password", e);
        }
    }
}