package com.example.cscb07project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Student_dashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_dashboard_fragment);

        //click submit complaints
        findViewById(R.id.submit_complaint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to submit fragment
                Intent intent = new Intent(Student_dashboardActivity.this, Student_complaintsActivity.class);
                startActivity(intent);

            }
        });

        //click log out
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to login fragment
                Intent intent = new Intent(Student_dashboardActivity.this, LoginActivity.class);
                startActivity(intent);

            }
    });
}}