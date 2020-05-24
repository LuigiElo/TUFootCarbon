package com.example.androidapp.APIAccess;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface CarbonAPI {

    @Headers("Accept: application/json")
    @GET("categories/Usage/calculation")
    Call<CalculationResponse> getWaterUsageCalculation(@Header ("Authorization")String auth ,
                                                       @Query("type") String type,
                                                       @Query("values.usesPerDay") int uses);

    @Headers("Accept: application/json")
    @GET("categories/Usage/items;label")
    Call<ItemsResponse> getWaterUsageTypes(@Header ("Authorization")String auth);



    @Headers("Accept: application/json")
    @GET("categories/Electricity/calculation")
    Call<CalculationResponse> getElectricityCalculation(@Header ("Authorization")String auth ,
                                                        @Query("country") String country,
                                                        @Query("values.energyConsumption") int amount);

    @Headers("Accept: application/json")
    @GET("categories/Electricity/items;label")
    Call<ItemsResponse> getElectricityCountriesForCalculation(@Header ("Authorization")String auth ,
                                                              @Query("resultStart") int resultStart ,
                                                              @Query("resultLimit") int resultLimit);


    @Headers("Accept: application/json")
    @GET("categories/Airports_by_country/items;label")
    Call<ItemsResponse> getAirportsByCode(@Header ("Authorization")String auth ,
                                          @Query("resultStart") int resultStart ,
                                          @Query("resultLimit") int resultLimit);




    @Headers("Accept: application/json")
    @GET("categories/Great_Circle_flight_methodology/calculation")
    Call<CalculationResponse> getFlightCalculation(@Header ("Authorization")String auth ,
                                             @Query("values.IATACode1") String iataCode1,
                                             @Query("values.IATACode2") String iataCode2,
                                             @Query("values.isReturn") boolean isReturn,
                                             @Query("values.passengerClass") String passengerClass,
                                             @Query("values.journeys") int journeys);




}


