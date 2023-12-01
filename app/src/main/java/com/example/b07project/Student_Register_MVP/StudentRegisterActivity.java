package com.example.b07project.Student_Register_MVP;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.b07project.Dashboard.Student_dashboardActivity;
import com.example.b07project.R;

public class StudentRegisterActivity extends AppCompatActivity implements StudentRegisterView {
    private EditText usernameField, fullNameField, passwordField;
    private StudentRegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_starter_register);

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

