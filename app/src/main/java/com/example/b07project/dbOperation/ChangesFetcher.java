package com.example.b07project.dbOperation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.Query;


import androidx.annotation.NonNull;
public class ChangesFetcher<T> implements FetchfromChangesOperation<T> {
    private DatabaseReference ref;

    public ChangesFetcher() {
        ref = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void FetchChanges(Class<T> tClass, ResultCallback<T> callback) {
        DatabaseReference itemsRef = ref.child(tClass.getSimpleName());
        Query query = itemsRef.limitToLast(1);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                // 处理新添加的节点
                T newItem = dataSnapshot.getValue(tClass);
                callback.onSuccess(newItem);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                // 处理更新的节点
                T updatedItem = dataSnapshot.getValue(tClass);
                callback.onSuccess(updatedItem);
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
}
