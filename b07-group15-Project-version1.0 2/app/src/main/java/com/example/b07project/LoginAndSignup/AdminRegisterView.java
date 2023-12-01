package com.example.b07project.LoginAndSignup;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.b07project.R;

public class AdminRegisterView extends AppCompatActivity implements AdminRegisterModel.AdminRegisterInterface {
    private EditText usernameField, nameField, passwordField;
    private AdminRegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_register_fragment);

        presenter = new AdminRegisterPresenter(this);

        usernameField = findViewById(R.id.usernameEditText);
        nameField = findViewById(R.id.fullNameEditText);
        passwordField = findViewById(R.id.passwordEditText);

        findViewById(R.id.sign_up_button).setOnClickListener(view ->
                presenter.registerAdmin(
                        usernameField.getText().toString().trim(),
                        nameField.getText().toString().trim(),
                        passwordField.getText().toString().trim()
                ));
        findViewById(R.id.back_bar).setOnClickListener(v -> finish());
    }

    @Override
    public void showUsernameTaken() {
        Toast.makeText(this, "Username already taken. Please choose another.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showRegistrationSuccess() {
        startActivity(new Intent(this, Admin_dashboardActivity.class));
    }

    @Override
    public void showRegistrationFailure() {
        Toast.makeText(this, "Registration failed.", Toast.LENGTH_SHORT).show();
    }
}
