package com.example.androidapp.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.androidapp.APIAccess.CarbonAPI;
import com.example.androidapp.APIAccess.ServiceGenerator;
import com.example.androidapp.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        final TextView goal = root.findViewById(R.id.text_home2);
        goal.setText("Target: " + homeViewModel.CARBON_EMISSION_GOAL + " Kg CO2/year");
        final TextView average = root.findViewById(R.id.text_home3);
        average.setText("Global Average: " + homeViewModel.CARBON_EMISSION_AVERAGE + " Kg CO2/year");

        final ImageView good =root.findViewById(R.id.imageView2);
        final ImageView bad =root.findViewById(R.id.imageView3);

        homeViewModel.getText().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(@Nullable Float s) {
                if(s> homeViewModel.CARBON_EMISSION_GOAL){
                    bad.setVisibility(View.VISIBLE);
                    textView.setTextColor(Color.RED);

                }else{
                    good.setVisibility(View.VISIBLE);
                    textView.setTextColor(Color.GREEN);
                }
                textView.setText("My CO2 footprint: "+ s+""+" Kg CO2/year");
            }
        });
        return root;


    }


}