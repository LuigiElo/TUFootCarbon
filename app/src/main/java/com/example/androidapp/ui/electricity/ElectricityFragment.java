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

        //RecyclerView Setup
        final RecyclerView recyclerView = root.findViewById(R.id.electricityValuesView);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setHasFixedSize(true);
        final CardAdapter cardAdapter = new CardAdapter();
        recyclerView.setAdapter(cardAdapter);


        electricityViewModel.getTextElectricity().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                try {
                    textViewElectricity.setText(s);
                    electricityViewModel.insert(new CarbonEmissions(FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), Float.parseFloat(s), Calendar.getInstance().getTime().toString(), electricityViewModel.EMISSION_TYPE_ELECTRICITY, countriesSpinner.getSelectedItem().toString()));
                    editText.getText().clear();
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(root.getContext(),"error ocurred",Toast.LENGTH_SHORT).show();
                }
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

        electricityViewModel.getAll().observe(this, new Observer<List<CarbonEmissions>>() {
            @Override
            public void onChanged(List<CarbonEmissions> carbonEmissions) {
                cardAdapter.setCards(carbonEmissions);
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



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                electricityViewModel.delete(cardAdapter.getCarbonEmissionAt(viewHolder.getAdapterPosition()));
                Toast.makeText(root.getContext(), "Emission deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);





        return root;
    }
}