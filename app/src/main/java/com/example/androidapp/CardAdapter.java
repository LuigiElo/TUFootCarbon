package com.example.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    private Context context;


    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item,parent,false);

        context = parent.getContext();


        return new CardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        CarbonEmissions current = ce.get(position);

        //animation for the image
        holder.imageView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition));

        holder.container.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition));

        try {
            String image = current.getType();
            switch(image){
                case "flights":
                    holder.imageView.setImageResource(R.drawable.ic_plane);
                    break;
                case "electricity":
                    holder.imageView.setImageResource(R.drawable.ic_lightbulb);
                    break;
                case "water":
                    holder.imageView.setImageResource(R.drawable.ic_water);
                    break;
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date dateDate = current.getDate();
            String date = formatter.format(dateDate);
            System.out.println(holder.dateTextView.getText());
            System.out.println(current.getSum());
            holder.dateTextView.setText(date);


        }catch(Exception e) {
            e.printStackTrace();

        }

        holder.valueTextView.setText(current.getSum() + "CO2 kg/year");
        holder.descriptionTextView.setText(current.getDescription());


    }

    @Override
    public int getItemCount() {
        return ce.size();
    }

    public void setCards(List<CarbonEmissions> ce){
        this.ce = ce;
        notifyDataSetChanged();
    }

    public CarbonEmissions getCarbonEmissionAt(int position){
        return ce.get(position);
    }

    class CardHolder extends RecyclerView.ViewHolder{
        private TextView valueTextView;
        private TextView dateTextView;
        private TextView descriptionTextView;
        private ImageView imageView;
        private RelativeLayout container;


        public CardHolder(View itemView){
            super(itemView);
            container = itemView.findViewById(R.id.relativeLayout);
            valueTextView = (TextView) itemView.findViewById(R.id.values_textview);
            dateTextView = (TextView)  itemView.findViewById(R.id.date_textView);
            descriptionTextView = (TextView) itemView.findViewById(R.id.description_textView);
            imageView = itemView.findViewById(R.id.cardImage);



        }



    }

    public interface OnDeleteListener{
        void onDeleteClick(int position);
    }



}
