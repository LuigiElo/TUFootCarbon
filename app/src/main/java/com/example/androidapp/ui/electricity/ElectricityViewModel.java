package com.example.androidapp.ui.electricity;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidapp.APIAccess.RequestManager;
import com.example.androidapp.Database.CarbonEmissions;
import com.example.androidapp.Database.CarbonRepository;

import java.util.ArrayList;
import java.util.List;

public class ElectricityViewModel extends AndroidViewModel {

    public final static String EMISSION_TYPE_ELECTRICITY = "electricity";
    private MutableLiveData<String> mText;
    private MutableLiveData<List<String>> countries;
    RequestManager rm = RequestManager.getInstance();;
    CarbonRepository cr;

    public ElectricityViewModel(Application app) {
        super(app);
        cr = CarbonRepository.getInstance(app);
        mText = new MutableLiveData<>();
        countries = new MutableLiveData<List<String>>();
        rm.getCountriesForElectricityCalculation();
    }

    public void getElectricityCalculation(String country,int amount){
        rm.getElectricityCalculation(country,amount);
    }

    public LiveData<String> getTextElectricity() {
        mText = rm.getOutput();
        return mText;
    }

    public MutableLiveData<List<String>> getCountries() {
        countries = rm.getItemsLive();
        return countries;
    }


    public LiveData<List<CarbonEmissions>> getAll() {
        return cr.getAllEmissionsByType(EMISSION_TYPE_ELECTRICITY);
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