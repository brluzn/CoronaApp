package com.example.coronaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
    public static List<City> iller;

    public City c;
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




         class Veri extends AsyncTask<Void,Void,Void>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                String url="https://covid19.saglik.gov.tr/";

                try {
                    Document doc = Jsoup.connect(url).get();
                    Element table=doc.select("tbody").get(0);

                    Elements rows=table.select("tr");
                    for (Element row:rows){
                        /*String name=row.select("td").get(0).text();
                        String ratio=row.select("td").get(1).text();
                        */

                        if (row.select("td").get(0).text().equals(il_isim)){
                            String name=row.select("td").get(0).text();
                            String ratio=row.select("td").get(1).text();
                            System.out.println("Bu şehir"+name);
                            il_vaka_sayisi_textView.setText("Vaka Sayısı : "+ratio);
                            il_neleryasak_text.setText(name);
                            c=new City(name,ratio);

                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    String result = ratio.split(",")[0];
                                    // Stuff that updates the UI
                                    Yasaklar_Neler(result);
                                    System.out.println("sayi" + result);

                                }
                            });
                        }

                    }





                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }



        progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        iller=new ArrayList<>();
        Veri veri=new Veri();
        veri.execute();
        progressDialog.dismiss();




    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void Yasaklar_Neler(String vaka_sayisi){

        int vaka= Integer.parseInt(vaka_sayisi);
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
                    ,"Kapalı","Kapalı","Normal ","50 kisiye kadar 1 saat" );
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


    public void VerileriAl(){

    }



}