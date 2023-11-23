package com.example.b07project.dbOperation_o;
import android.util.Log;

import com.example.b07project.Information;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.Query;
import  com.google.firebase.database.ValueEventListener;


import androidx.annotation.NonNull;
public class ChangesFetcher implements FetchfromChangesOperation {
    private ChildEventListener childEventListenerForSingleItem;
    private ChildEventListener childEventListenerForNewItem;
    private ValueEventListener valueEventListenerForUpdates;
    private DatabaseReference itemsRef;

    @Override
    public void fetchSingleNewitem(String path, ResultCallback<Information> callback) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        itemsRef = ref.child(path);
        Query query = itemsRef.limitToLast(1);
        childEventListenerForSingleItem = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("FirebaseListener", "New child added: " + dataSnapshot.getKey());
                // 处理新添加的节点
                Information newItem = (Information) dataSnapshot.getValue(Information.class);
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
    query.addChildEventListener(childEventListenerForSingleItem);
    }

    @Override
    public void fetchNewitem(String path, ResultCallback<Information> callback) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference itemsRef = ref.child(path);
        childEventListenerForNewItem = new ChildEventListener(){
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                // 处理新添加的节点
                Information newItem = (Information) dataSnapshot.getValue(Information.class);
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
        DatabaseReference itemsRef = ref.child(path);

        valueEventListenerForUpdates = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 检查数据是否存在
                if (dataSnapshot.exists()) {
                    // 遍历子节点
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        Information updatedItem = (Information) childSnapshot.getValue(Information.class);
                        callback.onSuccess(updatedItem);
                    }
                } else {
                    // 数据不存在时的处理
                    callback.onFailure(new Exception("No data available"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFailure(databaseError.toException());
            }
        };

        itemsRef.addValueEventListener(valueEventListenerForUpdates);
    }
    @Override
    public void removeListener() {
        if (itemsRef != null) {
            if (childEventListenerForSingleItem != null) {
                itemsRef.removeEventListener(childEventListenerForSingleItem);
            }
            if (childEventListenerForNewItem != null) {
                itemsRef.removeEventListener(childEventListenerForNewItem);
            }
            if (valueEventListenerForUpdates != null) {
                itemsRef.removeEventListener(valueEventListenerForUpdates);
            }
        }
    }
}
