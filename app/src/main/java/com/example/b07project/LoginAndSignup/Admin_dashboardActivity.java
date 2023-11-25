package com.example.b07project.LoginAndSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.b07project.R;
import com.example.b07project.complaintsadminview.ComplaintAdminActivity;

public class Admin_dashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard_fragment);

        findViewById(R.id.view_complaint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to submit fragment
                Intent intent = new Intent(Admin_dashboardActivity.this, ComplaintAdminActivity.class);
                startActivity(intent);

            }
        });

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //go to login fragment
                Intent intent = new Intent(Admin_dashboardActivity.this, AdminLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}