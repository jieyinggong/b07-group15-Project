package com.example.b07project.Dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.b07project.R;
import com.example.b07project.Student_Event.StudentScheduledEventActivity;
import com.example.b07project.Student_Event.StudentUpcomingEventActivity;
import com.example.b07project.Student_Announcement.AnnouncementActivity;
import com.example.b07project.Student_Complaints.StudentComplaint;
import com.example.b07project.Student_Login_MVP.StudentLoginView;
import com.example.b07project.Student_POSt_checker.CMSpostMain;


public class Student_dashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_starter_dashboard);

        //click postchecker

        findViewById(R.id.post_checker).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //go to submit fragment
                Intent intent = new Intent(Student_dashboardActivity.this, CMSpostMain.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.announcement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Student_dashboardActivity.this, AnnouncementActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.events).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //go to login fragment
                Intent intent = new Intent(Student_dashboardActivity.this, StudentUpcomingEventActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.scheduled_event).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //go to submit fragment
                Intent intent = new Intent(Student_dashboardActivity.this, StudentScheduledEventActivity.class);
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
                Intent intent = new Intent(Student_dashboardActivity.this, StudentLoginView.class);
                startActivity(intent);
            }
    });
}}