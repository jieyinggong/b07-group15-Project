package com.example.b07project.ScheduledEvents_studentview;

import android.content.SharedPreferences;

import com.example.b07project.dbOperation_Information.DefaultCallback;
import com.example.b07project.main.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.widget.Toast;

import android.content.SharedPreferences;

public class AddScheduledEvent {
    private void addScheduledEvent(Event event, String path) {
        DefaultCallback callback = new DefaultCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference();
        ref.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // 检查键是否存在
                if (!dataSnapshot.hasChild(event.infoID)) {
                    // 如果不存在，添加新的键值对
                    ref.child(event.infoID).setValue(event);
                } else {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 处理错误情况
            }
        });
        DatabaseReference newRef = ref.child(path).child(event.infoID);

    }
}
