package com.example.coronaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.navigation.NavigationView;


public class DrawerActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView nav_view;
    ActionBarDrawerToggle toggle;
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

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

        //menu itemlerine tıklanınca ne olacak belirliyoruz

        if (item.getItemId()==R.id.action_grafikler){
            Intent intent=new Intent(DrawerActivity.this,GraphActivity.class);
            startActivity(intent);
        }
        if (item.getItemId()==R.id.action_yasaklar){
            Intent intent=new Intent(DrawerActivity.this,MainActivity.class);
            startActivity(intent);
        }

        return false;
    }
}