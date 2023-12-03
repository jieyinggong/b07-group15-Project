package com.example.b07project.Admin_Login_MVP;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.b07project.Admin_Register_MVP.AdminRegisterView;
import com.example.b07project.Dashboard.Admin_dashboardActivity;
import com.example.b07project.R;
import com.example.b07project.Student_Login_MVP.StudentLoginView;
import com.google.firebase.database.FirebaseDatabase;

public class AdminLoginView extends AppCompatActivity implements AdminLoginModel.AdminLoginViewInterface {
    private EditText usernameField, passwordField;
    private Toolbar toolbar;
    private AdminLoginPresenter presenter;
    private AdminLoginModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_stater_login);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        model = new AdminLoginModel(firebaseDatabase);

        presenter = new AdminLoginPresenter( this,model);

        usernameField = findViewById(R.id.usernameEditText);
        passwordField = findViewById(R.id.passwordEditText);
        toolbar = findViewById(R.id.back_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> startActivity(new Intent(this, StudentLoginView.class)));

        findViewById(R.id.sign_in_button).setOnClickListener(view ->
                presenter.attemptLogin(usernameField.getText().toString(), passwordField.getText().toString()));

        findViewById(R.id.sign_up_button).setOnClickListener(view ->
                startActivity(new Intent(this, AdminRegisterView.class)));
    }


    public void onLoginSuccess() {
        Toast.makeText(this, "Authentication Success!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, Admin_dashboardActivity.class));
    }


    public void onLoginFailure() {
        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
    }

}


