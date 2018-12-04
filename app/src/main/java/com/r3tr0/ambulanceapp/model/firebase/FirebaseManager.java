package com.r3tr0.ambulanceapp.model.firebase;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.r3tr0.ambulanceapp.model.events.OnFirebaseProcessEndListener;
import com.r3tr0.ambulanceapp.model.events.OnValidationProcessEndListener;
import com.r3tr0.ambulanceapp.model.interfaces.ISigningManager;
import com.r3tr0.ambulanceapp.model.models.User;

/**
 * The implementation of the  interface.
 * This class is responsible for the Firebase operations.
 */
public class FirebaseManager implements ISigningManager {
    private static String TAG = "test firebase"; //Tag for debugging.

    private FirebaseAuth firebaseAuth; // The authentication object of the firebase.
    private FirebaseRepository firebaseRepository; //The firebase database repository.

    private Context context;

    private OnFirebaseProcessEndListener onFirebaseProcessEndListener;
    private OnValidationProcessEndListener onValidationProcessEndListener;

    public FirebaseManager(Context context) {
        this.context = context;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseRepository = FirebaseRepository.getInstance();
    }

    public void setOnFirebaseProcessEndListener(OnFirebaseProcessEndListener onFirebaseProcessEndListener) {
        this.onFirebaseProcessEndListener = onFirebaseProcessEndListener;
    }

    public void setOnValidationProcessEndListener(OnValidationProcessEndListener onValidationProcessEndListener) {
        this.onValidationProcessEndListener = onValidationProcessEndListener;
    }

    public LiveData<DataSnapshot> getSnapshot() {
        return firebaseRepository.getMainDataSnapshot();
    }

    /**
     * The Sign in process is done using the
     * {@link FirebaseAuth#signInWithEmailAndPassword(String, String)}
     *
     * then in the OnCompleteListener, The event listeners are added.
     *
     * @param user The user to be logging in.
     * @return The currently logged in FirebaseUser or null if not successful.
     */
    @Override
    public FirebaseUser signIn(User user) {
        return doSignIn(user);
    }

    private FirebaseUser doSignIn(User user){
        int validateID = FirebaseHelper.isUserLoginReady(user);

        if (validateID == 0){
            if (onValidationProcessEndListener != null)
                onValidationProcessEndListener.onSuccess();

            // [START sign_in_with_email]
            firebaseAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                    .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {// Sign in success, update UI with the signed-in user's information

                                Log.d(TAG, "signInWithEmail:success");
                                if (onFirebaseProcessEndListener != null)
                                    onFirebaseProcessEndListener.onSuccess(firebaseAuth.getCurrentUser());
                            }

                            else {// If sign in fails, display a message to the user.

                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                if (onFirebaseProcessEndListener != null)
                                    onFirebaseProcessEndListener.onFail(task.getException());
                            }
                        }
                    });
        }

        else {
            if(onValidationProcessEndListener != null)
                onValidationProcessEndListener.onFail(validateID);
        }

        return firebaseAuth.getCurrentUser();
    }

    /**
     * The Sign in process is done using the
     * {@link FirebaseAuth#createUserWithEmailAndPassword(String, String)}
     *
     * then in the OnCompleteListener, The event listeners are added.
     *
     * @param user The user to be signed up.
     * @return The currently logged in FirebaseUser or null if not successful.
     */
    @Override
    public FirebaseUser signUp(final User user) {
        int validateID = FirebaseHelper.isUserReady(user);

        if (validateID == 0) {
            if (onValidationProcessEndListener != null)
                onValidationProcessEndListener.onSuccess();
            firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                    .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {// Sign in success, update UI with the signed-in user's information

                                Log.d(TAG, "signInWithEmail:success");
                                if (onFirebaseProcessEndListener != null)
                                    onFirebaseProcessEndListener.onSuccess(firebaseAuth.getCurrentUser());
                                firebaseRepository.InsertNew(user);
                            } else {// If sign in fails, display a message to the user.

                                Log.w(TAG, "signInWithEmail:failure", task.getException());

                                if (onFirebaseProcessEndListener != null)
                                    onFirebaseProcessEndListener.onFail(task.getException());
                            }
                        }
                    });
        }

        else {
            if(onValidationProcessEndListener != null)
                onValidationProcessEndListener.onFail(validateID);
        }

        return firebaseAuth.getCurrentUser();
    }

    @Override
    public void signOut(){
        firebaseAuth.signOut();
    }

    public void sendMessage(String message) {
        firebaseRepository.sendMessage(message);
    }


}
