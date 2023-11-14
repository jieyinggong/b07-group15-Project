package com.example.cscb07project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login_fragment);

        findViewById(R.id.back_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to student login page
                Intent intent = new Intent(AdminLoginActivity.this, StudentLoginActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to admin register page
                Intent intent = new Intent(AdminLoginActivity.this, AdminRegisterActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to admin dashboard page
                Intent intent = new Intent(AdminLoginActivity.this, Admin_dashboardActivity.class);
                startActivity(intent);
            }
        });
    }

}