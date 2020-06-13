package com.example.androidapp.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "carbon_emissions")
public class CarbonEmissions {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "user")
    private String user;
    @ColumnInfo(name = "value")
    private float sum;
    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo (name ="description")
    private String description;

    //having it type date was creating some problems
    @ColumnInfo(name = "date")
    private Date date;

    public CarbonEmissions(String user, float sum, Date date, String type, String description) {
        this.user = user;
        this.sum = sum;
        this.date = date;
        this.type = type;
        this.description = description;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
