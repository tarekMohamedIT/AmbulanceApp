package com.r3tr0.ambulanceapp.model.firebase;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.r3tr0.ambulanceapp.model.interfaces.IDBManager;
import com.r3tr0.ambulanceapp.model.models.User;

public class FirebaseRepository implements IDBManager<User> {

    private static FirebaseRepository instance;

    private DatabaseReference databaseReference;
    private MutableLiveData<DataSnapshot> mainDataSnapshot;
    private ValueEventListener valueEventListener;

    private FirebaseRepository(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (mainDataSnapshot == null)
                    mainDataSnapshot = new MutableLiveData<>();
                mainDataSnapshot.setValue(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mainDataSnapshot = null;
            }
        };
        databaseReference
                .addValueEventListener(valueEventListener);
    }

    public static FirebaseRepository getInstance() {
        if (instance == null)
            instance = new FirebaseRepository();
        return instance;
    }

    public MutableLiveData<DataSnapshot> getMainDataSnapshot() {
        return mainDataSnapshot;
    }

    @Override
    public void InsertNew(User user) {
        databaseReference
                .child("drivers")
                .child(user.getPhoneNumber())
                .setValue(user);
    }

    @Override
    public User GetByID(@NonNull String phone) {
        if (mainDataSnapshot.getValue() != null)
            return mainDataSnapshot.getValue().child(phone).getValue(User.class);
        return null;
    }

    @Override
    public void Update(@NonNull String id, @NonNull User value) {
        databaseReference
                .child("drivers")
                .child(value.getEmail())
                .setValue(value);
    }

    @Override
    public void Delete(@NonNull String phone) {
        databaseReference
                .child("drivers")
                .child(phone)
                .removeValue();
    }

    public void stopListening(){
        databaseReference
                .removeEventListener(valueEventListener);
    }

    public void sendMessage(String message) {
        databaseReference.child("Message").setValue(message);
    }
}
