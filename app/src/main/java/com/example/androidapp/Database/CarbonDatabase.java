package com.example.androidapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {CarbonEmissions.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class CarbonDatabase extends RoomDatabase {

    private static CarbonDatabase instance;

    public abstract CarbonDao noteDao();

    public static synchronized CarbonDatabase getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CarbonDatabase.class, "carbon_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
