package com.example.coronaapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitApi {

    @GET("brluzn/special_template/master/corona_api")
    Call<JSONResponse> ModelClass();

}
