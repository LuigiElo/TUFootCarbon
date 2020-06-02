package com.example.androidapp.ui.flights;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Arrays;
import java.util.List;

public class FlightsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FlightsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<List<String>> getFlighClasses() {
        List<String> list = Arrays.asList(new String[]{"average", "economy", "premium economy", "business", "first"});
        MutableLiveData<List<String>> returnValue = new MutableLiveData<>();
        returnValue.setValue(list);
        return returnValue;

    }
 }