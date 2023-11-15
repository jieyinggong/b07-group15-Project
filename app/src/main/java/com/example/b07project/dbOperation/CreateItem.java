package com.example.b07project.dbOperation;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;



public class CreateItem<T> implements CreateOperation<T>{
    FirebaseDatabase db;
    public CreateItem(){
        db = FirebaseDatabase.getInstance();
    }

    public CreateItem(FirebaseDatabase db){
        this.db=db;
    }

    @Override
    public void create(String path, T item, DefaultCallback callback) {
        DatabaseReference ref = db.getReference();
        ref.child(path).push().setValue(item)
                .addOnSuccessListener(v -> {
                    if (callback != null) callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    if (callback != null) callback.onFailure(e);
                });
    }
}
