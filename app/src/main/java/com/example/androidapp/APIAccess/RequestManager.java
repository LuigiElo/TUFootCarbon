package com.example.androidapp.APIAccess;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestManager {


    private static RequestManager instance;
    private MutableLiveData<String> output;
    private MutableLiveData<ItemsResponse> itemsResponse;
    private MutableLiveData<List<String>> itemsLive;
    private List<String> items;
    CarbonAPI carbonAPI;
    String user;
    private int resultStart = 0;
    public static final int RESULT_LIMIT = 100;

    private  RequestManager() {

        output = new MutableLiveData<>();
        itemsResponse = new MutableLiveData<>();
        itemsLive = new MutableLiveData<>();
        carbonAPI = ServiceGenerator.getCarbonAPI();
        user =  ServiceGenerator.getAuthHeader("eloluigi","luigi09");
    }

    public static synchronized RequestManager getInstance() {
        if (instance == null) {
            instance = new RequestManager();
        }
        return instance;
    }
    public MutableLiveData<String> getWaterUsage(String type, int times) {

        Call<CalculationResponse> call = carbonAPI.getWaterUsageCalculation(user,type,times);
        call.enqueue(new Callback<CalculationResponse>() {
            @Override
            public void onResponse(Call<CalculationResponse> call, Response<CalculationResponse> response) {
                if (response.code() == 200) {
                    output.setValue(response.body().getOutput().getAmounts().get(0).getValue()+response.body().getOutput().getAmounts().get(0).getType()+response.body().getOutput().getAmounts().get(0).getUnit());
                }
            }
            @Override
            public void onFailure(Call<CalculationResponse> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong");

            }
        });
        return output;
    }

    public MutableLiveData<String> getElectricityCalculation(String country, int amount) {

        Call<CalculationResponse> call = carbonAPI.getElectricityCalculation(user,country,amount);
        call.enqueue(new Callback<CalculationResponse>() {
            @Override
            public void onResponse(Call<CalculationResponse> call, Response<CalculationResponse> response) {
                if (response.code() == 200) {
                    output.setValue(response.body().getOutput().getAmounts().get(0).getValue()+response.body().getOutput().getAmounts().get(0).getType()+response.body().getOutput().getAmounts().get(0).getUnit());
                }
            }
            @Override
            public void onFailure(Call<CalculationResponse> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong");

            }
        });
        return output;
    }

    public MutableLiveData<List<String>> getCountriesForElectricityCalculation(){
        Call<ItemsResponse> call = carbonAPI.getElectricityCountriesForCalculation(user,resultStart,RESULT_LIMIT);
        //this part is skipped entirely when I debug
        call.enqueue(new Callback<ItemsResponse>() {
            @Override
            public void onResponse(Call<ItemsResponse> call, Response<ItemsResponse> response) {
                if (response.code() == 200) {
                    itemsResponse.setValue(response.body());
                    Log.d("Retrift111111111111111",response.body().getStatus());

                }
            }
            @Override
            public void onFailure(Call<ItemsResponse> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong getting the countries");

            }
        });

        System.out.println(itemsResponse.getValue().getItemsLabels().toString());
        return itemsLive;
    }





    public MutableLiveData<String> getOutput() {
        return output;
    }

    public List<String> getAirportByCountry(){
        Call<ItemsResponse> call = carbonAPI.getAirportsByCode(user,resultStart,RESULT_LIMIT);
        call.enqueue(new Callback<ItemsResponse>() {
            @Override
            public void onResponse(Call<ItemsResponse> call, Response<ItemsResponse> response) {
                if (response.code() == 200) {
                    itemsResponse.setValue(response.body());
                    Log.d("Retrift111111111111111",response.body().getStatus());

                }
            }
            @Override
            public void onFailure(Call<ItemsResponse> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong getting the countries");

            }
        });

        if(resultStart == 0) {
            items = itemsResponse.getValue().getItemsLabels();
        } else {
            for (int i = 0 ;i <itemsResponse.getValue().getItems().size();i++){
                items.add(itemsResponse.getValue().getItems().get(i).getLabel());
            }
        }

        if(itemsResponse.getValue().getResultsTruncated()){
            resultStart = resultStart + 100;
            getAirportByCountry();
        }else {
            resultStart = 0;
        }

        return items;
    }

    public MutableLiveData<String> getFlightCalculation(String iataCode1, String iataCode2,boolean isReturn,String passengerClass,int journeys) {

        Call<CalculationResponse> call = carbonAPI.getFlightCalculation(user,iataCode1,iataCode2,isReturn,passengerClass,journeys);
        call.enqueue(new Callback<CalculationResponse>() {
            @Override
            public void onResponse(Call<CalculationResponse> call, Response<CalculationResponse> response) {
                if (response.code() == 200) {
                    output.setValue(response.body().getOutput().getAmounts().get(1).getValue()+response.body().getOutput().getAmounts().get(0).getType()+response.body().getOutput().getAmounts().get(0).getUnit());
                }
            }
            @Override
            public void onFailure(Call<CalculationResponse> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong");

            }
        });
        return output;
    }

    public MutableLiveData<List<String>> getWaterUsageTypes(){
        Call<ItemsResponse> call = carbonAPI.getWaterUsageTypes(user);
        call.enqueue(new Callback<ItemsResponse>() {
            @Override
            public void onResponse(Call<ItemsResponse> call, Response<ItemsResponse> response) {
                if (response.code() == 200) {
                    itemsResponse.setValue(response.body());
                    itemsLive.setValue(itemsResponse.getValue().getItemsLabels());
                }
            }
            @Override
            public void onFailure(Call<ItemsResponse> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong getting the countries");

            }
        });
        return itemsLive;
    }


    public MutableLiveData<List<String>> getItemsLive() {
        return itemsLive;
    }

}
