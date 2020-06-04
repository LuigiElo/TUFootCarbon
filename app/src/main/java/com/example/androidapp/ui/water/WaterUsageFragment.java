package com.example.androidapp.ui.water;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.CardAdapter;
import com.example.androidapp.Database.CarbonEmissions;
import com.example.androidapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.List;

public class WaterUsageFragment extends Fragment{

    private WaterUsageViewModel waterUsageViewModel;
    private MutableLiveData<List<String>> list = new MutableLiveData<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        waterUsageViewModel =
                ViewModelProviders.of(this).get(WaterUsageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_waterusage, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.valuesView);
        final TextView textView = root.findViewById(R.id.text_send);
        final EditText waterText = root.findViewById(R.id.waterText);
        final Spinner spinner = root.findViewById(R.id.spinner);
        final Button button = root.findViewById(R.id.button);



        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setHasFixedSize(true);

        final CardAdapter cardAdapter = new CardAdapter();
        recyclerView.setAdapter(cardAdapter);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int times = Integer.parseInt(waterText.getText().toString());
                    waterUsageViewModel.getWaterUsage(spinner.getSelectedItem().toString(),times);
                    waterUsageViewModel.getText();

                }
                catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(root.getContext(),"Input values",Toast.LENGTH_SHORT).show();
                }
            }
        });


        waterUsageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                try {
                    textView.setText(s);
                    waterUsageViewModel.insert(new CarbonEmissions(FirebaseAuth.getInstance().getCurrentUser().getEmail(), Float.parseFloat(s), Calendar.getInstance().getTime().toString(), "water", spinner.getSelectedItem().toString()));
                    waterText.getText().clear();
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(root.getContext(),"error ocurred",Toast.LENGTH_SHORT).show();
                }
            }
        });

        waterUsageViewModel.getAll().observe(this, new Observer<List<CarbonEmissions>>() {
            @Override
            public void onChanged(List<CarbonEmissions> carbonEmissions) {
                cardAdapter.setCards(carbonEmissions);
            }
        });

        waterUsageViewModel.getSpinnerData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable  List<String> strings) {
                list.setValue(strings);
                try {
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_spinner_item, list.getValue());
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(spinnerArrayAdapter);
                }catch(Exception e ){
                    e.printStackTrace();
                }

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                waterUsageViewModel.delete(cardAdapter.getCarbonEmissionAt(viewHolder.getAdapterPosition()));
                Toast.makeText(root.getContext(), "Emission deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        return root;
    }


}