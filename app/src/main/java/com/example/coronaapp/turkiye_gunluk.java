package com.example.coronaapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class turkiye_gunluk {
    public String patients;
    public String totalPatients;
    public String deaths;
    public String totalDeaths;
    public String recovered;
    public String totalRecovered;
    public String totalIntubated;
    public String totalIntensiveCare;
    public String tests;
    public String totalTests;
    public String date;
    public String critical;
    public String pneumoniaPercent;

    public turkiye_gunluk() {
    }

    public turkiye_gunluk(String patients, String totalPatients, String deaths, String totalDeaths, String recovered, String totalRecovered, String totalIntubated, String totalIntensiveCare, String tests, String totalTests, String date, String critical, String pneumoniaPercent) {
        this.patients = patients;
        this.totalPatients = totalPatients;
        this.deaths = deaths;
        this.totalDeaths = totalDeaths;
        this.recovered = recovered;
        this.totalRecovered = totalRecovered;
        this.totalIntubated = totalIntubated;
        this.totalIntensiveCare = totalIntensiveCare;
        this.tests = tests;
        this.totalTests = totalTests;
        this.date = date;
        this.critical = critical;
        this.pneumoniaPercent = pneumoniaPercent;
    }


    public String getPatients() {
        return patients;
    }

    public void setPatients(String patients) {
        this.patients = patients;
    }

    public String getTotalPatients() {
        return totalPatients;
    }

    public void setTotalPatients(String totalPatients) {
        this.totalPatients = totalPatients;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(String totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(String totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public String getTotalIntubated() {
        return totalIntubated;
    }

    public void setTotalIntubated(String totalIntubated) {
        this.totalIntubated = totalIntubated;
    }

    public String getTotalIntensiveCare() {
        return totalIntensiveCare;
    }

    public void setTotalIntensiveCare(String totalIntensiveCare) {
        this.totalIntensiveCare = totalIntensiveCare;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public String getTotalTests() {
        return totalTests;
    }

    public void setTotalTests(String totalTests) {
        this.totalTests = totalTests;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }

    public String getPneumoniaPercent() {
        return pneumoniaPercent;
    }

    public void setPneumoniaPercent(String pneumoniaPercent) {
        this.pneumoniaPercent = pneumoniaPercent;
    }

}
