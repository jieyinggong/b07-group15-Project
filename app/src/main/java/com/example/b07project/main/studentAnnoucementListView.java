package com.example.b07project.main;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.b07project.complaintsadminview.ComplaintAdminActivity;
import com.example.b07project.complaintsadminview.DetailedItemActivity;
import com.example.b07project.main.Information;
import com.example.b07project.R;
import com.example.b07project.dbOperation_Information.*;
import com.example.b07project.main.InformationAdapter;
import java.util.ArrayList;
import java.util.List;

public class studentAnnoucementListView extends AppCompatActivity {
    private ListView listView;
    private InformationAdapter adapter;
    private List<Information> dataList;
    FetchfromChangesOperation fetcher = new ChangesFetcher();
    private boolean isDataLoaded = false;
    String path = "Announcement";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_annoucement_list_view);

        listView = findViewById(R.id.annoucementListView);
        dataList = new ArrayList<>();
        ReadOperation read = new ReadItem();
        adapter = new InformationAdapter(this, R.layout.list_item_text, dataList);
        listView.setAdapter(adapter);

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
//
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
                    Toast.makeText(studentAnnoucementListView.this, "Data is still loading, please wait.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Information info = dataList.get(position);
                Intent intent = new Intent(studentAnnoucementListView.this, DetailedItemActivity.class);
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
                Toast.makeText(studentAnnoucementListView.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onPause(){
        super.onPause();
        fetcher.removeListener();
    }

    }
