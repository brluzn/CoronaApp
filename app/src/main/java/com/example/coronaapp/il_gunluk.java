package com.example.coronaapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class il_gunluk {
    @SerializedName("dateRange")
    @Expose
    private String dateRange;
    @SerializedName("cities")
    @Expose
    private List<City> cities = null;

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
