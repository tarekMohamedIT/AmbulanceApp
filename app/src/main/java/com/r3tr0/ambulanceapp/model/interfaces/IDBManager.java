package com.r3tr0.ambulanceapp.model.interfaces;

import android.support.annotation.NonNull;

public interface IDBManager<V> {

    void InsertNew(V value);

    V GetByID(@NonNull String email);

    void Update(@NonNull String id, @NonNull V value);

    void Delete(@NonNull String email);

}
