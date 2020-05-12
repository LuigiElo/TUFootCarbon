package com.example.androidapp.APIAccess;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface CarbonAPI {

    @Headers("Accept: application/json")
    @GET("categories/Usage/calculation")
    Call<CalculationResponse> getWaterUsageCalculation(@Header ("Authorization")String auth , @Query("type") String type, @Query("values.usesPerDay") int uses);

    @Headers("Accept: application/json")
    @GET("categories/Electricity/calculation")
    Call<CalculationResponse> getElectricityCalculation(@Header ("Authorization")String auth , @Query("country") String country, @Query("values.energyConsumption") int amount);

    @Headers("Accept: application/json")
    @GET("categories/Electricity/items;label")
    Call<ItemsResponse> getElectricityCountriesForCalculation(@Header ("Authorization")String auth , @Query("resultStart") int resultStart , @Query("resultLimit") int resultLimit);

    @Headers("Accept: application/json")
    @GET("categories/All_airports_by_code/items;label")
    Call<ItemsResponse> getAirportsByCode(@Header ("Authorization")String auth , @Query("resultStart") int resultStart , @Query("resultLimit") int resultLimit);



}


