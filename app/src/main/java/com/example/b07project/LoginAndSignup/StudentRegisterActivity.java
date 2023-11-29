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

import java.security.MessageDigest;

public class StudentRegisterActivity extends AppCompatActivity {

    private EditText usernameField, fullNameField, passwordField;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_register_fragment);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        usernameField = findViewById(R.id.usernameEditText);
        fullNameField = findViewById(R.id.fullNameEditText);
        passwordField = findViewById(R.id.passwordEditText);

        findViewById(R.id.back_bar).setOnClickListener(v -> finish());

        findViewById(R.id.sign_up_button).setOnClickListener(v -> registerStudent());
    }

    private void registerStudent() {
        String username = usernameField.getText().toString().trim();
        String fullName = fullNameField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        if (username.isEmpty() || fullName.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_LONG).show();
            return;
        }

        // Check if username is unique
        checkUsernameUnique(username, fullName, password);
    }

    private void checkUsernameUnique(String username, String fullName, String password) {
        mDatabase.child("students").child(username).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    Toast.makeText(StudentRegisterActivity.this, "Username already taken. Please choose another.", Toast.LENGTH_LONG).show();
                } else {
                    // Username is unique, proceed to register the student
                    registerNewStudent(username, fullName, password);
                }
            } else {
                Toast.makeText(StudentRegisterActivity.this, "Failed to check username uniqueness.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerNewStudent(String username, String fullName, String password) {
        String hashedPassword = hashPassword(password);

        Student student = new Student(username, fullName, hashedPassword);
        mDatabase.child("students").child(username).setValue(student)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(StudentRegisterActivity.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(StudentRegisterActivity.this, Student_dashboardActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(StudentRegisterActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
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

}
