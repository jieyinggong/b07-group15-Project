package com.example.b07project.dbOperation_Information;

import com.example.b07project.main.Information;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CreateItem implements CreateOperation{

    public CreateItem(){}

    @Override
    public void create(String path, Information item, DefaultCallback callback) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference();
        DatabaseReference newRef = ref.child(path).push();
        item.infoID = newRef.getKey();
        newRef.setValue(item)
                .addOnSuccessListener(v -> {
                    if (callback != null) callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    if (callback != null) callback.onFailure(e);
                });
    }
}
