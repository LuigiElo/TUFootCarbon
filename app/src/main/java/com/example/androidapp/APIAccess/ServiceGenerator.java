package com.example.androidapp.APIAccess;

import android.util.Base64;




import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {




    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build();



    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl("https://api.carbonkit.net/3.6/")
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient);

    private static Retrofit retrofit = retrofitBuilder.build();

    private static CarbonAPI carbonAPI = retrofit.create(CarbonAPI.class);



    public static CarbonAPI getCarbonAPI() {
        return carbonAPI;
    }


    public static String getAuthHeader(String user,String password){
        String base = user + ":" + password;
        String auth = "Basic " + Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);
        return  auth;
    }




}
