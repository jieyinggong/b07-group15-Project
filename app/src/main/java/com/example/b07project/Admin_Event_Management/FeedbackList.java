package com.example.b07project.Admin_Event_Management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.b07project.R;
import com.example.b07project.dbOperation_Information.ResultCallback;
import com.example.b07project.dbOperation_Special.ReadSpecialItem;
import com.example.b07project.dbOperation_Special.ReadSpecialOperation;
import com.example.b07project.main.Feedback;
import com.example.b07project.main.Information;
import com.example.b07project.main.SetToolbar;

import java.util.ArrayList;
import java.util.List;

public class FeedbackList extends AppCompatActivity {

    private FeedbackAdapter feedbackAdapter;
    private List<Feedback> feedbacks_list;
    private boolean isDataLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_feedback_comment_listview);

        String eventid = getIntent().getStringExtra("EVENTID");
        String path = "Feedback/" + eventid;
        ListView feedbacks_listview = findViewById(R.id.feedback_list_view);
        feedbacks_list = new ArrayList<>();
        ReadSpecialOperation read = new ReadSpecialItem();
        feedbackAdapter = new FeedbackAdapter(this, feedbacks_list);
        feedbacks_listview.setAdapter(feedbackAdapter);

        read.listAllSpecial(path, Feedback.class, new ResultCallback<List<Information>>() {
            @Override
            public void onSuccess(List<Information> result) {
                feedbacks_list.clear();
                for (Information info : result) {
                    if(info instanceof Feedback) {
                        Feedback feedback = (Feedback) info;
                        feedbacks_list.add(0, feedback);
                    }
                }
                feedbackAdapter.notifyDataSetChanged();
                isDataLoaded = true;
            }

            @Override
            public void onFailure(Exception e) {
                Log.i("TAG", "Error:" + e.getMessage());
            }
        });

        feedbacks_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!isDataLoaded) {
                    Toast.makeText(FeedbackList.this, "Data is still loading, please wait.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Feedback feedback = feedbacks_list.get(position);
                Intent intent = new Intent(FeedbackList.this, DetailedFeedbackActivity.class);
                intent.putExtra("COMMENT", feedback.content);
                intent.putExtra("RATE",feedback.numericRating);
                startActivity(intent);
            }
        });

        Toolbar toolbar = findViewById(R.id.back_bar);
        SetToolbar.setBackFinish(this, toolbar);

    }
}