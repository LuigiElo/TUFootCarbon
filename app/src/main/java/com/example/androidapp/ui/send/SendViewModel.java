package com.example.androidapp.ui.send;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidapp.APIAccess.RequestManager;

public class SendViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    RequestManager rm = RequestManager.getInstance();

    public SendViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Wait...");
        rm.getWaterUsage("bath",3);


    }

    public LiveData<String> getText() {
        mText= rm.getOutput();
    return mText;
    }
}