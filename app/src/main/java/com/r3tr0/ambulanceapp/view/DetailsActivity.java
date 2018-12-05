package com.r3tr0.ambulanceapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.r3tr0.ambulanceapp.R;
import com.r3tr0.ambulanceapp.model.firebase.FirebaseManager;
import com.r3tr0.ambulanceapp.model.models.Emergency;

public class DetailsActivity extends AppCompatActivity {
    TextView headlineTextView;
    TextView infoTextView;
    FirebaseManager manager;
    Emergency emergency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        headlineTextView = findViewById(R.id.headlineTextView);
        infoTextView = findViewById(R.id.infoTextView);

        manager = new FirebaseManager(this);

        emergency = getIntent().getParcelableExtra("emergency");

        if (emergency != null) {
            headlineTextView.setText(String.format("Emergency : %s", emergency.getType()));

            StringBuilder builder = new StringBuilder();

            builder.append("Number of patients : ")
                    .append(emergency.getNumberOfPatients())
                    .append("\n");

            builder.append("Accepted by : ")
                    .append(emergency.getAcceptedBy());

            infoTextView.setText(builder.toString());

        }

    }

    public void accept(View view) {
        manager.setAccepted(emergency.getId(), manager.getActiveUser().getId());
        Toast.makeText(this, "This emergency is accepted.", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void reject(View view) {
        manager.setRejected(emergency.getId());
        finish();
    }
}
