package com.example.b07project.dbOperation;

import android.service.carrier.CarrierMessagingService;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.ArrayList;
import java.lang.Class;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import  com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
public class ReadItem<T> implements ReadOperation<T>{
    DatabaseReference ref;
    public  ReadItem(){
        ref = FirebaseDatabase.getInstance().getReference();
    }
    public  ReadItem(DefaultCallback callback){
        ref = FirebaseDatabase.getInstance().getReference();
    }

    public void read(String id, Class<T> tClass, ResultCallback<T> callback){
        DatabaseReference itemRef =ref.child(tClass.getSimpleName()).child(id);
        itemRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 数据读取成功
                if (dataSnapshot.exists()) {
                    // 使用 dataSnapshot 中的数据创建 T 类型的对象
                    T result = dataSnapshot.getValue(tClass);
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
    public void listAll(Class<T> tClass, ResultCallback<List<T>> callback) {
        DatabaseReference itemsRef = ref.child(tClass.getSimpleName());
        itemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<T> resultList = new ArrayList<>();
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        T item = itemSnapshot.getValue(tClass);
                        resultList.add(item);
                    }
                    if (callback != null) {
                        callback.onSuccess(resultList);
                    }
                } else {
                    if (callback != null) {
                        callback.onFailure(new Exception("No data found in " + tClass.getSimpleName()));
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
