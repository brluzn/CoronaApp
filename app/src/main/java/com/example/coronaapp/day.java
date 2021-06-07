package com.example.coronaapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class day {


    @SerializedName("day")
    private turkiye_gunluk turkiyeGunluk;

    public turkiye_gunluk getTurkiyeGunluk() {
        return turkiyeGunluk;
    }

    public void setTurkiyeGunluk(turkiye_gunluk turkiyeGunluk) {
        this.turkiyeGunluk = turkiyeGunluk;
    }
}
