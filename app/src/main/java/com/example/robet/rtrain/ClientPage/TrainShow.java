package com.example.robet.rtrain.ClientPage;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.adapter.TrainAdapter;
import com.example.robet.rtrain.gson.TrainResponse;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.robet.rtrain.R.color.colorPrimary;
import static com.example.robet.rtrain.R.color.indots3;

public class TrainShow extends AppCompatActivity {

    Loading loading;
    TrainAdapter adapter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.topEkonomi)
    LinearLayout topEkonomi;
    @BindView(R.id.ekonomi)
    LinearLayout ekonomi;
    @BindView(R.id.topBisnis)
    LinearLayout topBisnis;
    @BindView(R.id.bisnis)
    LinearLayout bisnis;
    @BindView(R.id.topExpress)
    LinearLayout topExpress;
    @BindView(R.id.express)
    LinearLayout express;
    @BindView(R.id.navigation)
    LinearLayout navigation;

    String category, date;
    int day, month, year, date1, date2;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_show);
        ButterKnife.bind(this);

        loading = new Loading(this);
        adapter = new TrainAdapter();
        category = "none";

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        date1 = (year * 10000) + (month * 100) + day;

        if (date == null || date.isEmpty()) {
            date =  day + "-" + month + "-" + year;
        }

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(adapter);

        loading.start();
        RestApi.getData().trainShow().enqueue(new Callback<TrainResponse>() {
            @Override
            public void onResponse(Call<TrainResponse> call, Response<TrainResponse> response) {
                loading.stop();
                adapter.listTrain.addAll(response.body().getTrain());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TrainResponse> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick({R.id.ekonomi, R.id.bisnis, R.id.express, R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ekonomi:

                topEkonomi.setBackground(getDrawable(indots3));
                topBisnis.setBackground(getDrawable(colorPrimary));
                topExpress.setBackground(getDrawable(colorPrimary));
                category = "ekonomi";
                adapter.listTrain.clear();
                adapter.notifyDataSetChanged();
                trainSearch(date, category);

                break;
            case R.id.bisnis:

                topEkonomi.setBackground(getDrawable(colorPrimary));
                topBisnis.setBackground(getDrawable(indots3));
                topExpress.setBackground(getDrawable(colorPrimary));
                category = "bisnis";
                adapter.listTrain.clear();
                adapter.notifyDataSetChanged();
                trainSearch(date, category);

                break;
            case R.id.express:

                topEkonomi.setBackground(getDrawable(colorPrimary));
                topBisnis.setBackground(getDrawable(colorPrimary));
                topExpress.setBackground(getDrawable(indots3));
                category = "express";
                adapter.listTrain.clear();
                adapter.notifyDataSetChanged();
                trainSearch(date, category);

                break;
            case R.id.fab:

                pickDate();

                break;
        }
    }

    private void pickDate() {

        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {

                year = mYear;
                month = mMonth + 1;
                day = mDay;
                date2 = (year * 10000) + (month * 100) + day;

                if(date1 > date2){

                    Toast.makeText(getApplicationContext(), "anda harus memilih tanggal dengan benar",
                            Toast.LENGTH_SHORT).show();

                } else {
                    date = year + "-" + month + "-" + day;
                    adapter.listTrain.clear();
                    adapter.notifyDataSetChanged();
                    trainSearch(date, category);
                }

            }

        },year, calendar.get(Calendar.MONTH), day).show();

    }

    private void trainSearch(String mDate, String mCategory) {

        loading.start();
        RestApi.getData().TrainSearch(mDate,mCategory).enqueue(new Callback<TrainResponse>() {
            @Override
            public void onResponse(Call<TrainResponse> call, Response<TrainResponse> response) {
                loading.stop();
                adapter.listTrain.addAll(response.body().getTrain());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TrainResponse> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
