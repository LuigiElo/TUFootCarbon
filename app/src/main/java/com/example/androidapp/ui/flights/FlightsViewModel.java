package com.example.androidapp.ui.flights;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidapp.APIAccess.RequestManager;
import com.example.androidapp.Database.CarbonEmissions;
import com.example.androidapp.Database.CarbonRepository;

import java.util.Arrays;
import java.util.List;

public class FlightsViewModel extends AndroidViewModel {

    public final static String EMISSION_TYPE_FLIGHTS = "flights";
    private MutableLiveData<String> mText;
    RequestManager rm = RequestManager.getInstance();
    CarbonRepository cr;
    private MutableLiveData<List<String>> spinnerData;




    public FlightsViewModel(Application app) {
        super(app);
        cr = CarbonRepository.getInstance(app);
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
        spinnerData = new MutableLiveData<>();
        rm.getAirportByCountry();
    }

    public LiveData<String> getText() {
        mText = rm.getOutput();
        return mText;
    }

    public MutableLiveData<List<String>> getFlightClasses() {

        return  rm.getFlightClasses();
    }

    public void getFLightsCalculation(String iataCode1,String iataCode2,boolean isReturn,String passengerClass,int jouneys){
        iataCode1 = iataCode1.substring(iataCode1.indexOf("[")+1, iataCode1.indexOf("]"));
        iataCode2 = iataCode2.substring(iataCode2.indexOf("[")+1, iataCode2.indexOf("]"));
        rm.getFlightCalculation(iataCode1,iataCode2,isReturn,passengerClass,jouneys);
    }


    public MutableLiveData<List<String>> getSpinnerData(){
        spinnerData= rm.getItemsLiveFlights() ;
        return spinnerData;
    }

    public LiveData<List<CarbonEmissions>> getAll() {
        return cr.getAllEmissionsByType(EMISSION_TYPE_FLIGHTS);
    }

    public void insert(CarbonEmissions carbonEmissions){
        cr.insert(carbonEmissions);
    }

    public void delete(CarbonEmissions carbonEmissions){
        cr.delete(carbonEmissions);
    }



    public void deleteAll() {
        cr.deleteAllNotes();
    }




 }