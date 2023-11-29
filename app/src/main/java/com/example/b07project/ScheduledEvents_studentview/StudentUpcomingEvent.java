package com.example.b07project.ScheduledEvents_studentview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.b07project.dbOperation_Special.ChangesSpecialFetch;
import com.example.b07project.dbOperation_Special.FetchSpecialChangesOperation;
import com.example.b07project.dbOperation_Special.ReadSpecialItem;
import com.example.b07project.dbOperation_Special.ReadSpecialOperation;
import com.example.b07project.main.Event;
import com.example.b07project.main.Information;
import com.example.b07project.R;
import com.example.b07project.dbOperation_Information.*;


import java.util.ArrayList;
import java.util.List;

public class StudentUpcomingEvent extends AppCompatActivity {
    private ListView listView;
    private EventAdapter adapter;
    private List<Event> dataList; //要显示的东西

    FetchSpecialChangesOperation fetcher = new ChangesSpecialFetch();
    private boolean isDataLoaded = false;
    String path = "Event";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_upcomingevent_listview);

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
                    Toast.makeText(StudentUpcomingEvent.this, "Data is still loading, please wait.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Event event = dataList.get(position);
                Intent intent = new Intent(StudentUpcomingEvent.this, DetailedUpcomingEventActivity.class);
                intent.putExtra("EVENTID", event.infoID);
                startActivity(intent);
            }
        });
    }
    protected void onResume() {
        super.onResume();
//        Complaint info2 = new Complaint("subject2","content111");
//        dataList.add(0, info2);
//        adapter.notifyDataSetChanged();
        adapter.clear();
        fetcher.fetchNewSpecialitem(path, Event.class, new ResultCallback<Information>() {
            @Override
            public void onSuccess(Information newItem) {
               Event.AddCheckedEvent(newItem, dataList);
               adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(StudentUpcomingEvent.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onPause(){
        super.onPause();
        fetcher.removeListener();
    }


    public void ListEvent(List<Information> result){
        for (Information info : result) {
            Event.AddCheckedEvent(info, dataList);
            }
    }


}
