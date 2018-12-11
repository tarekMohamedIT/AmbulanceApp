package com.r3tr0.ambulanceapp.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.r3tr0.ambulanceapp.model.dialogs.MessageDialog;

public class BaseAuthActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    public void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(message);
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void hideKeyboard(View view) {
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showMessage(String title, String message, int type) {
        MessageDialog dialog = new MessageDialog(this);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setType(type);
        dialog.show();
    }

    public void showMessage(String title, String message, int type, View.OnClickListener onClickListener) {
        MessageDialog dialog = new MessageDialog(this);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setType(type);
        dialog.setOnClickListener(onClickListener);
        dialog.show();
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }
}
