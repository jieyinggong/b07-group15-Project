package com.example.b07project.ScheduledEvents_studentview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.b07project.R;
import com.example.b07project.dbOperation_Information.DefaultCallback;
import com.example.b07project.dbOperation_Information.ResultCallback;
import com.example.b07project.dbOperation_Special.ChangesSpecialFetch;
import com.example.b07project.dbOperation_Special.FetchSpecialChangesOperation;
import com.example.b07project.dbOperation_Special.ReadSpecialItem;
import com.example.b07project.dbOperation_Special.ReadSpecialOperation;
import com.example.b07project.main.Event;
import com.example.b07project.main.Information;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentScheduledEventActivity extends AppCompatActivity {
    private ListView listView;
    private EventAdapter adapter;

    private List<Event> dataList;

    FetchSpecialChangesOperation fetcher = new ChangesSpecialFetch();
    String path;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_scheduled_event_listview);

        listView = findViewById(R.id.my_list_view);
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
    }
//    protected void onResume() {
//        super.onResume();
//        adapter.clear();
//        fetcher.fetchNewSpecialitem(path, Event.class, new ResultCallback<Information>() {
//            @Override
//            public void onSuccess(Information newItem) {
//                Event.AddCheckedEvent(newItem, dataList);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                Toast.makeText(StudentScheduledEventActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        fetcher.fetchUpdates("Event", Event.class, new ResultCallback<Information>() {
//            @Override
//            public void onSuccess(Information result) {
//                if (result instanceof Event){
//                    Event event = (Event) result;
//
//                }
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//
//            }
//        });
//    }

//    protected void onPause(){
//        super.onPause();
//        fetcher.removeListener();
//    }

    public  void read(String path){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(path);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> eventPaths = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String eventPath = snapshot.getValue(String.class);
                    eventPaths.add(eventPath);
                    Log.i("tag", "read path success" + eventPath);
                }
                loadEventDetails(eventPaths);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("readSchdueldEvent: ", databaseError.getMessage());
            }
        });
    }

    private void loadEventDetails(List<String> eventPaths) {
        DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference().child("Event");

        for (String path : eventPaths) {
            eventsRef.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Event event = dataSnapshot.getValue(Event.class);
                    dataList.add(0, event);
                    Log.i("tag", "add path success" + event.subject);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("readSchdueldEvent: ", databaseError.getMessage());
                }
            });
        }
    }

}
