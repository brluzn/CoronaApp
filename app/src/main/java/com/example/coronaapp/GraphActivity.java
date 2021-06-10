package com.example.coronaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;

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

    TextView table_tarih1;                                                                  //xml dosyalarında oluşturduğumuz görsel nesneleri (buton ,text vs) tanımlıyoruz
    TextView table_vaka,table_iyilesen,table_vefat,table_agirHasta,table_testSayisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.activity_graph, null, false);     //burada drawer ı kullandığımız için xml dosyasını infalate ediyoruz
        drawer.addView(view, 1);



        //turkiye geneli vaka bilgilerini RETROFIT kütüphanesi ile çekme işlemleri
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")  //verinin çekileceği BASE_URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);   //retrofit nesnesi oluşturduk
        Call<JSONResponse> call = retrofitApi.ModelClass(); //interface de bulunan fonsksiyonu belirttik

        call.enqueue(new Callback<JSONResponse>() {     //veriyi getirme talebi
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {      //veri adresine bağlanıldı
                if (!response.isSuccessful())   //veri getirme başarısız ise
                {
                    System.out.println("error");
                    Toast.makeText(GraphActivity.this,"error failure",Toast.LENGTH_SHORT).show();
                }

                JSONResponse d =response.body();    //veri getirme başarılı  bütün verileri seçtik

                modelclassList =new ArrayList<>(Arrays.asList(d.getData()));        //bütün verileri tutacağımız dizi

                BarChart(modelclassList);   //bar grafiğini çağırdık
                PieChart(modelclassList);   //pasta grafiğini çağırdık
                LineChart(modelclassList);  //çizgi grafiğini çağırdık
                if (modelclassList.size()>0){       //eğer veriler boş değil ise
                    Textview_Setups();

                    table_tarih1.setText(modelclassList.get(modelclassList.size()-1).getDate());
                    table_agirHasta.setText(modelclassList.get(modelclassList.size()-1).getCritical()); //bilgileri textlere yazdırdık
                    table_iyilesen.setText(modelclassList.get(modelclassList.size()-1).getRecovered());
                    table_testSayisi.setText(modelclassList.get(modelclassList.size()-1).getTests());
                    table_vaka.setText(modelclassList.get(modelclassList.size()-1).getPatients());
                    table_vefat.setText(modelclassList.get(modelclassList.size()-1).getDeaths());


                }

            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {   //bağlantı hatası kontorlü
                Toast.makeText(GraphActivity.this,"error failure",Toast.LENGTH_SHORT).show();
                System.out.println("error");
            }
        });

    }

    public void Textview_Setups(){                           //textviewlerin eşleştirmesini sagladık
        table_tarih1=findViewById(R.id.table_tarih1);
        table_testSayisi=findViewById(R.id.table_testSayisi);
        table_agirHasta=findViewById(R.id.table_agirhasta);
        table_iyilesen=findViewById(R.id.table_iyilesen);
        table_vaka=findViewById(R.id.table_vaka);
        table_vefat=findViewById(R.id.table_vefat);




    }

    public void BarChart(List<turkiye_gunluk> modelclassList){

        BarChart barChart =findViewById(R.id.barChart); //bar grafiğinin id sine erişerek bağlantı kruduk

        ArrayList<BarEntry> visitors=new ArrayList<>(); //kullanılacak verilerin tutacak dizi

        for (int i=0;i < modelclassList.size();i++){    //ne kadar verimiz varsa yani kaç günlük bilgi varsa tek tek ekledik

            String x=modelclassList.get(i).getDate();
            int y=Integer.parseInt(modelclassList.get(i).getTotalPatients());
            visitors.add(new BarEntry(i,y));
        }
        BarDataSet barDataSet=new BarDataSet(visitors,"Günlük Vaka Sayıları");
        barDataSet.setColors(Color.GRAY);               //grafik sutun rengi
        barDataSet.setValueTextColor(Color.BLACK);  //grafik yazı rengi
        barDataSet.setValueTextSize(1f);    //grafik yazı boyutu

        BarData barData=new BarData(barDataSet);    //veri setini oluşturduk

        barChart.setFitBars(true);
        barChart.setData(barData);          //verilei bar grafiğine bağladık
        barChart.getDescription().setText("Türkiye Geneli Hasta Sayisi");
        barChart.animateY(1000);

    }

    public void PieChart(List<turkiye_gunluk> modelclassList){
        PieChart pieChart=findViewById(R.id.pieChart);  //pasta grafiğinin id sine erişerek bağlantı kruduk

        int vefat=Integer.parseInt(modelclassList.get(modelclassList.size()-1).getTotalDeaths());
        int vaka=Integer.parseInt(modelclassList.get(modelclassList.size()-1).getTotalPatients());  //yazdırılacak veriler
        int iyilesen=Integer.parseInt(modelclassList.get(modelclassList.size()-1).getTotalRecovered());

        int total=vefat+vaka+iyilesen;


        ArrayList<PieEntry> entries=new ArrayList<>();
        entries.add(new PieEntry(vaka,"Aktif Vaka"));
        entries.add(new PieEntry(iyilesen,"İyilesen")); //verileri diziye atadık
        entries.add(new PieEntry(vefat,"Vefat"));

        PieDataSet pieDataSet=new PieDataSet(entries,"Turkiye");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);        //pasta grafiği renk
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Türkiye Güncel Durum\n"+modelclassList.get(modelclassList.size()-1).getDate());
        pieChart.animateY(1000, Easing.EaseInCubic);

        pieChart.setDrawHoleEnabled(true);
        Legend legend = pieChart.getLegend();
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);  //Set the legend horizontal display
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP); //top
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT); //Right to it


    }

    public void LineChart(List<turkiye_gunluk> modelclassList){
        LineChart lineChart=findViewById(R.id.lineChart);



        ArrayList<Entry> values1=new ArrayList<>();
        for (int i=0;i<modelclassList.size();i++){                  //birinci çizgi verileri
            values1.add(new Entry(i,Integer.parseInt(modelclassList.get(i).getPatients())));
        }

        LineDataSet gunluk_vaka=new LineDataSet(values1,"Gunluk Hasta Sayısı");
        gunluk_vaka.setColor(Color.RED);                    //birinci çizgi renk
        gunluk_vaka.setCircleColor(Color.RED);




        ArrayList<Entry> values2=new ArrayList<>();                 //ikinci çizgi
        for (int i=0;i<modelclassList.size();i++){
            values2.add(new Entry(i,Integer.parseInt(modelclassList.get(i).getDeaths())));

        }
        LineDataSet gunluk_vefat=new LineDataSet(values2,"Vefat");


        ArrayList<Entry> values3=new ArrayList<>(); //üçüncü çizgi
        for (int i=0;i<modelclassList.size();i++){
            values3.add(new Entry(i,Integer.parseInt(modelclassList.get(i).getRecovered())));
        }

        LineDataSet gunluk_iyilesen=new LineDataSet(values3,"İyileşen");
        gunluk_iyilesen.setColor(Color.GREEN);                  //üçüncü çizgi renk
        gunluk_iyilesen.setCircleColor(Color.GREEN);

        LineData data=new LineData(gunluk_vaka,gunluk_vefat,gunluk_iyilesen);
        lineChart.setData(data);
        lineChart.invalidate();




    }

}