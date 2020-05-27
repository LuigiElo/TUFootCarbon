package com.example.androidapp.ui.water;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.androidapp.R;

import java.util.ArrayList;
import java.util.List;

public class WaterUsageFragment extends Fragment {

    private WaterUsageViewModel waterUsageViewModel;
    private MutableLiveData<List<String>> list = new MutableLiveData<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        waterUsageViewModel =
                ViewModelProviders.of(this).get(WaterUsageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_waterusage, container, false);
        final TextView textView = root.findViewById(R.id.text_send);
        waterUsageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        waterUsageViewModel.getSpinnerData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable  List<String> strings) {
                list.setValue(strings);
                try {
                    Spinner spinner = root.findViewById(R.id.spinner);
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_spinner_item, list.getValue());
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(spinnerArrayAdapter);
                }catch(Exception e ){
                    e.printStackTrace();
                }

            }
        });





        return root;
    }
}