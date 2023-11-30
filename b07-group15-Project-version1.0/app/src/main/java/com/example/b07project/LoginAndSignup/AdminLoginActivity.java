package com.example.b07project.LoginAndSignup;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import androidx.appcompat.widget.Toolbar;
import com.example.b07project.R;

public class AdminLoginActivity extends AppCompatActivity implements AdminLoginView {
    private EditText usernameField, passwordField;
    private AdminLoginPresenter presenter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login_fragment);

        presenter = new AdminLoginPresenter(this);

        usernameField = findViewById(R.id.usernameEditText);
        passwordField = findViewById(R.id.passwordEditText);
        toolbar = findViewById(R.id.back_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> startActivity(new Intent(this, StudentLoginActivity.class)));

        findViewById(R.id.sign_in_button).setOnClickListener(view -> presenter.attemptLogin(usernameField.getText().toString(), passwordField.getText().toString()));
        findViewById(R.id.sign_up_button).setOnClickListener(view -> startActivity(new Intent(this, AdminRegisterActivity.class)));
    }

    @Override
    public void onLoginSuccess() {
        startActivity(new Intent(this, Admin_dashboardActivity.class));
    }

    @Override
    public void onLoginFailure() {
        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
    }
}
