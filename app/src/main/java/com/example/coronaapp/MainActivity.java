package com.example.coronaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
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

import static com.example.coronaapp.R.*;
import static com.example.coronaapp.R.id.text_gunluk_ortalama;

public class MainActivity extends DrawerActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner_iller;
    TextView il_vaka_sayisi_textView;
    TextView il_neleryasak_text;
    static il_gunluk il_veri;
    ProgressDialog progressDialog;
    TextView text_yasak1,text_yasak2,text_yasak3,text_yasak4,text_yasak5,text_yasak6,text_yasak7,text_yasak8,text_yasak9,text_yasak10,text_yasak11,text_yasak12,text_yasak13;
    public static List<City> iller;
    TextView text_gunluk_ortalama;
    TextView text_haftalik_ortalama;
    TextView text_risk_durumu;

    public City c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater=LayoutInflater.from(this);
        View view=inflater.inflate(layout.activity_main,null,false);
        drawer.addView(view,0);

        spinner_iller=findViewById(id.spinner_iller);
        il_vaka_sayisi_textView=findViewById(id.il_vaka_sayisi_textView);
        il_neleryasak_text=findViewById(id.il_neleryasak_text);

        //System.out.println(nufus.get(0)[0]);

        ArrayAdapter<CharSequence> arrayAdapter=ArrayAdapter.createFromResource(this, array.cityArrayList, layout.style_spinner);
        arrayAdapter.setDropDownViewResource(layout.support_simple_spinner_dropdown_item);
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
                progressDialog=new ProgressDialog(MainActivity.this);
                progressDialog.show();
                progressDialog.setContentView(layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                progressDialog.dismiss();
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
                            il_vaka_sayisi_textView.setText(ratio);
                            il_neleryasak_text.setText(name);
                            c=new City(name,ratio);

                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    String result = ratio.split(",")[0]+"."+ratio.split(",")[1];
                                    // Stuff that updates the UI
                                    Yasaklar_Neler(result);
                                    System.out.println("sayi" + result);
                                    Vaka_Sayilari(result,position);

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





        Veri veri=new Veri();
        veri.execute();
        progressDialog.dismiss();




    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void Yasaklar_Neler(String vaka_sayisi){
        text_risk_durumu=findViewById(id.text_risk_durumu);

        float vaka= Float.parseFloat(vaka_sayisi);
        if (0<=vaka && vaka<20){
            //düşük riskli il
            Yasaklar_setText("Serbest ","Yasak ","Serbest ","Serbest ","Açık ","Açık ","Açık ","Açık ","Açık "
                    ,"07:00 - 19:00 Açık","09:00 - 19:00 Açık","Normal ","100 kisiye kadar 1 saat" );
            text_risk_durumu.setText("DÜŞÜK RİSKLİ");
            text_risk_durumu.setTextColor(Color.BLUE);
        }
        else if (20<=vaka && vaka<50){
            //orta riskli il
            Yasaklar_setText("Serbest ","Yasak ","Serbest ","Serbest ","Açık ","Açık ","Açık ","Açık ","Açık "
                    ,"07:00 - 19:00 Açık","09:00 - 19:00 Açık","Normal ","100 kisiye kadar 1 saat" );
            text_risk_durumu.setText("ORTA RİSKLİ");
            text_risk_durumu.setTextColor(Color.YELLOW);
        }
        else if (50<=vaka && vaka<100){
            //yüksek riskli il
            Yasaklar_setText("Pazar Yasak ","Yasak ","10.00-14.00 arası serbest ","14.00-18.00 arası serbest ","Açık "
                    ,"Açık ","Açık ","Kapalı ","Yüz yüze sınav ","07:00 - 19:00 Açık","Kapalı ","Normal ","50 kisiye kadar 1 saat" );

            text_risk_durumu.setText("YÜKSEK RİSKLİ");
            text_risk_durumu.setTextColor(getResources().getColor(color.orange));
        }
        else if (vaka>100){
            //çok yüksek riskli il

            Yasaklar_setText("Serbest ","Yasak ","10.00-14.00 arası serbest ","14.00-18.00 arası serbest ","Açık ","Açık ","Açık ","Açık ","Açık "
                    ,"Kapalı ","Kapalı ","Normal ","50 kisiye kadar 1 saat" );

            text_risk_durumu.setText("ÇOK YÜKSEK RİSKLİ");
            text_risk_durumu.setTextColor(getResources().getColor(color.orange));
        }





    }


    //yasakların yazdırılmasını sağlayan fonsksiyon
    public void Yasaklar_setText(String y1,String y2,String y3,String y4,String y5,String y6
            ,String y7,String y8,String y9,String y10,String y11,String y12,String y13){

        text_yasak1=(findViewById(id.text_yasak1));
        text_yasak2=(findViewById(id.text_yasak2));
        text_yasak3=(findViewById(id.text_yasak3));
        text_yasak4=(findViewById(id.text_yasak4));
        text_yasak5=(findViewById(id.text_yasak5));
        text_yasak6=(findViewById(id.text_yasak6));   //yasakların yazılacakları textleri(activity_main.xml) eşleştirdik
        text_yasak7=(findViewById(id.text_yasak7));
        text_yasak8=(findViewById(id.text_yasak8));
        text_yasak9=(findViewById(id.text_yasak9));
        text_yasak10=(findViewById(id.text_yasak10));
        text_yasak11=(findViewById(id.text_yasak11));
        text_yasak12=(findViewById(id.text_yasak12));
        text_yasak13=(findViewById(id.text_yasak13));
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

        {
            if (y1.equals("Serbest ")){
                text_yasak1.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable.ic_baseline_check_circle_24, 0);
            }else{
                text_yasak1.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable.ic_baseline_cancel_24, 0);
            }

            if (!y2.equals("Yasak ")){
                text_yasak2.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable.ic_baseline_check_circle_24, 0);
            }else{
                text_yasak2.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable.ic_baseline_cancel_24, 0);
            }

            if (y3.equals("Serbest ")){
                text_yasak3.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable.ic_baseline_check_circle_24, 0);
            }else{
                text_yasak3.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable.ic_baseline_cancel_24, 0);
            }
            if (y4.equals("Serbest ")){
                text_yasak4.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable.ic_baseline_check_circle_24, 0);
            }else{
                text_yasak4.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable.ic_baseline_cancel_24, 0);
            }

            if (y4.equals("Serbest ")){
                text_yasak4.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable.ic_baseline_check_circle_24, 0);
            }else{
                text_yasak4.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable.ic_baseline_cancel_24, 0);
            }

            if (y5.equals("Açık ")){
                text_yasak5.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable.ic_baseline_check_circle_24, 0);
            }else{
                text_yasak5.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable.ic_baseline_cancel_24, 0);
            }

            if (y6.equals("Açık ")){
                text_yasak6.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable.ic_baseline_check_circle_24, 0);
            }else{
                text_yasak6.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable.ic_baseline_cancel_24, 0);
            }
            if (y7.equals("Açık ")){
                text_yasak7.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable.ic_baseline_check_circle_24, 0);
            }else{
                text_yasak7.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable.ic_baseline_cancel_24, 0);
            }
            if (y8.equals("Açık ")){
                text_yasak8.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable.ic_baseline_check_circle_24, 0);
            }else{
                text_yasak8.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable.ic_baseline_cancel_24, 0);
            }
            if (y9.equals("Açık ")){
                text_yasak9.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable.ic_baseline_check_circle_24, 0);
            }else{
                text_yasak9.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable.ic_baseline_cancel_24, 0);
            }

            if (!y10.equals("Kapalı ")){
                text_yasak10.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable.ic_baseline_check_circle_24, 0);
            }else{
                text_yasak10.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable.ic_baseline_cancel_24, 0);
            }
            if (!y11.equals("Kapalı ")){
                text_yasak11.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable.ic_baseline_check_circle_24, 0);
            }else{
                text_yasak11.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable.ic_baseline_cancel_24, 0);
            }
        }




    }


    public void Vaka_Sayilari(String ratio,int pos){
        float vaka_orani= Float.parseFloat(ratio);
        List<int[]> nufus=Arrays.asList(getResources().getIntArray(array.cityPopulationList));//illerin nufüs bilgileri string.xml den alındı

        int gunluk_ortalama=Math.round((nufus.get(0)[pos]/100000)*vaka_orani)/7;
        int haftalik_ortalama=Math.round((nufus.get(0)[pos]/100000)*vaka_orani);
        text_gunluk_ortalama=findViewById(id.text_gunluk_ortalama);
        text_haftalik_ortalama=findViewById(id.text_haftalik_ortalama);

        text_gunluk_ortalama.setText(String.valueOf(gunluk_ortalama));
        text_haftalik_ortalama.setText(String.valueOf(haftalik_ortalama));

        System.out.println("gunluk  "+gunluk_ortalama );
        System.out.println("haftalik  "+haftalik_ortalama );



    }



}