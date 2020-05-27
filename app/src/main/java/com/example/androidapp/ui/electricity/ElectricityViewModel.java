package com.example.androidapp.ui.electricity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidapp.APIAccess.RequestManager;

import java.util.List;

public class ElectricityViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<String>> countries;
    RequestManager rm = RequestManager.getInstance();;

    public ElectricityViewModel() {
        mText = new MutableLiveData<>();
        countries = new MutableLiveData<List<String>>();
        mText.setValue("This is gallery fragment");
        getCountries();



    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<List<String>> getCountries() {
        countries = rm.getCountriesForElectricityCalculation();
        return countries;
    }
}