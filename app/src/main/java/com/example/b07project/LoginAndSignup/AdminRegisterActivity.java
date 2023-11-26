package com.example.b07project.LoginAndSignup;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.b07project.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AdminRegisterActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private EditText usernameField, nameField, passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_register_fragment);

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
    }

    private void registerUser() {
        String username = usernameField.getText().toString().trim();
        String fullName = nameField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        // Basic validation
        if(username.isEmpty() || fullName.isEmpty() || password.isEmpty()) {
            Toast.makeText(AdminRegisterActivity.this, "Please fill in all fields", Toast.LENGTH_LONG).show();
            return;
        }

        // Hash the password
        String hashedPassword = hashPassword(password);

        // Store user data in Realtime Database
        Admin admin = new Admin(username, fullName, hashedPassword);
        mDatabase.child("admins").child(username).setValue(admin)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Registration success
                        Toast.makeText(AdminRegisterActivity.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdminRegisterActivity.this, Admin_dashboardActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Registration failure
                        Toast.makeText(AdminRegisterActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
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

}