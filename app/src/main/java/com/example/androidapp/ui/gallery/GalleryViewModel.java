package com.example.androidapp.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidapp.APIAccess.RequestManager;

import java.util.ArrayList;
import java.util.List;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<String>> countries;
    RequestManager rm = RequestManager.getInstance();;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        countries = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
         getCountries();


    }

    public LiveData<String> getText() {
        return mText;
    }

    public List<String> getCountries() {
        countries.setValue(rm.getCountriesForElectricityCalculation());
        return countries.getValue();
    }
}