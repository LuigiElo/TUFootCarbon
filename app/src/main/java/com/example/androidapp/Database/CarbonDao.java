package com.example.androidapp.Database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface CarbonDao {

    @Insert
    void insert(CarbonEmissions carbonEmissions);

    @Update
    void update(CarbonEmissions carbonEmissions);

    @Delete
    void delete(CarbonEmissions carbonEmissions);

    @Query("DELETE FROM carbon_emissions")
    void deleteAll();


    @Query("SELECT * FROM carbon_emissions ORDER BY id DESC")
    LiveData<List<CarbonEmissions>> getAllEmissions();


    //Does not work
    @Query("SELECT sum(value) FROM carbon_emissions")
    LiveData<Float> getEmissionTotal();



    @Query("SELECT * FROM carbon_emissions WHERE type LIKE :type ORDER BY id DESC")
    LiveData<List<CarbonEmissions>> getAllEmissionsByType(String type);


    @Query("SELECT * FROM carbon_emissions WHERE date BETWEEN :from AND :to")
    LiveData<List<CarbonEmissions>> getMonthlyEmissions(Date from, Date to);

}
