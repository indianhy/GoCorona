package com.fricsoft.gocorona;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {

    ArrayList<State> states;

    public StateAdapter(ArrayList<State> states) {
        this.states = states;
    }

    @NonNull
    @Override
    public StateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.country_card, parent, false);

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull StateAdapter.ViewHolder holder, int position) {
        State country = states.get(position);
        holder.cases.setText(country.getCases());
        holder.active.setText(country.getActive());
        holder.recovered.setText(country.getRecovered());
        holder.deaths.setText(country.getDeaths());
        holder.country.setText(country.getState());
        holder.flag.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cases, country, active, recovered, deaths;
        ImageView flag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cases = itemView.findViewById(R.id.cases);
            country = itemView.findViewById(R.id.country);
            active = itemView.findViewById(R.id.active);
            recovered = itemView.findViewById(R.id.cured);
            deaths = itemView.findViewById(R.id.deaths);
            flag = itemView.findViewById(R.id.flag);
        }
    }
}