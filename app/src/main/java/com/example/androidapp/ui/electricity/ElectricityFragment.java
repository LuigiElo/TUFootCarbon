package com.example.androidapp.ui.electricity;

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

import java.util.List;

public class ElectricityFragment extends Fragment {

    private ElectricityViewModel electricityViewModel;
    private MutableLiveData<List<String>> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        electricityViewModel =
                ViewModelProviders.of(this).get(ElectricityViewModel.class);
        View root = inflater.inflate(R.layout.fragment_electricity, container, false);
        final TextView textView = root.findViewById(R.id.text_electricity);
        electricityViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }

        });

        list = new MutableLiveData<>();



        return root;
    }
}