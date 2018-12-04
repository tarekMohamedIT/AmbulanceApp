package com.r3tr0.ambulanceapp.model.events;

import com.google.firebase.auth.FirebaseUser;

public interface OnFirebaseProcessEndListener {
    void onSuccess(FirebaseUser user);
    void onFail();
}
