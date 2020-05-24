package com.example.androidapp.ui.send;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.androidapp.R;

public class WaterUsageFragment extends Fragment {

    private WaterUsageViewModel waterUsageViewModel;

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
        return root;
    }
}