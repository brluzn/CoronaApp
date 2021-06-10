package com.example.coronaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DrawerActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView nav_view;
    ActionBarDrawerToggle toggle;
    public List<turkiye_gunluk> modelclassList2;    //xml dosyalarında oluşturduğumuz görsel nesneleri (buton ,text vs) tanımlıyoruz
    TextView text_toplam_vaka;
    TextView text_toplam_iyilesen;
    TextView text_toplam_vefat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        drawer=findViewById(R.id.drawer);
        toolbar=findViewById(R.id.toolbar);
        nav_view=findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);
        toggle=new ActionBarDrawerToggle(this,drawer,toolbar,0,0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        nav_view.setNavigationItemSelectedListener(this); //menu itemlerine tıklamayı aktif hale getirdik.



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")          //drawer menude yazan bilgieleri almak için RETROFIT ile verileri çektik
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

                }

                JSONResponse d =response.body();
                // System.out.println(d.getData());
                modelclassList2 =new ArrayList<>(Arrays.asList(d.getData()));
                if (modelclassList2.size()>0){
                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    View headerView = navigationView.getHeaderView(0);
                    text_toplam_iyilesen = (TextView) headerView.findViewById(R.id.text_toplam_iyilesen);
                    text_toplam_vaka = (TextView) headerView.findViewById(R.id.text_toplam_vaka);               //drawer_header a yazılack veriler ve yazdırma işlemleri
                    text_toplam_vefat = (TextView) headerView.findViewById(R.id.text_toplam_vefat);

                    text_toplam_iyilesen.setText(modelclassList2.get(modelclassList2.size()-1).getTotalRecovered());
                    text_toplam_vaka.setText(modelclassList2.get(modelclassList2.size()-1).getTotalPatients());
                    text_toplam_vefat.setText(modelclassList2.get(modelclassList2.size()-1).getTotalDeaths());

                }

            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

                System.out.println("error");
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

        //menu itemlerine tıklanınca ne olacak belirliyoruz

        if (item.getItemId()==R.id.action_grafikler){
            Intent intent=new Intent(DrawerActivity.this,GraphActivity.class);  //grafikler aktivitesine git
            startActivity(intent);
        }
        if (item.getItemId()==R.id.action_yasaklar){
            Intent intent=new Intent(DrawerActivity.this,MainActivity.class);     //yasaklar aktivitesine git
            startActivity(intent);
        }

        return false;
    }
}