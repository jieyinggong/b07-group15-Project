package com.example.b07project.Student_Login_MVP;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.b07project.Admin_Login_MVP.AdminLoginActivity;
import com.example.b07project.Student_Register_MVP.StudentRegisterActivity;
import com.example.b07project.Dashboard.Student_dashboardActivity;
import com.example.b07project.R;

public class StudentLoginActivity extends AppCompatActivity implements StudentLoginView {

    private EditText usernameField, passwordField;
    private StudentLoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_starter_login);

        presenter = new StudentLoginPresenter(this);

        usernameField = findViewById(R.id.usernameEditText);
        passwordField = findViewById(R.id.passwordEditText);

        findViewById(R.id.sign_up_button).setOnClickListener(v -> startActivity(new Intent(this, StudentRegisterActivity.class)));
        findViewById(R.id.sign_in_button).setOnClickListener(v -> presenter.attemptLogin(usernameField.getText().toString(), passwordField.getText().toString()));
        findViewById(R.id.textView3).setOnClickListener(v -> startActivity(new Intent(this, AdminLoginActivity.class)));
    }

    @Override
    public void onLoginSuccess(String username) {
        SharedPreferences studentPreference = getSharedPreferences("StudentPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = studentPreference.edit();
        editor.putString("username", username);
        editor.apply();
        startActivity(new Intent(this, Student_dashboardActivity.class));
    }

    @Override
    public void onLoginFailure() {
        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
    }
}
