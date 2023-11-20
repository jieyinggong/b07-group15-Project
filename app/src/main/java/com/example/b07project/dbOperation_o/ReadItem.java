package com.example.b07project.dbOperation_o;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.ArrayList;
import java.lang.Class;

import com.example.b07project.Information;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import  com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
public class ReadItem implements ReadOperation{
    DatabaseReference ref;
    public  ReadItem(){
        ref = FirebaseDatabase.getInstance().getReference();
    }
    public  ReadItem(DefaultCallback callback){
        ref = FirebaseDatabase.getInstance().getReference();
    }

    public void read(String id,String nodeName, ResultCallback<Information> callback){
        DatabaseReference itemRef =ref.child(nodeName).child(id);
        itemRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 数据读取成功
                if (dataSnapshot.exists()) {
                    // 使用 dataSnapshot 中的数据创建 T 类型的对象
                    Information result = dataSnapshot.getValue(Information.class);
                    if (callback != null) {
                        callback.onSuccess(result);
                    }
                } else {
                    // 数据不存在
                    if (callback != null) {
                        callback.onFailure(new Exception("Data not found for ID: " + id));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 读取数据失败
                if (callback != null) {
                    callback.onFailure(databaseError.toException());
                }
            }
        });
    }
    public void listAll(String node, ResultCallback<List<Information>> callback) {
        DatabaseReference itemsRef = ref.child(node);
        itemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<Information> resultList = new ArrayList<>();
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        Information item = itemSnapshot.getValue(Information.class);
                        resultList.add(item);
                    }
                    if (callback != null) {
                        callback.onSuccess(resultList);
                    }
                } else {
                    if (callback != null) {
                        callback.onFailure(new Exception("No data found in " + node));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                if (callback != null) {
                    callback.onFailure(databaseError.toException());
                }
            }
        });
    }

}
