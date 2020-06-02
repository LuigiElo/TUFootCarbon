package com.example.androidapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.Database.CarbonEmissions;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardHolder> {

    private List<CarbonEmissions> ce = new ArrayList<>();

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);

        return new CardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        CarbonEmissions current = ce.get(position);
        try {
            holder.valueTextView.setText(current.getSum() + "CO2 kg/year");
            holder.dateTextView.setText(current.getDate());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return ce.size();
    }

    public void setCards(List<CarbonEmissions> ce){
        this.ce = ce;
        notifyDataSetChanged();
    }

    class CardHolder extends RecyclerView.ViewHolder{
        private TextView valueTextView;
        private TextView dateTextView;
        private TextView delete;

        public CardHolder(View itemView){
            super(itemView);
            valueTextView = itemView.findViewById(R.id.valuesView);
            dateTextView = itemView.findViewById(R.id.date_textView);

        }

    }



}
