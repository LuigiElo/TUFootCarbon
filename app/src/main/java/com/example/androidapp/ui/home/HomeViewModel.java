package com.example.androidapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidapp.APIAccess.RequestManager;
import com.example.androidapp.APIAccess.ServiceGenerator;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    RequestManager rm = RequestManager.getInstance();

    public HomeViewModel() {
        mText = new MutableLiveData<>();

        mText.setValue(rm.CARBON_EMISSION_GOAL+" kg/year");
    }

    public LiveData<String> getText() {
        return mText;
    }
}