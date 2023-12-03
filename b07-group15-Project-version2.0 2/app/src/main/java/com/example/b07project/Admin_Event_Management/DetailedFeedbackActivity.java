package com.example.b07project.Admin_Event_Management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.b07project.R;
import com.example.b07project.main.SetToolbar;

public class DetailedFeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_event_detailed_feedback_comment_display);

        String rate =getIntent().getStringExtra("RATE");
        String comment = getIntent().getStringExtra("COMMENT");

        TextView ratingTextView = findViewById(R.id.rating_text_view);
        if (ratingTextView != null) {
            ratingTextView.setText(rate);
        } else {
            Log.e("DetailActivity", "TextView not found");
        }
        TextView commentTextView = findViewById(R.id.comment_text_view);
        commentTextView.setText(comment);

        Toolbar toolbar = findViewById(R.id.back_bar);
        SetToolbar.setBackFinish(this, toolbar);
    }
}