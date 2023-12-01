package com.example.b07project.dbOperation_Information;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.b07project.main.Information;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class ChangesFetcher implements FetchfromChangesOperation {
    private ChildEventListener childEventListenerForNewItem;
    private ChildEventListener childEventListenerForUpdates;
    private DatabaseReference itemsRef;


    @Override
    public void fetchNewitem(String path, ResultCallback<Information> callback) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        itemsRef = ref.child(path);
        childEventListenerForNewItem = new ChildEventListener(){
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                // 处理新添加的节点
                Information newItem = dataSnapshot.getValue(Information.class);
                Log.i("TAG", "onChildAdded: fetch success");
                callback.onSuccess(newItem);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                if (callback != null) {
                    callback.onFailure(databaseError.toException());
                }

            }
        };
        itemsRef.addChildEventListener(childEventListenerForNewItem);
    }


    public void fetchUpdates(String path, ResultCallback<Information> callback) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        itemsRef = ref.child(path);
        childEventListenerForUpdates = new ChildEventListener(){
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                if (dataSnapshot.exists()) {
                    // 遍历子节点
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        Information updatedItem = childSnapshot.getValue(Information.class);
                        callback.onSuccess(updatedItem);
                    }
                } else {
                    // 数据不存在时的处理
                    callback.onFailure(new Exception("No data available"));
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                if (callback != null) {
                    callback.onFailure(databaseError.toException());
                }

            }
        };
        itemsRef.addChildEventListener(childEventListenerForUpdates);
    }

    @Override
    public void removeListener() {
        if (itemsRef != null) {

            if (childEventListenerForNewItem != null) {
                itemsRef.removeEventListener(childEventListenerForNewItem);
                childEventListenerForNewItem = null;
                Log.i("TAG", "removeListener: remove ");
            }
            if (childEventListenerForUpdates != null) {
                itemsRef.removeEventListener(childEventListenerForUpdates);
                childEventListenerForUpdates = null;
            }
        }
    }
}
