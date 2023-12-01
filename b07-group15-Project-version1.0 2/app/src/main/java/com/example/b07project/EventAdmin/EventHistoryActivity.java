package com.example.b07project.EventAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.b07project.R;
import com.example.b07project.ScheduledEvents_studentview.EventAdapter;
import com.example.b07project.dbOperation_Information.ResultCallback;
import com.example.b07project.dbOperation_Special.ChangesSpecialFetch;
import com.example.b07project.dbOperation_Special.FetchSpecialChangesOperation;
import com.example.b07project.dbOperation_Special.ReadSpecialItem;
import com.example.b07project.dbOperation_Special.ReadSpecialOperation;
import com.example.b07project.main.Event;
import com.example.b07project.main.Information;

import java.util.ArrayList;
import java.util.List;

public class EventHistoryActivity extends AppCompatActivity {
    private ListView listView;
    private EventAdapter adapter;
    private List<Event> dataList; //要显示的东西

    FetchSpecialChangesOperation fetcher = new ChangesSpecialFetch();
    private boolean isDataLoaded = false;
    String path = "Event";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_history_admin);

        listView = findViewById(R.id.my_list_view);
        dataList = new ArrayList<>();
        ReadSpecialOperation read = new ReadSpecialItem();
        adapter = new EventAdapter(this, R.layout.list_item_text, dataList);
        listView.setAdapter(adapter);
        read.listAllSpecial(path, Event.class, new ResultCallback<List<Information>>() {
            @Override
            public void onSuccess(List<Information> result) {
                dataList.clear();
                if (result != null) {
                    ListEvent(result);
                }
                adapter.notifyDataSetChanged();
                isDataLoaded = true;
            }

            @Override
            public void onFailure(Exception e) {
                Log.i("TAG", "Error:" + e.getMessage());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!isDataLoaded) {
                    Toast.makeText(EventHistoryActivity.this, "Data is still loading, please wait.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Event event = dataList.get(position);
               intendCases(event);
            }
        });
    }

    protected void onResume() {
        super.onResume();
        adapter.clear();
        fetcher.fetchNewSpecialitem(path, Event.class, new ResultCallback<Information>() {
            @Override
            public void onSuccess(Information newItem) {
                AddCheckedEvent(newItem);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(EventHistoryActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onPause(){
        super.onPause();
        fetcher.removeListener();
    }
    public void ListEvent(List<Information> result){
        for (Information info : result) {
            AddCheckedEvent(info);
        }
    }

    public void AddCheckedEvent(Information info){
        if (info instanceof Event && info.infoID != null) {
            Event event = (Event) info;
            dataList.add(0, event);
        }
    }

    public void intendCases(Event event){
        if (event.eventNotStart()){
            Intent intent = new Intent(EventHistoryActivity.this, DetailedUpcomingEventActivity.class);
            intent.putExtra("EVENTID", event.infoID);
            startActivity(intent);
        }else{
            Intent intent = new Intent(EventHistoryActivity.this, DetailedEventHistory.class);
            intent.putExtra("EVENTID", event.infoID);
            startActivity(intent);
        }
    }
}