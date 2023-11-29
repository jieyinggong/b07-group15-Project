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
import com.example.b07project.dbOperation_Information.ResultCallback;
import com.example.b07project.dbOperation_Special.ChangesSpecialFetch;
import com.example.b07project.dbOperation_Special.FetchSpecialChangesOperation;
import com.example.b07project.main.Event;
import com.example.b07project.main.Information;

import java.util.ArrayList;
import java.util.List;

public class StudentScheduledEvent extends AppCompatActivity {
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = dataList.get(position);
                Intent intent = new Intent(StudentScheduledEvent.this, DetailedScheduledEventActivity.class);
                intent.putExtra("EVENTID", event.infoID);
                startActivity(intent);
            }
        });
    }
    protected void onResume() {
        super.onResume();
        adapter.clear();
        fetcher.fetchNewSpecialitem(path, Event.class, new ResultCallback<Information>() {
            @Override
            public void onSuccess(Information newItem) {
                Event.AddCheckedEvent(newItem, dataList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(StudentScheduledEvent.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onPause(){
        super.onPause();
        fetcher.removeListener();
    }

}
