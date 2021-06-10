package com.example.coronaapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("caseRatio")
    @Expose
    private String caseRatio;

    public City(String name, String caseRatio) {
        this.name = name;
        this.caseRatio = caseRatio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaseRatio() {
        return caseRatio;
    }

    public void setCaseRatio(String caseRatio) {
        this.caseRatio = caseRatio;
    }
}
