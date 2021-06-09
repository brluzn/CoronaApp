package com.example.coronaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Debug;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

public class MainActivity extends DrawerActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner_iller;
    TextView il_vaka_sayisi_textView;
    TextView il_neleryasak_text;
    static il_gunluk il_veri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater=LayoutInflater.from(this);
        View view=inflater.inflate(R.layout.activity_main,null,false);
        drawer.addView(view,0);

        spinner_iller=findViewById(R.id.spinner_iller);
        il_vaka_sayisi_textView=findViewById(R.id.il_vaka_sayisi_textView);
        il_neleryasak_text=findViewById(R.id.il_neleryasak_text);


        ArrayAdapter<CharSequence> arrayAdapter=ArrayAdapter.createFromResource(this,R.array.cityArrayList,R.layout.style_spinner);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_iller.setAdapter(arrayAdapter);

        spinner_iller.setOnItemSelectedListener(this);


        ///iller retrofit işlemleri




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       // System.out.println(parent.getItemAtPosition(position).toString());
        /*il_vaka_sayisi_textView.setText(parent.getItemAtPosition(position).toString()+"  : 88.78");
        il_neleryasak_text.setText(parent.getItemAtPosition(position).toString());*/
        String il_isim=parent.getItemAtPosition(position).toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://covid-turkey-case-ratio.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        Call<il_gunluk> call = retrofitApi.il_ModelClass();
        call.enqueue(new Callback<il_gunluk>() {
            @Override
            public void onResponse(Call<il_gunluk> call, Response<il_gunluk> response) {

                if (!response.isSuccessful())
                {
                    System.out.println("error");

                }

                if (response.isSuccessful()){
                    il_veri =response.body();


                /*System.out.println(il_veri.getCities().get(1).getName());
                System.out.println(il_veri.getCities().get(1).getCaseRatio());
                System.out.println(il_veri.getCities().size());*/

                    for (int i=0;i<il_veri.getCities().size();i++){
                        if (il_veri.getCities().get(i).getName().equals(il_isim)){
                            System.out.println("Bu şehir"+il_veri.getCities().get(i).getName());
                            il_vaka_sayisi_textView.setText("Vaka Sayısı : "+il_veri.getCities().get(i).getCaseRatio());
                            il_neleryasak_text.setText(il_veri.getCities().get(i).getName());
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<il_gunluk> call, Throwable t) {
                System.out.println("error Failure");
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }






}