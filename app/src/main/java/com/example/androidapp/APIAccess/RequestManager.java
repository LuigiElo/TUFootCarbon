package com.example.androidapp.APIAccess;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestManager {



    private static RequestManager instance;
    private MutableLiveData<String> output;
    private MutableLiveData<ItemsResponse> itemsResponse;
    private MutableLiveData<List<String>> itemsLive;
    private MutableLiveData<List<String>> itemsLiveFlights;
    private List<String> items;
    CarbonAPI carbonAPI;
    String user;
    private int resultStart = 0;
    public static final int RESULT_LIMIT = 100;


    private  RequestManager() {

        output = new MutableLiveData<>();
        itemsResponse = new MutableLiveData<>();
        itemsLive = new MutableLiveData<>();
        itemsLiveFlights = new MutableLiveData<>();
        items= new ArrayList<String>();
        carbonAPI = ServiceGenerator.getCarbonAPI();

        //Its using a specific account,but in the future each user should have its own.
        user =  ServiceGenerator.getAuthHeader("eloluigi","luigi09");
    }

    public static synchronized RequestManager getInstance() {
        if (instance == null) {
            instance = new RequestManager();
        }
        return instance;
    }



    /**
     * Consumes the CarbonKit API requesting a calculation of the water usage
     *
     * @param  type
     *         type of water usage
     *
     * @param  times
     *         amount of uses of a type of water usage
     *
     * @return String with the value ready to be passed to the ViewModel
     *
     * @see {@link com.example.androidapp.APIAccess.CarbonAPI#getWaterUsageCalculation(String, String, double)}
     */
    public MutableLiveData<String> getWaterUsage(String type, double times) {

        Call<CalculationResponse> call = carbonAPI.getWaterUsageCalculation(user,type,times);
        call.enqueue(new Callback<CalculationResponse>() {
            @Override
            public void onResponse(Call<CalculationResponse> call, Response<CalculationResponse> response) {
                if (response.code() == 200) {
                    output.setValue(response.body().getOutput().getAmounts().get(0).getValue()+"");
                }
            }
            @Override
            public void onFailure(Call<CalculationResponse> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong");

            }
        });
        return output;
    }


    /**
     * Consumes the CarbonKit API requesting a calculation of the electricity
     *
     * @param  country
     *         country where the user lives
     *
     * @param  amount
     *         amount of  electricity used in kwh
     *
     * @return String with the value ready to be passed to the ViewModel
     *
     * @see {@link com.example.androidapp.APIAccess.CarbonAPI#getElectricityCalculation(String, String, double)}
     */
    public MutableLiveData<String> getElectricityCalculation(String country, double amount) {

        Call<CalculationResponse> call = carbonAPI.getElectricityCalculation(user,country,amount);
        call.enqueue(new Callback<CalculationResponse>() {
            @Override
            public void onResponse(Call<CalculationResponse> call, Response<CalculationResponse> response) {
                if (response.code() == 200) {
                    output.setValue(response.body().getOutput().getAmounts().get(0).getValue()+"");
                }
            }
            @Override
            public void onFailure(Call<CalculationResponse> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong");

            }
        });
        return output;
    }




    /**
     *    Consumes the CarbonKit API requesting a list of countries to be used in the calculation.
     *
     *    This method uses recursion to get all the elements since the API
     * can only handle a request of 100 elements at a time and there are about 150 countries in this list.
     *
     *    When the max number of results has been reached,
     * the response item contains "ResultsTruncated" with the value false,
     * which stops the recursion, thus the list is complete.
     *
     * @return List with the countries ready to be displayed in a spinner
     *
     * @see {@link com.example.androidapp.APIAccess.CarbonAPI#getElectricityCountriesForCalculation(String, int, int)}
     */    public MutableLiveData<List<String>> getCountriesForElectricityCalculation(){
        Call<ItemsResponse> call = carbonAPI.getElectricityCountriesForCalculation(user,resultStart,RESULT_LIMIT);
        //this part is skipped entirely when I debug
        call.enqueue(new Callback<ItemsResponse>() {
            @Override
            public void onResponse(Call<ItemsResponse> call, Response<ItemsResponse> response) {
                if (response.code() == 200) {
                    itemsResponse.setValue(response.body());
                    if(resultStart == 0) {
                        items = itemsResponse.getValue().getItemsLabels();
                    } else {
                        for (int i = 0 ;i <itemsResponse.getValue().getItems().size();i++){
                            items.add(itemsResponse.getValue().getItems().get(i).getLabel());
                        }
                    }

                    if(itemsResponse.getValue().getResultsTruncated()){
                        resultStart = resultStart + 100;
                        getCountriesForElectricityCalculation();
                    }else {
                        resultStart = 0;
                    }

                    itemsLive.setValue(items);
                }
            }
            @Override
            public void onFailure(Call<ItemsResponse> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong getting the countries");

            }
        });

        return itemsLive;
    }





    public MutableLiveData<String> getOutput() {
        return output;
    }


    /**
     *    Consumes the CarbonKit API requesting a list of airports
     *
     *    This method uses recursion to get all the elements since the API
     * can only handle a request of 100 elements at a time and there are about 500 airports in this list.
     *
     *    When the max number of results has been reached,
     * the response item contains "ResultsTruncated" with the value false,
     * which stops the recursion, thus the list is complete.
     *
     * @return List with the airports ready to be displayed in a spinner
     *
     * @see {@link com.example.androidapp.APIAccess.CarbonAPI#getAirportsByCode(String, int, int)}
     */
    public MutableLiveData<List<String>> getAirportByCountry(){
        Call<ItemsResponse> call = carbonAPI.getAirportsByCode(user,resultStart,RESULT_LIMIT);
        call.enqueue(new Callback<ItemsResponse>() {
            @Override
            public void onResponse(Call<ItemsResponse> call, Response<ItemsResponse> response) {
                if (response.code() == 200) {
                    itemsResponse.setValue(response.body());
                    if(resultStart == 0) {
                        System.out.println(response.body().getItems().get(0).getLabel());
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
                    itemsLiveFlights.setValue(items);


                }
            }
            @Override
            public void onFailure(Call<ItemsResponse> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong getting the countries");

            }
        });
        return itemsLiveFlights;
    }


    /**
     * Consumes the CarbonKit API requesting the calculation for a given flight
     *
     * @param  iataCode1
     *         Airport of departure
     *
     * @param  iataCode2
     *         Airport of arrival
     *
     * @param  isReturn
     *         Is the flight a round trip
     *
     * @param  passengerClass
     *         Passenger class i.e. "economy"
     *
     * @param  journeys
     *         number of times this flight was performed
     *
     * @return String with the value ready to be passed to the ViewModel
     *
     * @see {@link com.example.androidapp.APIAccess.CarbonAPI#getFlightCalculation(String, String, String, boolean, String, int)}
     */
    public MutableLiveData<String> getFlightCalculation(String iataCode1, String iataCode2,boolean isReturn,String passengerClass,int journeys) {

        Call<CalculationResponse> call = carbonAPI.getFlightCalculation(user,iataCode1,iataCode2,isReturn,passengerClass,journeys);
        call.enqueue(new Callback<CalculationResponse>() {
            @Override
            public void onResponse(Call<CalculationResponse> call, Response<CalculationResponse> response) {
                if (response.code() == 200) {
                    output.setValue(response.body().getOutput().getAmounts().get(1).getValue()+"");
                }
            }
            @Override
            public void onFailure(Call<CalculationResponse> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong");

            }
        });
        return output;
    }

    /**
     *    Consumes the CarbonKit API requesting a list of airports
     *
     *    This method uses recursion to get all the elements since the API
     * can only handle a request of 100 elements at a time and there are about 500 airports in this list.
     *
     *    When the max number of results has been reached,
     * the response item contains "ResultsTruncated" with the value false,
     * which stops the recursion, thus the list is complete.
     *
     * @return List with the types of water to displayed
     *
     * @see {@link com.example.androidapp.APIAccess.CarbonAPI#getWaterUsageTypes(String)}
     */
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
                Log.i("Retrofit", "Something went wrong getting the water types");

            }
        });
        return itemsLive;
    }


    public MutableLiveData<List<String>> getItemsLive() {
        return itemsLive;
    }

    public MutableLiveData<List<String>> getItemsLiveFlights() {
        return itemsLiveFlights;
    }


    public MutableLiveData<List<String>> getFlightClasses() {
        List<String> list = Arrays.asList(new String[]{"average", "economy", "premium economy", "business", "first"});
        MutableLiveData<List<String>> values = new MutableLiveData<>();
        values.setValue(list);
        return values;
    }
}
