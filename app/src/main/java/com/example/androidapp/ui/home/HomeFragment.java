package com.example.androidapp.ui.home;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.androidapp.APIAccess.CarbonAPI;
import com.example.androidapp.APIAccess.ServiceGenerator;
import com.example.androidapp.R;

import static java.lang.Thread.sleep;

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
        good.setAnimation(AnimationUtils.loadAnimation(root.getContext(),R.anim.fade_scale));


        final ImageView trees = root.findViewById(R.id.trees);
        final AnimationDrawable animation = (AnimationDrawable) trees.getDrawable();
        animation.start();

        homeViewModel.getText().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(@Nullable Float s) {
                try {
                    if (s > homeViewModel.CARBON_EMISSION_GOAL) {
                        good.setImageResource(R.drawable.ic_global_warming);
                        textView.setTextColor(Color.parseColor("#5F021F"));


                    } else {
                        good.setImageResource(R.drawable.ic_love);
                        textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    }
                    textView.setText("My CO2 footprint: " + s + " Kg CO2/year");
                }catch(Exception e) {
                    textView.setText("My CO2 footprint: 0 Kg CO2/year");
                    Toast.makeText(root.getContext(),"No values yet",Toast.LENGTH_SHORT);
                }
            }
        });




        return root;
    }


}