package com.example.cscb07project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_register_fragment);

        findViewById(R.id.back_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go back to admin register page
                Intent intent = new Intent(AdminRegisterActivity.this, AdminLoginActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go back to admin register page
                Intent intent = new Intent(AdminRegisterActivity.this, Admin_dashboardActivity.class);
                startActivity(intent);
            }
        });
    }

}