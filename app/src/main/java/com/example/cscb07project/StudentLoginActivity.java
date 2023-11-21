package com.example.cscb07project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StudentLoginActivity extends AppCompatActivity {

    private EditText usernameField, passwordField;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_login_fragment);

        mDatabase = FirebaseDatabase.getInstance().getReference("students");

        // Initialize EditText fields
        usernameField = findViewById(R.id.usernameEditText); // Replace with actual ID from your layout
        passwordField = findViewById(R.id.passwordEditText); // Replace with actual ID from your layout

        // Click on sign up
        findViewById(R.id.sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to register fragment
                Intent intent = new Intent(StudentLoginActivity.this, StudentRegisterActivity.class);
                startActivity(intent);
            }
        });

        // Click on sign in
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Attempt to sign in
                signIn();
            }
        });

        // Click on "An admin? Click here"
        findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to admin login activity
                Intent intent = new Intent(StudentLoginActivity.this, AdminLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void signIn() {
        final String username = usernameField.getText().toString().trim();
        final String password = passwordField.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(StudentLoginActivity.this, "Please enter both username and password", Toast.LENGTH_LONG).show();
            return;
        }

        // Hash the password to compare with the database
        final String hashedPassword = hashPassword(password);

        // Query the database for the username
        mDatabase.child("students").child(username).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Student student = task.getResult().getValue(Student.class);
                if (student != null && student.passwordHash.equals(hashedPassword)) {
                    // Correct password
                    Toast.makeText(StudentLoginActivity.this, "Login successful.", Toast.LENGTH_SHORT).show();
                    // Navigate to the Student Dashboard
                    Intent intent = new Intent(StudentLoginActivity.this, Student_dashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Incorrect password or username doesn't exist
                    Toast.makeText(StudentLoginActivity.this, "Login failed. Incorrect username or password.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(StudentLoginActivity.this, "Failed to query database.", Toast.LENGTH_LONG).show();
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
    // Student class (make sure this matches the structure you're using in Firebase)
    public static class Student {
        public String username;
        public String fullName;
        public String passwordHash; // Make sure this name matches with what you use in Firebase

        // Required no-argument constructor
        public Student() {
        }
    }
}