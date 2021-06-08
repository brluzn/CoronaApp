package com.example.coronaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GraphActivity extends DrawerActivity {
    public List<turkiye_gunluk> modelclassList;

    TextView table_tarih1;
         TextView table_vaka,table_iyilesen,table_vefat,table_agirHasta,table_testSayisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.activity_graph, null, false);
        drawer.addView(view, 1);

        System.out.println("sdsdsds");


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        Call<JSONResponse> call = retrofitApi.ModelClass();

        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (!response.isSuccessful())
                {
                    System.out.println("error");
                    Toast.makeText(GraphActivity.this,"error failure",Toast.LENGTH_SHORT).show();
                }

                JSONResponse d =response.body();
                // System.out.println(d.getData());
                modelclassList =new ArrayList<>(Arrays.asList(d.getData()));
                System.out.println(modelclassList.get(modelclassList.size()-1).getDate());

                if (modelclassList.size()>0){
                    Textview_Setups();

                    table_tarih1.setText(modelclassList.get(modelclassList.size()-1).getDate());
                    table_agirHasta.setText(modelclassList.get(modelclassList.size()-1).getCritical());
                    table_iyilesen.setText(modelclassList.get(modelclassList.size()-1).getRecovered());
                    table_testSayisi.setText(modelclassList.get(modelclassList.size()-1).getTests());
                    table_vaka.setText(modelclassList.get(modelclassList.size()-1).getPatients());
                    table_vefat.setText(modelclassList.get(modelclassList.size()-1).getDeaths());
                }

            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Toast.makeText(GraphActivity.this,"error failure",Toast.LENGTH_SHORT).show();
                System.out.println("error");
            }
        });

    }

    public void Textview_Setups(){
        table_tarih1=findViewById(R.id.table_tarih1);
        table_testSayisi=findViewById(R.id.table_testSayisi);
        table_agirHasta=findViewById(R.id.table_agirhasta);
        table_iyilesen=findViewById(R.id.table_iyilesen);
        table_vaka=findViewById(R.id.table_vaka);
        table_vefat=findViewById(R.id.table_vefat); //textviewlerin eşleştirmesini sagladık
    }

}