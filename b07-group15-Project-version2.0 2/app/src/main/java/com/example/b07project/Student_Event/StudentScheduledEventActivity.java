package com.example.b07project.Student_Event;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.b07project.R;
import com.example.b07project.main.Event;
import com.example.b07project.main.SetToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentScheduledEventActivity extends AppCompatActivity {
    private EventAdapter adapter;

    private List<Event> dataList;

    String path;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_event_scheduled_event_listview);

        ListView listView = findViewById(R.id.my_list_view);
        dataList = new ArrayList<>();
        adapter = new EventAdapter(this, R.layout.list_item_text, dataList);
        listView.setAdapter(adapter);

        SharedPreferences studentPreferences = getSharedPreferences("StudentPref", MODE_PRIVATE);
        String username = studentPreferences.getString("username", "not found");
        path = "StudentEventOrganize/" + username;
        read(path);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = dataList.get(position);
                Intent intent = new Intent(StudentScheduledEventActivity.this, DetailedScheduledEventActivity.class);
                intent.putExtra("EVENTID", event.infoID);
                startActivity(intent);
            }
        });
        Toolbar toolbar = findViewById(R.id.back_bar);
        SetToolbar.setBackFinish(this, toolbar);
    }

    public  void read(String path){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(path);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> eventPaths = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String eventPath = snapshot.getValue(String.class);
                    eventPaths.add(eventPath);
                    Log.i("tag", "read path success" + eventPath);
                }
                loadEventDetails(eventPaths);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("readSchdueldEvent: ", databaseError.getMessage());
            }
        });
    }

    private void loadEventDetails(List<String> eventPaths) {
        DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference().child("Event");

        for (String path : eventPaths) {
            eventsRef.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Event event = dataSnapshot.getValue(Event.class);
                    dataList.add(0, event);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("readSchdueldEvent: ", databaseError.getMessage());
                }
            });
        }
    }

}
