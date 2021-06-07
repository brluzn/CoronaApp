package com.example.coronaapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitApi {

    @GET("ozanerturk/covid19-turkey-api/master/dataset/timeline.json")
    Call<List<day>> getModelClass();

}
