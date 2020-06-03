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
import java.util.Date;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardHolder> {

    private List<CarbonEmissions> ce = new ArrayList<>();

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item,parent,false);



        return new CardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        CarbonEmissions current = ce.get(position);
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date dateDate = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy").parse(current.getDate());
            String date = formatter.format(dateDate);
            System.out.println(holder.dateTextView.getText());
            System.out.println(current.getSum());
            holder.dateTextView.setText(date);
            holder.valueTextView.setText(current.getSum() + "CO2 kg/year");


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
            valueTextView = (TextView) itemView.findViewById(R.id.values_textview);
            dateTextView = (TextView)  itemView.findViewById(R.id.date_textView);
            delete = itemView.findViewById(R.id.delete);



        }



    }



}
