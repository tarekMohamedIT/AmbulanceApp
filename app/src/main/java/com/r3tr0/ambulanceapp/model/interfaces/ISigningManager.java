package com.r3tr0.ambulanceapp.model.interfaces;

import com.google.firebase.auth.FirebaseUser;
import com.r3tr0.ambulanceapp.model.models.User;

public interface ISigningManager {
    FirebaseUser signUp(User user);
    FirebaseUser signIn(User user);
    void signOut();
}
