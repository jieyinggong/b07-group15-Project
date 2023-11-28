package com.example.b07project.ScheduledEvents_studentview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07project.R;
import com.example.b07project.dbOperation_Information.ReadItem;
import com.example.b07project.dbOperation_Information.ReadOperation;
import com.example.b07project.dbOperation_Information.ResultCallback;
import com.example.b07project.dbOperation_Special.ReadSpecialItem;
import com.example.b07project.dbOperation_Special.ReadSpecialOperation;
import com.example.b07project.main.Event;
import com.example.b07project.main.Information;

import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.ParseException;

public class DetailedScheduledEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_scheduled_event);

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
                    String subject = event.subject;
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

        //click feedback
        findViewById(R.id.feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String endTimeString = endTextView.getText().toString();
                    Calendar endTime = parseStringToCalendar(endTimeString);

                    if (Calendar.getInstance().after(endTime)) {
                        Intent intent = new Intent(DetailedScheduledEventActivity.this, Feedback_StudentActivity.class);
                        intent.putExtra("EVENTID", eventid);
                        startActivity(intent);
                    }else{
                        showToast("Can't give feedback for event that hasn't ended!");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private Calendar parseStringToCalendar(String timeString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(timeString));
        return calendar;
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}