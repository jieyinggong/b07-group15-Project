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

public class StudentRegisterActivity extends AppCompatActivity implements StudentRegisterView {
    private EditText usernameField, fullNameField, passwordField;
    private StudentRegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_register_fragment);

        presenter = new StudentRegisterPresenter(this);

        usernameField = findViewById(R.id.usernameEditText);
        fullNameField = findViewById(R.id.fullNameEditText);
        passwordField = findViewById(R.id.passwordEditText);

        findViewById(R.id.back_bar).setOnClickListener(v -> finish());
        findViewById(R.id.sign_up_button).setOnClickListener(v ->
                presenter.registerStudent(
                        usernameField.getText().toString().trim(),
                        fullNameField.getText().toString().trim(),
                        passwordField.getText().toString().trim()
                ));
    }

    @Override
    public void showUsernameTaken() {
        Toast.makeText(this, "Username already taken. Please choose another.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showRegistrationSuccess() {
        startActivity(new Intent(this, Student_dashboardActivity.class));
    }

    @Override
    public void showRegistrationFailure() {
        Toast.makeText(this, "Registration failed.", Toast.LENGTH_SHORT).show();
    }
}

