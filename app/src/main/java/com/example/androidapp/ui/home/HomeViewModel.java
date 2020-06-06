package com.example.androidapp.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidapp.APIAccess.RequestManager;
import com.example.androidapp.APIAccess.ServiceGenerator;
import com.example.androidapp.Database.CarbonRepository;

public class HomeViewModel extends AndroidViewModel {


    public final static float CARBON_EMISSION_GOAL = 2100;
    public final static float CARBON_EMISSION_AVERAGE = 5100;
    private LiveData<Float> mText;
    private LiveData<Float> sum;
    RequestManager rm = RequestManager.getInstance();
    CarbonRepository cr;


    public HomeViewModel(Application app) {
        super(app);
        cr = CarbonRepository.getInstance(app);
        mText = new MutableLiveData<>();





    }

    public LiveData<Float> getText() {
        mText = cr.getEmissionTotal();
        return mText;
    }






}