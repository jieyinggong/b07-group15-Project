package com.example.b07project.Admin_Complaints;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.b07project.R;
import com.example.b07project.main.SetToolbar;

public class DetailedItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_detailed_complaint_item);

        String subject = getIntent().getStringExtra("SUBJECT");
        String content = getIntent().getStringExtra("CONTENT");
        TextView subjectTextView = findViewById(R.id.subject_text_view);
        if (subjectTextView != null) {
            subjectTextView.setText("Some Text");
        } else {
            Log.e("DetailActivity", "TextView not found");
        }
        TextView contentTextView = findViewById(R.id.content_text_view);
        if (subjectTextView != null) {
            subjectTextView.setText(subject);
        }
        if(contentTextView != null) {
            contentTextView.setText(content);
        }

        Toolbar toolbar = findViewById(R.id.back_bar);
        SetToolbar.setBackFinish(this, toolbar);
    }
}