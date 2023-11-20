package com.example.b07project.dbOperation_o;

import com.example.b07project.Information;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import  com.google.firebase.database.ValueEventListener;

public class EditItem implements EditOperation {

    public void update(String id, String nodeName, Information item, DefaultCallback callback){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference itemRef =ref.child(nodeName).child(id);
        item.infoID = id;
        ValueEventListener listener = new EditCheckListener(
                itemRef,
                () -> itemRef.setValue(item),  // onSuccess
                callback
        );
        itemRef.addListenerForSingleValueEvent(listener);
    }
    public void delete(String id, String nodeName, DefaultCallback callback){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference itemRef =ref.child(nodeName).child(id);
        ValueEventListener listener = new EditCheckListener(
                itemRef,
                itemRef::removeValue,  // onSuccess
                callback
        );
        itemRef.addListenerForSingleValueEvent(listener);
    }
}
