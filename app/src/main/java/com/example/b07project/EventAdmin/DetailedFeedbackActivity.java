package com.example.b07project.EventAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.b07project.R;

import org.w3c.dom.Text;

public class DetailedFeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_feedback_admin);

        String rate = String.valueOf(getIntent().getStringExtra("RATE"));
        String comment = getIntent().getStringExtra("COMMENT");

        TextView ratingTextView = findViewById(R.id.rating_text_view);
        if (ratingTextView != null) {
            ratingTextView.setText("rate");
        } else {
            Log.e("DetailActivity", "TextView not found");
        }
        TextView commentTextView = findViewById(R.id.comment_text_view);
        commentTextView.setText(comment);

        findViewById(R.id.back_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 结束当前Activity
            }
        });
    }
}