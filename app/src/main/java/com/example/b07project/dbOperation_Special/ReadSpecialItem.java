package com.example.b07project.dbOperation_Special;

import androidx.annotation.NonNull;

import com.example.b07project.main.Information;
import com.example.b07project.dbOperation_Information.DefaultCallback;
import com.example.b07project.dbOperation_Information.ResultCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReadSpecialItem implements ReadSpecialOperation {
    DatabaseReference ref;
    public ReadSpecialItem(){
        ref = FirebaseDatabase.getInstance().getReference();
    }
    public ReadSpecialItem(DefaultCallback callback){
        ref = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void readSpecial(String id,Class<?> claz, String path, ResultCallback<Information> callback){
        DatabaseReference itemRef =ref.child(path).child(id);
        itemRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (Information.class.isAssignableFrom(claz)) {
                        Information result = (Information) dataSnapshot.getValue(claz);
                        if (callback != null) {
                            callback.onSuccess(result);
                        }}
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

    @Override
    public void listAllSpecial(String path, Class<?> claz, ResultCallback<List<Information>> callback) {
        DatabaseReference itemsRef = ref.child(path);
        itemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<Information> resultList = new ArrayList<>();
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        if (Information.class.isAssignableFrom(claz)) {
                            Information item = (Information) dataSnapshot.getValue(claz);
                            resultList.add(item);
                    }
                    if (callback != null) {
                        callback.onSuccess(resultList);
                    }}
                } else {
                    if (callback != null) {
                        callback.onFailure(new Exception("No data found in " + path));
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
