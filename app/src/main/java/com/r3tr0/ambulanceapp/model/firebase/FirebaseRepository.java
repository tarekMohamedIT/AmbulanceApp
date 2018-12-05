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

    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void InsertNew(User user) {

        DatabaseReference newUser = databaseReference
                .child("drivers")
                .push();
        newUser.setValue(user);

        user.setId(newUser.getKey());
        setUser(user);
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

    public void setAccepted(String emergencyId, String driverId) {
        databaseReference
                .child("waiting Emergencies")
                .child(emergencyId)
                .child("Accepted by?")
                .setValue("ACCEPTED-" + driverId);
    }

    public void setRejected(String emergencyId) {
        databaseReference
                .child("waiting Emergencies")
                .child(emergencyId)
                .child("Accepted by?")
                .setValue("NONE");
    }
}
