package com.example.b07project.LoginAndSignup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.b07project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

        mDatabase = FirebaseDatabase.getInstance().getReference();

        usernameField = findViewById(R.id.usernameEditText);
        passwordField = findViewById(R.id.passwordEditText);
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
        String username = usernameField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_LONG).show();
            return;
        }

        String hashedPassword = hashPassword(password);

        mDatabase.child("students").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Student student = dataSnapshot.getValue(Student.class);
                if (student != null && student.passwordHash.equals(hashedPassword)) {
                    Toast.makeText(StudentLoginActivity.this, "Authentication successful.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(StudentLoginActivity.this, Student_dashboardActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(StudentLoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(StudentLoginActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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