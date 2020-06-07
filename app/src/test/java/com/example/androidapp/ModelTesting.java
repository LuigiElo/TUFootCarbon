package com.example.androidapp;

import android.app.Service;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.androidapp.APIAccess.CalculationResponse;
import com.example.androidapp.APIAccess.CarbonAPI;
import com.example.androidapp.APIAccess.ItemsResponse;
import com.example.androidapp.APIAccess.RequestManager;
import com.example.androidapp.APIAccess.ServiceGenerator;
import com.example.androidapp.Database.CarbonRepository;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.*;

/**
 * Since most of the business logic is done by the API, no point in doing local testing.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ModelTesting {

    RequestManager rm = RequestManager.getInstance();

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }





}