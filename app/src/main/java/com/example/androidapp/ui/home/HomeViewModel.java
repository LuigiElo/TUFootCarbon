package com.example.androidapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidapp.APIAccess.Amount;
import com.example.androidapp.APIAccess.Output;
import com.example.androidapp.APIAccess.RequestManager;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    RequestManager rm;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("GAY");
    }

    public LiveData<String> getText() {
        return mText;
    }
}