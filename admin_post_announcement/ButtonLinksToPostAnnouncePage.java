package com.example.b07project.admin_post_announcement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.b07project.R;
import com.example.b07project.announcement_student_view.AnnouncementStudentActivity;

//***********************not very sure if the bottom is working*****************
public class ButtonLinksToPostAnnouncePage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard_fragment);

        findViewById(R.id.new_announce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ButtonLinksToPostAnnouncePage.this, AnnouncementStudentActivity.class);
                startActivity(intent);
            }
        });
    }

}

//**********************************ends*****************************************
