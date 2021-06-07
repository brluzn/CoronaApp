package com.example.coronaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GraphActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater=LayoutInflater.from(this);
        View view=inflater.inflate(R.layout.activity_graph,null,false);
        drawer.addView(view,1);


        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        RetrofitApi retrofitApi=retrofit.create(RetrofitApi.class);


        Call<List<day>> call=retrofitApi.getModelClass();
        call.enqueue(new Callback<List<day>>() {
            @Override
            public void onResponse(Call<List<day>> call, Response<List<day>> response) {
                System.out.println("Vsasdda");
                if (!response.isSuccessful())
                    System.out.println("V");
                List<day> list=response.body();
                System.out.println("Veri"+list.get(0));

            }

            @Override
            public void onFailure(Call<List<day>> call, Throwable t) {
                Toast.makeText(GraphActivity.this, "Veriler Alınamadı", Toast.LENGTH_SHORT).show();
            }
        });

    }
}