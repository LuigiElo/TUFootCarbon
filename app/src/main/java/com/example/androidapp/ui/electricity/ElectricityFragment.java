package com.example.androidapp.ui.electricity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    private MutableLiveData<List<String>> list = new MutableLiveData<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        electricityViewModel =
                ViewModelProviders.of(this).get(ElectricityViewModel.class);
        View root = inflater.inflate(R.layout.fragment_electricity, container, false);


        final TextView textViewElectricity = root.findViewById(R.id.text_electricity);
        final Spinner countriesSpinner = root.findViewById(R.id.countriesSpinner);
        final Button button3 = root.findViewById(R.id.button3);
        final EditText editText = root.findViewById(R.id.editText);


        electricityViewModel.getTextElectricity().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textViewElectricity.setText(s);
            }

        });

        electricityViewModel.getCountries().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable  List<String> strings) {
                list.setValue(strings);
                try {

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_spinner_item, list.getValue());
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    countriesSpinner.setAdapter(spinnerArrayAdapter);
                }catch(Exception e ){
                    e.printStackTrace();
                }

            }
        });


        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int amount = Integer.parseInt(editText.getText().toString());
                    electricityViewModel.getElectricityCalculation(countriesSpinner.getSelectedItem().toString(),amount);
                    electricityViewModel.getTextElectricity();

                }
                catch(Exception e){
                    e.printStackTrace();
                    textViewElectricity.setText("Input all the values");
                }
            }
        });





        return root;
    }
}