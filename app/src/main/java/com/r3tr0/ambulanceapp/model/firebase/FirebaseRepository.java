package com.r3tr0.ambulanceapp.model.firebase;

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
    private DataSnapshot mainDataSnapshot;
    private ValueEventListener valueEventListener;

    private FirebaseRepository(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mainDataSnapshot = dataSnapshot;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mainDataSnapshot = null;
            }
        };
        databaseReference
                .child("drivers")
                .addValueEventListener(valueEventListener);
    }

    public static FirebaseRepository getInstance() {
        if (instance == null)
            instance = new FirebaseRepository();
        return instance;
    }

    @Override
    public void InsertNew(User user) {
        databaseReference
                .child("drivers")
                .child(user.getEmail().replaceAll("[.#$\\[\\]]", ""))
                .setValue(user);
    }

    @Override
    public User GetByID(@NonNull String email) {
        if (mainDataSnapshot != null)
            return mainDataSnapshot.child(email).getValue(User.class);
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
    public void Delete(@NonNull String email) {
        databaseReference
                .child("drivers")
                .child(email)
                .removeValue();
    }

    public void stopListening(){
        databaseReference
                .child("drivers")
                .removeEventListener(valueEventListener);
    }
}
