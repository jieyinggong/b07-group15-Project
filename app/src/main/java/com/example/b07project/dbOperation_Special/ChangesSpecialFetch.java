package com.example.b07project.dbOperation_Special;


import androidx.annotation.NonNull;

import com.example.b07project.main.Information;
import com.example.b07project.dbOperation_Information.ResultCallback;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ChangesSpecialFetch implements FetchSpecialChangesOperation {
    private ChildEventListener childEventListenerForNewItem;
    private ChildEventListener childEventListenerForUpdates;
    private DatabaseReference itemsRef;

    @Override
    public void fetchNewSpecialitem(String path, Class<?> claz,ResultCallback<Information> callback) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        itemsRef = ref.child(path);
        childEventListenerForNewItem = new ChildEventListener(){
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                if (Information.class.isAssignableFrom(claz)) {
                    Information newItem = (Information) dataSnapshot.getValue(claz);
                    callback.onSuccess(newItem);
                }
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

    public void fetchUpdates(String path, Class<?> claz, ResultCallback<Information> callback) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        itemsRef = ref.child(path);

        childEventListenerForUpdates = new ChildEventListener(){
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                if (dataSnapshot.exists()) {
                    if (Information.class.isAssignableFrom(claz)) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            Information updatedItem = (Information) childSnapshot.getValue(claz);
                            callback.onSuccess(updatedItem);
                        }
                    }
                } else {
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
            }
            if (childEventListenerForUpdates != null) {
                itemsRef.removeEventListener(childEventListenerForUpdates);
            }
        }
    }
}
