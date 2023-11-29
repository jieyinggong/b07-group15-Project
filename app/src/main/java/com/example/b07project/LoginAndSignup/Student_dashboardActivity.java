package com.example.b07project.LoginAndSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.b07project.R;
import com.example.b07project.ScheduledEvents_studentview.StudentScheduledEvent;
import com.example.b07project.ScheduledEvents_studentview.StudentUpcomingEvent;
import com.example.b07project.complaints_studentview.StudentComplaint;
import com.example.b07project.complaints_studentview.Student_complaintsActivity;
import com.example.b07project.postcheckerfixed.CMSpostMain;

public class Student_dashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_dashboard_fragment);

        //click postchecker

        findViewById(R.id.post_checker).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //go to submit fragment
                Intent intent = new Intent(Student_dashboardActivity.this, CMSpostMain.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.events).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //go to login fragment
                Intent intent = new Intent(Student_dashboardActivity.this, StudentUpcomingEvent.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.scheduled_event).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //go to submit fragment
                Intent intent = new Intent(Student_dashboardActivity.this, StudentScheduledEvent.class);
                startActivity(intent);
            }
        });

        //click submit complaints
        findViewById(R.id.submit_complaint).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //go to submit fragment
                Intent intent = new Intent(Student_dashboardActivity.this, StudentComplaint.class);
                startActivity(intent);
            }
        });


        //click log out
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //go to login fragment
                Intent intent = new Intent(Student_dashboardActivity.this, StudentLoginActivity.class);
                startActivity(intent);
            }
    });
}}