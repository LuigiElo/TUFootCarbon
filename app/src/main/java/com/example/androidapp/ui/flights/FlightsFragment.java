package com.example.androidapp.ui.flights;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.androidapp.R;

public class FlightsFragment extends Fragment {

    private FlightsViewModel flightsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        flightsViewModel =
                ViewModelProviders.of(this).get(FlightsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_flights, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);

        Spinner classSpinner = root.findViewById(R.id.classSpinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_spinner_item, flightsViewModel.getFlightClasses().getValue());
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(spinnerArrayAdapter);

        flightsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });





        return root;
    }
}