package com.example.androidapp.ui.electricity;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

        //Used to closed the keyboard after input
        final InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        final TextView textViewElectricity = root.findViewById(R.id.text_electricity);
        final Spinner countriesSpinner = root.findViewById(R.id.countriesSpinner);
        final Button button3 = root.findViewById(R.id.button3);
        final EditText editText = root.findViewById(R.id.editText);


        //Animation
        final ImageView loading = root.findViewById(R.id.loading);
        final AnimationDrawable animation = (AnimationDrawable) loading.getDrawable();

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
                    electricityViewModel.insert(new CarbonEmissions(FirebaseAuth.getInstance().getCurrentUser().getEmail(), Float.parseFloat(s), Calendar.getInstance().getTime(), electricityViewModel.EMISSION_TYPE_ELECTRICITY, countriesSpinner.getSelectedItem().toString()));
                    editText.getText().clear();
                }catch(Exception e){
                    e.printStackTrace();
                    if(editText.getText().equals("")){
                    }else{
                    Toast.makeText(root.getContext(),"error ocurred",Toast.LENGTH_SHORT).show();
                    }
                }
                animation.stop();
                loading.setVisibility(View.INVISIBLE);
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

                mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                loading.setVisibility(View.VISIBLE);
                animation.start();
                try{

                    electricityViewModel.getElectricityCalculation(countriesSpinner.getSelectedItem().toString(),editText.getText().toString());
                    electricityViewModel.getTextElectricity();

                }
                catch(Exception e){
                    e.printStackTrace();
                    textViewElectricity.setText("Input all the values");
                    animation.stop();
                    loading.setVisibility(View.INVISIBLE);
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