package com.example.b07project.complaintsadminview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.b07project.Complaint;
import com.example.b07project.Information;
import com.example.b07project.R;
import com.example.b07project.dbOperation_o.*;
import com.google.firebase.database.ChildEventListener;


import java.util.ArrayList;
import java.util.List;

public class ComplaintAdminActivity extends AppCompatActivity {
    private ListView listView;
    private InformationAdapter adapter;
    private List<Information> dataList; //要显示的东西

    FetchfromChangesOperation fetcher = new ChangesFetcher();
    private boolean isDataLoaded = false;
    String path = "Complaint";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_admin);

        listView = findViewById(R.id.my_list_view);
        String path = "Complaint";
        dataList = new ArrayList<>();
        ReadOperation read = new ReadItem();
        adapter = new InformationAdapter(this, R.layout.list_item_text, dataList);
        listView.setAdapter(adapter);
//        Complaint info1 = new Complaint("subject1","content11");
        read.listAll(path, new ResultCallback<List<Information>>() {
            @Override
            public void onSuccess(List<Information> result) {
                dataList.clear();
                if (result != null){
                    for (Information info : result) {
                        if (info != null && info.infoID != null) {
                            dataList.add(0,info);
                        }
                    }
                }
//                dataList.add(0,info2);
//                dataList.add(0, info1);
                adapter.notifyDataSetChanged();
                isDataLoaded = true;
            }

            @Override
            public void onFailure(Exception e) {
                Log.i("TAG","Error:"  + e.getMessage());
                }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!isDataLoaded) {
                    Toast.makeText(ComplaintAdminActivity.this, "Data is still loading, please wait.", Toast.LENGTH_SHORT).show();
                    return;
                }
               Information info = dataList.get(position);
                Intent intent = new Intent(ComplaintAdminActivity.this, DetailedItemActivity.class);
                intent.putExtra("SUBJECT", info.subject);
                intent.putExtra("CONTENT", info.content);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Complaint info2 = new Complaint("subject2","content111");
//        dataList.add(0, info2);
//        adapter.notifyDataSetChanged();
        fetcher.fetchNewitem(path, new ResultCallback<Information>() {
            @Override
            public void onSuccess(Information newItem) {
                    dataList.add(0, newItem);
                    adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(ComplaintAdminActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onPause(){
        super.onPause();
        fetcher.removeListener();
    }
}