package com.example.b07project.Admin_Event_Management;

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
import com.example.b07project.main.SetToolbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DetailedEventHistory extends AppCompatActivity {

    String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_event_detailed_event_management_display);
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
                    subject = event.subject;
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


        //click to see feedback
        findViewById(R.id.feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String endTimeString = endTextView.getText().toString();
                    Calendar endTime = parseStringToCalendar(endTimeString);

                    if (Calendar.getInstance().after(endTime)) {
                        Intent intent = new Intent(DetailedEventHistory.this, FeedbackAdmin.class);
                        intent.putExtra("EVENTID", eventid);
                        intent.putExtra("SUBJECT", subject);
                        startActivity(intent);
                    }else{
                        String message = "This event hasn't ended! Can't see feedback!";
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.back_bar);
        SetToolbar.setBackFinish(this, toolbar);
    }

    private Calendar parseStringToCalendar(String timeString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        if (timeString != null){
            Date date = dateFormat.parse(timeString);
            if (date != null){
              calendar.setTime(date);}
        }
        return calendar;
    }

    private void setTextView(TextView textView, String content){
        if (textView != null){
            textView.setText(content);
        }
    }
}

