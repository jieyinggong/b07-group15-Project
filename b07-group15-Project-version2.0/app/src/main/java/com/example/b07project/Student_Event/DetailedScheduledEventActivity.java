package com.example.b07project.Student_Event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07project.R;
import com.example.b07project.dbOperation_Information.ResultCallback;
import com.example.b07project.dbOperation_Special.ReadSpecialItem;
import com.example.b07project.dbOperation_Special.ReadSpecialOperation;
import com.example.b07project.main.Event;
import com.example.b07project.main.Information;
import com.example.b07project.main.ParseToCalendar;
import com.example.b07project.main.SetToolbar;

import java.util.Calendar;
import java.text.ParseException;

public class DetailedScheduledEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_event_detailed_scheduled_event);

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
        TextView spaceTextView = findViewById(R.id.space_text_view);

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
                    String space = String.valueOf(event.CurrentAvailableSpace);

                    setTextView(subjectTextView,subject);
                    setTextView(startTextView,start);
                    setTextView(endTextView, end);
                    setTextView(locationTextView, location);
                    setTextView(descriptionTextView, content);
                    setTextView(spaceTextView, space);
                }

            }

            @Override
            public void onFailure(Exception e) {
                    Log.i("TAG","Error:"  + e.getMessage());
            }
        });

        //click back
        Toolbar toolbar = findViewById(R.id.back_bar);
        SetToolbar.setBackFinish(this, toolbar);

        //click feedback
        findViewById(R.id.feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String endTimeString = endTextView.getText().toString();
                    Calendar endTime = ParseToCalendar.parseStringToCalendar(endTimeString);

                    if (Calendar.getInstance().after(endTime)) {
                        Intent intent = new Intent(DetailedScheduledEventActivity.this, Feedback_StudentActivity.class);
                        intent.putExtra("EVENTID", eventid);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Can't give feedback for event that hasn't ended!", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void setTextView(TextView textView, String content){
        if (textView != null){
            textView.setText(content);
        }
    }

}