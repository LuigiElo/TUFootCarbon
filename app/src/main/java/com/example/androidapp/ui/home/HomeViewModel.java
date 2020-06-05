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

    private MutableLiveData<String> mText;
    RequestManager rm = RequestManager.getInstance();
    CarbonRepository cr;


    public HomeViewModel(Application app) {
        super(app);
        cr = CarbonRepository.getInstance(app);

        mText = new MutableLiveData<>();

        String s = cr.getEmissionTotal().getValue()+"";
        mText.setValue(s);

    }

    public LiveData<String> getText() {

        return mText;
    }
}