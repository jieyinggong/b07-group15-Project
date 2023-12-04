package com.example.b07project.Student_Announcement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.b07project.Admin_Complaints.DetailedItemActivity;
import com.example.b07project.dbOperation_Information.ChangesFetcher;
import com.example.b07project.dbOperation_Information.FetchfromChangesOperation;
import com.example.b07project.dbOperation_Information.ReadItem;
import com.example.b07project.dbOperation_Information.ReadOperation;
import com.example.b07project.dbOperation_Information.ResultCallback;
import com.example.b07project.main.Information;
import com.example.b07project.main.InformationAdapter;

import java.util.ArrayList;
import java.util.List;

import com.example.b07project.R;
import com.example.b07project.main.SetToolbar;

public class AnnouncementActivity extends AppCompatActivity {
    private InformationAdapter adapter;
    private List<Information> dataList;

    FetchfromChangesOperation fetcher = new ChangesFetcher();
    private boolean isDataLoaded = false;
    String path = "Announcement";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.annoucement_view_listview);

        ListView listView = findViewById(R.id.annoucementListView);
        dataList = new ArrayList<>();
        ReadOperation read = new ReadItem();
        adapter = new InformationAdapter(this, R.layout.list_item_text, dataList);
        listView.setAdapter(adapter);

        read.listAll(path, new ResultCallback<List<Information>>() {
            @Override
            public void onSuccess(List<Information> result) {
                dataList.clear();
                if(result != null) {
                    for (Information info : result) {
                        if (info != null && info.infoID != null) {
                            dataList.add(0, info);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                isDataLoaded = true;
            }

            @Override
            public void onFailure(Exception e) { Log.i("TAG", "Error:" + e.getMessage());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!isDataLoaded) {
                    Toast.makeText(AnnouncementActivity.this, "Data is still loading, please wait.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Information info = dataList.get(position);
                Intent intent = new Intent(AnnouncementActivity.this, DetailedAnnouncementItemActivity.class);
                intent.putExtra("SUBJECT", info.subject);
                intent.putExtra("CONTENT", info.content);
                startActivity(intent);
            }
        });

        Toolbar toolbar = findViewById(R.id.back_bar);
        SetToolbar.setBackFinish(this, toolbar);

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.clear();

        fetcher.fetchNewitem(path, new ResultCallback<Information>() {
            @Override
            public void onSuccess(Information newItem) {
                dataList.add(0, newItem);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(AnnouncementActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onPause(){
        super.onPause();
        fetcher.removeListener();
    }
}
