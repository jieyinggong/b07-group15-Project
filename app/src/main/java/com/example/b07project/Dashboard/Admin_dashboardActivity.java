package com.example.b07project.Dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.b07project.Admin_Event_Management.EventHistoryActivity;
import com.example.b07project.Admin_Login_MVP.AdminLoginView;
import com.example.b07project.R;
import com.example.b07project.Admin_Announcement.AdminPostAnnouncement;
import com.example.b07project.Student_Announcement.AnnouncementActivity;
import com.example.b07project.Admin_Complaints.ComplaintAdminActivity;
import com.example.b07project.Admin_New_Event.EventAdminActivity;

public class Admin_dashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_stater_dashboard);

        findViewById(R.id.view_complaint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to submit fragment
                Intent intent = new Intent(Admin_dashboardActivity.this, ComplaintAdminActivity.class);
                startActivity(intent);

            }
        });
        findViewById(R.id.new_announce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_dashboardActivity.this, AdminPostAnnouncement.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.view_announcement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_dashboardActivity.this, AnnouncementActivity.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.new_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to submit fragment
                Intent intent = new Intent(Admin_dashboardActivity.this, EventAdminActivity.class);
                startActivity(intent);

            }
        });

        findViewById(R.id.events_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to submit fragment
                Intent intent = new Intent(Admin_dashboardActivity.this, EventHistoryActivity.class);
                startActivity(intent);

            }
        });

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //go to login fragment
                Intent intent = new Intent(Admin_dashboardActivity.this, AdminLoginView.class);
                startActivity(intent);
            }
        });


    }
}