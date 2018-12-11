package com.r3tr0.ambulanceapp.model.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.r3tr0.ambulanceapp.R;

public class MessageDialog extends Dialog implements View.OnClickListener {
    private ImageView typeImageView;
    private TextView titleTextView;
    private TextView messageTextView;
    private Button button;

    private String title;
    private String message;
    private int type = 0;

    private View.OnClickListener onClickListener;

    public MessageDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        typeImageView = findViewById(R.id.typeImageView);
        titleTextView = findViewById(R.id.titleTextView);
        messageTextView = findViewById(R.id.messageTextView);
        button = findViewById(R.id.button);

        titleTextView.setText(title);
        messageTextView.setText(message);

        if (type == 0) { //Error
            typeImageView.setImageResource(R.drawable.dialog_error);
            button.setBackgroundColor(Color.parseColor("#FFaa0000"));
        } else {
            typeImageView.setImageResource(R.drawable.dialog_finished);
            button.setBackgroundColor(Color.parseColor("#FF00aa00"));
        }

        button.setOnClickListener(this);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onClick(View v) {
        if (onClickListener != null)
            onClickListener.onClick(v);
        dismiss();
    }
}
