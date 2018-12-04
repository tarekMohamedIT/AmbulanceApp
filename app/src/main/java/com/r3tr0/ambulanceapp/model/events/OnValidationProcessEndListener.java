package com.r3tr0.ambulanceapp.model.events;

public interface OnValidationProcessEndListener {
    void onSuccess();
    void onFail(int id);
}
