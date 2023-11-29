package com.example.b07project.EventAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07project.R;
import com.example.b07project.ScheduledEvents_studentview.DetailedScheduledEventActivity;
import com.example.b07project.ScheduledEvents_studentview.Feedback_StudentActivity;
import com.example.b07project.dbOperation_Information.ResultCallback;
import com.example.b07project.dbOperation_Special.ReadSpecialItem;
import com.example.b07project.dbOperation_Special.ReadSpecialOperation;
import com.example.b07project.main.Event;
import com.example.b07project.main.Feedback;
import com.example.b07project.main.Information;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FeedbackAdmin extends AppCompatActivity {

    private List<Feedback> feedbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_admin);

        String eventid = getIntent().getStringExtra("EVENTID");
        String subject = getIntent().getStringExtra("SUBJECT");
        feedbacks = new ArrayList<>();

        TextView subjectTextView = findViewById(R.id.subject_text_view);
        if (subjectTextView != null) {
            subjectTextView.setText("Some Text");
        } else {
            Log.e("DetailActivity", "TextView not found");
        }
        TextView countTextView = findViewById(R.id.rate_num_text_view);
        TextView averageTextView = findViewById(R.id.average_text_view);

        ReadSpecialOperation readSpecialItem = new ReadSpecialItem();
        String path = "Feedback/" + eventid;

        readSpecialItem.listAllSpecial(path, Feedback.class, new ResultCallback<List<Information>>() {
            @Override
            public void onSuccess(List<Information> result) {
                feedbacks.clear();
                int totalrates = 0;
                for (Information info : result) {
                    if (info instanceof Feedback) {
                        Feedback feedback = (Feedback) info;
                        feedbacks.add(feedback);
                        totalrates += feedback.numericRating;
                    }
                }
                if (feedbacks.isEmpty()) {
                    // Show a toast message if there are no feedback
                    Toast.makeText(getApplicationContext(), "No Feedback!", Toast.LENGTH_SHORT).show();
                    countTextView.setText("No Rating yet!");
                    averageTextView.setText("No Rating yet!");
                } else {
                    // Compute the average rating
                    double averageRating = (double) totalrates / feedbacks.size();
                    int counts = feedbacks.size();
                    subjectTextView.setText(subject);
                    countTextView.setText(String.valueOf(counts));
                    averageTextView.setText(String.format(Locale.getDefault(), "%.2f", averageRating));
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.i("TAG","Error:"  + e.getMessage());
            }
        });

        findViewById(R.id.back_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 结束当前Activity
            }
        });

        //click feedback
        findViewById(R.id.feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feedbacks.isEmpty()){
                    Toast.makeText(getApplicationContext(), "No Feedback yet!", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(FeedbackAdmin.this, FeedbackList.class);
                    intent.putExtra("EVENTID", eventid);
                    startActivity(intent);
                }
            }
        });
    }
}