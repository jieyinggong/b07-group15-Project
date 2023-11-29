package com.example.b07project.ScheduledEvents_studentview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.b07project.R;
import com.example.b07project.dbOperation_Information.CreateItem;
import com.example.b07project.dbOperation_Information.CreateOperation;
import com.example.b07project.dbOperation_Information.DefaultCallback;
import com.example.b07project.dbOperation_Information.EditItem;
import com.example.b07project.dbOperation_Information.EditOperation;
import com.example.b07project.dbOperation_Information.ResultCallback;
import com.example.b07project.dbOperation_Special.ReadSpecialItem;
import com.example.b07project.dbOperation_Special.ReadSpecialOperation;
import com.example.b07project.main.Event;
import com.example.b07project.main.Information;
import com.example.b07project.main.ParseToCalendar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.Calendar;

public class DetailedUpcomingEventActivity extends AppCompatActivity {
    boolean isRSVP = false;
    String scheduledEventsPath = "StudentEventOrganize/";

    Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_upcoming_event);

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
                    event = (Event) result;
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
        findViewById(R.id.RSVP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (event.CurrentAvailableSpace == 0){
                    showToast("The number of participant is full!");
                    return;
                }
                if (checkEventTime(Calendar.getInstance(),event)){
                    event.CurrentAvailableSpace -= 1;
                    EditOperation edit = new EditItem();
                    edit.update(event.infoID, "Event", event, new DefaultCallback() {
                        @Override
                        public void onSuccess() {
                            addScheduledEventToPath(event);
                        }

                        @Override
                        public void onFailure(Exception e) {
                            showToast("Error!" + e.getMessage());
                        }
                    });
                }
            }
        });

    }

    private boolean checkEventTime(Calendar calendar, Event event){
        try {
            String endTimeString = event.endDateTime;
            Calendar endTime = ParseToCalendar.parseStringToCalendar(endTimeString);
            String startTimeString = event.startDateTime;
            Calendar startTime = ParseToCalendar.parseStringToCalendar(startTimeString);
            if (calendar.after(startTime) && calendar.before(endTime)) {
                showToast("The event is in progress. Cannot RSVP!");
                return false;
            } else if (calendar.after(endTime)) {
                showToast("Event has ended!");
                return false;
            } else {
                return true;
            }
        }catch (ParseException e) {
                e.printStackTrace();
                showToast("Error!");
                return false;
            }
    }

    private void addScheduledEventToPath(Event event) {
        SharedPreferences studentPreferences = getSharedPreferences("StudentPref", MODE_PRIVATE);
        String username = studentPreferences.getString("username", "not found");
        String path = scheduledEventsPath + username;

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference().child(path);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(event.infoID)) {
                    ref.child(event.infoID).setValue(event.infoID);
                    showToast("Success RSVP");
                } else {
                    showToast("Already RSVP!");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showToast("Error!" + databaseError.getMessage());
            }
        });
    }


    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}

