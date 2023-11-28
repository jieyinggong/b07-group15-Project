package com.example.b07project.complaintsadminview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.b07project.R;

public class DetailedItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_item);

        String subject = getIntent().getStringExtra("SUBJECT");
        String content = getIntent().getStringExtra("CONTENT");
//        Log.d("DetailedItemActivity", "Received subject: " + subject + ", content: " + content);
        TextView subjectTextView = findViewById(R.id.subject_text_view);
        if (subjectTextView != null) {
            subjectTextView.setText("Some Text");
        } else {
            Log.e("DetailActivity", "TextView not found");
        }
        TextView contentTextView = findViewById(R.id.content_text_view);

        subjectTextView.setText(subject);
        contentTextView.setText(content);

        Button returnButton = findViewById(R.id.return_button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 结束当前Activity
            }
        });
    }
}