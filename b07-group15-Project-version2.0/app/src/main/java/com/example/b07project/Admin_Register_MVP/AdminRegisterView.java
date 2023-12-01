package com.example.b07project.Admin_Register_MVP;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.b07project.Admin_Login_MVP.AdminLoginView;
import com.example.b07project.R;
import com.google.firebase.database.FirebaseDatabase;

public class AdminRegisterView extends AppCompatActivity implements AdminRegisterModel.AdminRegisterInterface {
    private EditText usernameField, nameField, passwordField;
    private AdminRegisterPresenter presenter;

    private AdminRegisterModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_stater_register);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        model = new AdminRegisterModel(firebaseDatabase);

        // Create the presenter with the view (this) and the model
        presenter = new AdminRegisterPresenter(this, model);

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
        Toast.makeText(this, "Registration Success!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, AdminLoginView.class));
    }

    @Override
    public void showRegistrationFailure() {
        Toast.makeText(this, "Registration failed.", Toast.LENGTH_SHORT).show();
    }
}