package com.example.b07project.dbOperation_o;
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

    @Override
    public void fetchNewitem(String node, ResultCallback<Information> callback) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference itemsRef = ref.child(node);
        Query query = itemsRef.limitToLast(1);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                // 处理新添加的节点
                Information newItem = dataSnapshot.getValue(Information.class);
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
    });
    }

    public void fetchUpdates(String node, ResultCallback<Information> callback) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference itemsRef = ref.child(node);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 检查数据是否存在
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
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFailure(databaseError.toException());
            }
        };

        itemsRef.addValueEventListener(listener);
    }

}