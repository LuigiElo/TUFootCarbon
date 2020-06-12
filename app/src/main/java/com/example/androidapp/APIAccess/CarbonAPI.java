package com.example.androidapp.APIAccess;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * This class contains the methods to consume the API
 * using the retrofit framework
 *
 */
public interface CarbonAPI {


    /**
     * Consumes the CarbonKit API requesting a calculation of the water usage
     *
     * @param  auth
     *         user for authorization
     * @param  type
     *         type of water usage
     * @param  uses
     *         amount of uses of a type of water usage
     *
     * @return CalculationResponse with the calculation result
     *
     * @see "https://www.carbonkit.net/categories/Usage"
     */
    @Headers("Accept: application/json")
    @GET("categories/Usage/calculation")
    Call<CalculationResponse> getWaterUsageCalculation(@Header ("Authorization")String auth ,
                                                       @Query("type") String type,
                                                       @Query("values.usesPerDay") double uses);

    /**
     * Consumes the CarbonKit API requesting a list of the different water usage types
     *
     * @param  auth
     *         user for authorization
     *
     * @return ItemsResponse with the correspondent list of water usage types
     *
     * @see "https://www.carbonkit.net/categories/Usage"
     */
    @Headers("Accept: application/json")
    @GET("categories/Usage/items;label")
    Call<ItemsResponse> getWaterUsageTypes(@Header ("Authorization")String auth);



    /**
     * Consumes the CarbonKit API requesting a list
     * of the countries available for calculation.
     *
     * @param  auth
     *         user for authorization
     *
     * @param  country
     *         country where the user lives
     *
     * @param  amount
     *         amount of electricity used in kwh
     *
     * @return CalculationResponse with the result of the calculation
     *
     * @see "https://www.carbonkit.net/categories/Electricity"
     */
    @Headers("Accept: application/json")
    @GET("categories/Electricity/calculation")
    Call<CalculationResponse> getElectricityCalculation(@Header ("Authorization")String auth ,
                                                        @Query("country") String country,
                                                        @Query("values.energyConsumption") double amount);


    /**
     * Consumes the CarbonKit API requesting a list
     * of the countries available for calculation.
     *
     * @param  auth
     *         user for authorization
     *
     * @param  resultStart
     *         first element of the dataset to be taken
     *
     * @param  resultLimit
     *         max numbers of elements of the dataset to be taken
     *
     * @return ItemsResponse with the correspondent list of Countries available for calculation
     *
     * @see "https://www.carbonkit.net/categories/Electricity"
     */
    @Headers("Accept: application/json")
    @GET("categories/Electricity/items;label")
    Call<ItemsResponse> getElectricityCountriesForCalculation(@Header ("Authorization")String auth ,
                                                              @Query("resultStart") int resultStart ,
                                                              @Query("resultLimit") int resultLimit);



    /**
     * Consumes the CarbonKit API requesting a list of airports
     *
     * @param  auth
     *         user for authorization
     *
     * @param  resultStart
     *         first element of the dataset to be taken
     *
     * @param  resultLimit
     *         max numbers of elements of the dataset to be taken
     *
     * @return ItemsResponse with the correspondent list of airports
     *
     * @see "https://www.carbonkit.net/categories/Airports_by_country"
     */
    @Headers("Accept: application/json")
    @GET("categories/Airports_by_country/items;label")
    Call<ItemsResponse> getAirportsByCode(@Header ("Authorization")String auth ,
                                          @Query("resultStart") int resultStart ,
                                          @Query("resultLimit") int resultLimit);





    /**
     * Consumes the CarbonKit API requesting the calculation for a given flight
     *
     * @param  auth
     *         user for authorization
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
     * @return CalculationResponse with the calculation result
     *
     * @see "https://www.carbonkit.net/categories/Great_Circle_flight_methodology"
     */
    @Headers("Accept: application/json")
    @GET("categories/Great_Circle_flight_methodology/calculation")
    Call<CalculationResponse> getFlightCalculation(@Header ("Authorization")String auth ,
                                             @Query("values.IATACode1") String iataCode1,
                                             @Query("values.IATACode2") String iataCode2,
                                             @Query("values.isReturn") boolean isReturn,
                                             @Query("values.passengerClass") String passengerClass,
                                             @Query("values.journeys") int journeys);





}


