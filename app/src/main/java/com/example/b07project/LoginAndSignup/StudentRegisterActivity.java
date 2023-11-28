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

        mDatabase = FirebaseDatabase.getInstance().getReference("students");

        usernameField = findViewById(R.id.usernameEditText); // ID for the username input
        fullNameField = findViewById(R.id.fullNameEditText); // ID for the full name input
        passwordField = findViewById(R.id.passwordEditText); // ID for the password input

        findViewById(R.id.back_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the activity, returning to the previous screen
                finish();
            }
        });

        findViewById(R.id.sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerStudent();
            }
        });
    }

    private void registerStudent() {
        String username = usernameField.getText().toString().trim();
        String fullName = fullNameField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        // Basic validation
        if (username.isEmpty() || fullName.isEmpty() || password.isEmpty()) {
            Toast.makeText(StudentRegisterActivity.this, "Please fill in all fields", Toast.LENGTH_LONG).show();
            return;
        }

        // Hash the password
        String hashedPassword = hashPassword(password);

        // Store student data in Realtime Database
        Student student = new Student(username, fullName, hashedPassword);
        mDatabase.child(username).setValue(student)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(StudentRegisterActivity.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                        // Redirect to the Student dashboard activity
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
    static class Student {
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
}