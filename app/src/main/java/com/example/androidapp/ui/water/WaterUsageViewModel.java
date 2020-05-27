package com.example.androidapp.ui.water;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidapp.APIAccess.RequestManager;

import java.util.ArrayList;
import java.util.List;

public class WaterUsageViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<String>> spinnerData;
    RequestManager rm = RequestManager.getInstance();

    public WaterUsageViewModel() {
        mText = new MutableLiveData<>();
        spinnerData = new MutableLiveData<>();
        mText.setValue("Wait...");
        spinnerData.setValue(new ArrayList<>());
        rm.getWaterUsage("bath",3);
        rm.getWaterUsageTypes();



    }

    public LiveData<String> getText() {
        mText = rm.getOutput();
    return mText;
    }

    public MutableLiveData<List<String>> getSpinnerData(){
        spinnerData= rm.getItemsLive() ;
        return spinnerData;
    }
}