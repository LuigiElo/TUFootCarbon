package com.example.androidapp.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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


    @Query("SELECT * FROM carbon_emissions")
    LiveData<List<CarbonEmissions>> getAllEmissions();

    @Query("SELECT SUM(value) FROM carbon_emissions")
    LiveData<Float> getEmissionTotal();

    @Query("SELECT * FROM carbon_emissions WHERE type LIKE :type")
    LiveData<List<CarbonEmissions>> getAllEmissionsByType(String type);

}
