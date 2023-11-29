package com.example.b07project.announcement_student_view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.b07project.R;
import com.example.b07project.admin_post_announcement.ButtonLinksToPostAnnouncePage;
import com.example.b07project.announcement_student_view.AnnouncementStudentActivity;

//***********************not very sure if the bottom is working*****************

public class ButtonLinksToAnnouncePage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard_fragment);

        findViewById(R.id.announcement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ButtonLinksToAnnouncePage.this, AnnouncementStudentActivity.class);
                startActivity(intent);
            }
        });
    }
}

//**********************************ends*****************************************
