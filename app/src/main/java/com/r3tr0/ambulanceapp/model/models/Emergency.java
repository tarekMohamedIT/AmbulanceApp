package com.r3tr0.ambulanceapp.model.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Emergency implements Parcelable {
    public static final Creator<Emergency> CREATOR = new Creator<Emergency>() {
        @Override
        public Emergency createFromParcel(Parcel in) {
            return new Emergency(in);
        }

        @Override
        public Emergency[] newArray(int size) {
            return new Emergency[size];
        }
    };
    private String id;
    private String type;
    private String numberOfPatients;
    private String acceptedBy;

    public Emergency(String id, String type, String numberOfPatients, String acceptedBy) {
        this.id = id;
        this.type = type;
        this.numberOfPatients = numberOfPatients;
        this.acceptedBy = acceptedBy;
    }

    protected Emergency(Parcel in) {
        id = in.readString();
        type = in.readString();
        numberOfPatients = in.readString();
        acceptedBy = in.readString();
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getNumberOfPatients() {
        return numberOfPatients;
    }

    public String getAcceptedBy() {
        return acceptedBy;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(type);
        dest.writeString(numberOfPatients);
        dest.writeString(acceptedBy);
    }
}
