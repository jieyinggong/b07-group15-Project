package com.example.b07project.Student_Register_MVP;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;



import com.example.b07project.R;
import com.example.b07project.Student_Login_MVP.StudentLoginView;
import com.google.firebase.database.FirebaseDatabase;

public class StudentRegisterView extends AppCompatActivity implements StudentRegisterModel.StudentRegisterViewInterface {
    private EditText usernameField, fullNameField, passwordField;
    private StudentRegisterPresenter presenter;
    private StudentRegisterModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_starter_register);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        model = new StudentRegisterModel(firebaseDatabase);
        presenter = new StudentRegisterPresenter(this,model);

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
        Toast.makeText(this, "Registration Success!.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, StudentLoginView.class));
    }

    @Override
    public void showRegistrationFailure() {
        Toast.makeText(this, "Registration failed.", Toast.LENGTH_SHORT).show();
    }
}

