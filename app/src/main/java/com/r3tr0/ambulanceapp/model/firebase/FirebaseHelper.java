package com.r3tr0.ambulanceapp.model.firebase;

import com.r3tr0.ambulanceapp.model.models.User;

public class FirebaseHelper {
    public static final String REGEX_NAME = "[A-Z][a-z]+";
    public static final String REGEX_EMAIL = "[A-Za-z_.]+@[a-z]+(\\.[a-z]+)+";
    public static final String REGEX_PASSWORD = "\\w{4,}";
    public static final String REGEX_PHONE = "[0-9\\s]+";

    //Checking flags
    public static final int CHECK_VALID = 0;
    public static final int CHECK_FIRST_NAME = 1;
    public static final int CHECK_LAST_NAME = 2;
    public static final int CHECK_EMAIL = 3;
    public static final int CHECK_PASSWORD = 4;
    public static final int CHECK_PHONE = 5;


    public static int isUserReady(User user){

        if (!user.getFirstName().matches(REGEX_NAME))
            return CHECK_FIRST_NAME;

        if (!user.getLastName().matches(REGEX_NAME))
            return CHECK_LAST_NAME;

        if (!user.getEmail().matches(REGEX_EMAIL))
            return CHECK_EMAIL;

        if (!user.getPassword().matches(REGEX_PASSWORD))
            return CHECK_PASSWORD;

        if (!user.getPhoneNumber().matches(REGEX_PHONE))
            return CHECK_PHONE;

        return CHECK_VALID;
    }

    public static int isUserLoginReady(User user){
        if (!user.getEmail().matches(REGEX_EMAIL))
            return CHECK_EMAIL;

        if (!user.getPassword().matches(REGEX_PASSWORD))
            return CHECK_PASSWORD;

        return CHECK_VALID;
    }
}
