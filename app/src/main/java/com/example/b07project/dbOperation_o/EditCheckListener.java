package com.example.b07project.dbOperation_o;
import com.google.firebase.database.DatabaseReference;
import  com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
public  class EditCheckListener implements ValueEventListener {
    private DatabaseReference ref;
    private Runnable onSuccess;
    private DefaultCallback callback;

    public EditCheckListener(DatabaseReference ref, Runnable onSuccess, DefaultCallback callback) {
        this.ref = ref;
        this.onSuccess = onSuccess;
        this.callback = callback;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
            onSuccess.run();
            callback.onSuccess();
        } else {
            callback.onFailure(new Exception("Operation failed: Item not found."));
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        callback.onFailure(new Exception(databaseError.getMessage()));
    }
}
