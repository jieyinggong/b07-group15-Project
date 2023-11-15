package com.example.b07project.dbOperation;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import  com.google.firebase.database.ValueEventListener;

public class EditItem<T> implements EditOperation<T> {
    private DatabaseReference ref;
    DefaultCallback callback;
    public  EditItem(){
        ref = FirebaseDatabase.getInstance().getReference();
        callback = new PrintCallback();
    }
    public  EditItem(DefaultCallback callback){
        ref = FirebaseDatabase.getInstance().getReference();
        this.callback = callback;
    }

    public void update(String id, T item){
        String className = item.getClass().getSimpleName();
        DatabaseReference itemRef =ref.child(className).child(id);
        ValueEventListener listener = new EditCheckListener<>(
                itemRef,
                () -> itemRef.setValue(item),  // onSuccess
                callback
        );
        itemRef.addListenerForSingleValueEvent(listener);
    }
    public void delete(String id, T item){
        String className = item.getClass().getSimpleName();
        DatabaseReference itemRef =ref.child(className).child(id);
        ValueEventListener listener = new EditCheckListener<>(
                itemRef,
                () -> itemRef.removeValue(),  // onSuccess
                callback
        );
        itemRef.addListenerForSingleValueEvent(listener);
    }
}
