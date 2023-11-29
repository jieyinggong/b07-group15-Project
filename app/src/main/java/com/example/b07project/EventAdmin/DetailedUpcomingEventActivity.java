package com.example.b07project.EventAdmin;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07project.R;
import com.example.b07project.ScheduledEvents_studentview.Feedback_StudentActivity;
import com.example.b07project.dbOperation_Information.ResultCallback;
import com.example.b07project.dbOperation_Special.ReadSpecialItem;
import com.example.b07project.dbOperation_Special.ReadSpecialOperation;
import com.example.b07project.main.Event;
import com.example.b07project.main.Information;
import com.example.b07project.main.ParseToCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DetailedUpcomingEventActivity extends AppCompatActivity {

    String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_upcoming_admin);
        String eventid = getIntent().getStringExtra("EVENTID");

        TextView subjectTextView = findViewById(R.id.subject_text_view);
        if (subjectTextView != null) {
            subjectTextView.setText("Some Text");
        } else {
            Log.e("DetailActivity", "TextView not found");
        }
        TextView startTextView = findViewById(R.id.start_time_text_view);
        TextView endTextView = findViewById(R.id.end_time_text_view);
        TextView locationTextView = findViewById(R.id.location_text_view);
        TextView descriptionTextView = findViewById(R.id.description_text_view);

        ReadSpecialOperation readSpecialItem = new ReadSpecialItem();
        String path = "Event";

        readSpecialItem.readSpecial(eventid, Event.class, path, new ResultCallback<Information>() {
            @Override
            public void onSuccess(Information result) {
                if(result instanceof Event) {
                    Event event = (Event) result;
                    subject = event.subject;
                    String content = event.content;
                    String start = event.startDateTime;
                    String end = event.endDateTime;
                    String location = event.location;

                    subjectTextView.setText(subject);
                    startTextView.setText(start);
                    endTextView.setText(end);
                    locationTextView.setText(location);
                    descriptionTextView.setText(content);
                }

            }

            @Override
            public void onFailure(Exception e) {
                Log.i("TAG","Error:"  + e.getMessage());
            }
        });

        //click back
        findViewById(R.id.back_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 结束当前Activity
            }
        });

        //click to see feedback
        findViewById(R.id.editEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent intent = new Intent(DetailedUpcomingEventActivity.this, EditEventActivity.class);
                        intent.putExtra("EVENTID", eventid);
                        startActivity(intent);

            }
        });

    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}

