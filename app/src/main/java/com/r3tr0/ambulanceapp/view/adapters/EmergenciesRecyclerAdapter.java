package com.r3tr0.ambulanceapp.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.r3tr0.ambulanceapp.R;
import com.r3tr0.ambulanceapp.model.events.OnItemClickListener;
import com.r3tr0.ambulanceapp.model.models.Emergency;

import java.util.ArrayList;
import java.util.List;

public class EmergenciesRecyclerAdapter extends RecyclerView.Adapter<EmergenciesRecyclerAdapter.EmergencyViewHolder> {
    private List<Emergency> emergencies;
    private LayoutInflater inflater;

    private OnItemClickListener onItemClickListener;

    public EmergenciesRecyclerAdapter(Context context) {
        this.emergencies = new ArrayList<>();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public EmergencyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new EmergencyViewHolder(inflater.inflate(
                R.layout.view_emergency_main,
                viewGroup,
                false));
    }

    public List<Emergency> getEmergencies() {
        return emergencies;
    }

    public void setEmergencies(List<Emergency> emergencies) {
        this.emergencies = emergencies;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull EmergencyViewHolder emergencyViewHolder, int i) {
        final Integer integer = i;
        emergencyViewHolder.titleTextView.setText(
                emergencies.get(i).getType());

        emergencyViewHolder.infoTextView.setText(
                String.format("Number of patients : %s", emergencies.get(i).getNumberOfPatients()));

        if (onItemClickListener != null)
            emergencyViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, integer);
                }
            });
    }

    @Override
    public int getItemCount() {
        return emergencies.size();
    }

    public class EmergencyViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView infoTextView;

        public EmergencyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.titleTextView = itemView.findViewById(R.id.typeTextView);
            this.infoTextView = itemView.findViewById(R.id.infoTextView);
        }
    }
}
