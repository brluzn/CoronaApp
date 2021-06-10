package com.example.coronaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
    ProgressDialog progressDialog;
    TextView text_yasak1,text_yasak2,text_yasak3,text_yasak4,text_yasak5,text_yasak6,text_yasak7,text_yasak8,text_yasak9,text_yasak10,text_yasak11,text_yasak12,text_yasak13;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater=LayoutInflater.from(this);
        View view=inflater.inflate(R.layout.activity_main,null,false);
        drawer.addView(view,0);

        spinner_iller=findViewById(R.id.spinner_iller);
        il_vaka_sayisi_textView=findViewById(R.id.il_vaka_sayisi_textView);
        il_neleryasak_text=findViewById(R.id.il_neleryasak_text);
        List<int[]> nufus=Arrays.asList(getResources().getIntArray(R.array.cityPopulationList));//illerin nufüs bilgileri string.xml den alındı
        //System.out.println(nufus.get(0)[0]);

        ArrayAdapter<CharSequence> arrayAdapter=ArrayAdapter.createFromResource(this,R.array.cityArrayList,R.layout.style_spinner);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_iller.setAdapter(arrayAdapter);

        spinner_iller.setOnItemSelectedListener(this);



        ///iller retrofit işlemleri




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println(position);
        /*il_vaka_sayisi_textView.setText(parent.getItemAtPosition(position).toString()+"  : 88.78");
        il_neleryasak_text.setText(parent.getItemAtPosition(position).toString());*/
        String il_isim=parent.getItemAtPosition(position).toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://covid-turkey-case-ratio.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


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
                            Yasaklar_Neler(il_veri.getCities().get(i).getCaseRatio(),i);
                        }
                    }
                }


                progressDialog.dismiss();

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

    public void Yasaklar_Neler(String vaka_sayisi,int pos){

        int vaka=Integer.parseInt(vaka_sayisi);
        if (0<=vaka && vaka<20){
            //düşük riskli il
            Yasaklar_setText("Serbest ","Yasak ","Serbest ","Serbest ","Açık ","Açık ","Açık","Açık ","Açık "
                    ,"07:00 - 19:00 Açık","09:00 - 19:00 Açık","Normal ","100 kisiye kadar 1 saat" );
        }
        else if (20<=vaka && vaka<50){
            //orta riskli il
            Yasaklar_setText("Serbest ","Yasak ","Serbest ","Serbest ","Açık ","Açık ","Açık","Açık ","Açık "
                    ,"07:00 - 19:00 Açık","09:00 - 19:00 Açık","Normal ","100 kisiye kadar 1 saat" );
        }
        else if (50<=vaka && vaka<100){
            //yüksek riskli il
            Yasaklar_setText("Pazar Yasak ","Yasak ","10.00-14.00 arası serbest ","14.00-18.00 arası serbest ","Açık "
                    ,"Açık ","Açık","Kapalı  ","Yüz yüze sınav ","07:00 - 19:00 Açık","Kapalı","Normal ","50 kisiye kadar 1 saat" );

        }
        else if (vaka>100){
            //çok yüksek riskli il

            Yasaklar_setText("Serbest ","Yasak ","Serbest ","Serbest ","Açık ","Açık ","Açık","Açık ","Açık "
                    ,"Kapal","Kapal","Normal ","50 kisiye kadar 1 saat" );
        }



    }


    //yasakların yazdırılmasını sağlayan fonsksiyon
    public void Yasaklar_setText(String y1,String y2,String y3,String y4,String y5,String y6
            ,String y7,String y8,String y9,String y10,String y11,String y12,String y13){

        text_yasak1=(findViewById(R.id.text_yasak1));
        text_yasak2=(findViewById(R.id.text_yasak2));
        text_yasak3=(findViewById(R.id.text_yasak3));
        text_yasak4=(findViewById(R.id.text_yasak4));
        text_yasak5=(findViewById(R.id.text_yasak5));
        text_yasak6=(findViewById(R.id.text_yasak6));   //yasakların yazılacakları textleri(activity_main.xml) eşleştirdik
        text_yasak7=(findViewById(R.id.text_yasak7));
        text_yasak8=(findViewById(R.id.text_yasak8));
        text_yasak9=(findViewById(R.id.text_yasak9));
        text_yasak10=(findViewById(R.id.text_yasak10));
        text_yasak11=(findViewById(R.id.text_yasak11));
        text_yasak12=(findViewById(R.id.text_yasak12));
        text_yasak13=(findViewById(R.id.text_yasak13));
        text_yasak1.setText(y1);
        text_yasak2.setText(y2);
        text_yasak3.setText(y3);
        text_yasak4.setText(y4);
        text_yasak5.setText(y5);
        text_yasak6.setText(y6);                       //yasakları yazdırma işlemi
        text_yasak7.setText(y7);
        text_yasak8.setText(y8);
        text_yasak9.setText(y9);
        text_yasak10.setText(y10);
        text_yasak11.setText(y11);
        text_yasak12.setText(y12);
        text_yasak13.setText(y13);


    }






}