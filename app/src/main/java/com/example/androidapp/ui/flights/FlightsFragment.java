package com.example.androidapp.ui.flights;

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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class FlightsFragment extends Fragment {

    private FlightsViewModel flightsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        flightsViewModel =
                ViewModelProviders.of(this).get(FlightsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_flights, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        final Button button = root.findViewById(R.id.buttonFlights);
        final EditText journeys = root.findViewById(R.id.journeysText);
        final Switch isReturnSwitch = root.findViewById(R.id.isReturnSwitch);
        final ImageView loading = root.findViewById(R.id.loading);
        final AnimationDrawable animation = (AnimationDrawable) loading.getDrawable();

        final InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        final Spinner departureSpinner = root.findViewById(R.id.departureSpinner);


        final Spinner arrivalSpinner = root.findViewById(R.id.arrivalSpinner);


        final Spinner classSpinner = root.findViewById(R.id.classSpinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_spinner_item, flightsViewModel.getFlightClasses().getValue());
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(spinnerArrayAdapter);

        //RecyclerView Setup
        final RecyclerView recyclerView = root.findViewById(R.id.flightsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setHasFixedSize(true);
        final CardAdapter cardAdapter = new CardAdapter();
        recyclerView.setAdapter(cardAdapter);

        flightsViewModel.getSpinnerData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable  List<String> strings) {
                loading.setVisibility(View.VISIBLE);
                animation.start();

                try {
                    ArrayAdapter<String> spinnerArrivalAdapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_spinner_item,strings);
                    spinnerArrivalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    arrivalSpinner.setAdapter(spinnerArrivalAdapter);
                    ArrayAdapter<String> spinnerDepartureAdapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_spinner_item, strings);
                    spinnerDepartureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    departureSpinner.setAdapter(spinnerDepartureAdapter);
                }catch(Exception e ){
                    e.printStackTrace();
                }
                animation.stop();
                loading.setVisibility(View.INVISIBLE);
            }
        });

        flightsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if(!departureSpinner.getSelectedItem().toString().equals(arrivalSpinner.getSelectedItem().toString())) {
                    textView.setText(s);
                    flightsViewModel.insert(new CarbonEmissions(FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), Float.parseFloat(s), Calendar.getInstance().getTime().toString(),flightsViewModel.EMISSION_TYPE_FLIGHTS , departureSpinner.getSelectedItem().toString() + " - " + arrivalSpinner.getSelectedItem().toString()));
                }
                journeys.getText().clear();
                animation.stop();
                loading.setVisibility(View.INVISIBLE);
            }
        });

        flightsViewModel.getAll().observe(this, new Observer<List<CarbonEmissions>>() {
            @Override
            public void onChanged(List<CarbonEmissions> carbonEmissions) {
                cardAdapter.setCards(carbonEmissions);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mgr.hideSoftInputFromWindow(journeys.getWindowToken(), 0);
                loading.setVisibility(View.VISIBLE);
                animation.start();
                try{

                    String iataCode1 = departureSpinner.getSelectedItem().toString();
                    String iataCode2 = arrivalSpinner.getSelectedItem().toString();
                    String passengerClass = classSpinner.getSelectedItem().toString();
                    boolean isReturn = isReturnSwitch.isChecked();
                    int journey = Integer.parseInt(journeys.getText().toString());
                    flightsViewModel.getFLightsCalculation(iataCode1,iataCode2,isReturn,passengerClass,journey);
                    flightsViewModel.getText();

                }
                catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(root.getContext(),"Input values",Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.INVISIBLE);
                    animation.stop();
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
                flightsViewModel.delete(cardAdapter.getCarbonEmissionAt(viewHolder.getAdapterPosition()));
                Toast.makeText(root.getContext(), "Emission deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);





        return root;
    }
}