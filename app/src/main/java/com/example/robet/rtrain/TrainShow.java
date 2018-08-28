package com.example.robet.rtrain;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;
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
    int day, month, year;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_show);
        ButterKnife.bind(this);

        loading = new Loading(this);
        adapter = new TrainAdapter();

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        if (date == null || date.isEmpty()) {
            date =  day + "-" + month + "-" + year;
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

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
                trainSearch(category, date);


                break;
            case R.id.bisnis:

                topEkonomi.setBackground(getDrawable(colorPrimary));
                topBisnis.setBackground(getDrawable(indots3));
                topExpress.setBackground(getDrawable(colorPrimary));
                category = "bisnis";
                trainSearch(category, date);

                break;
            case R.id.express:

                topEkonomi.setBackground(getDrawable(colorPrimary));
                topBisnis.setBackground(getDrawable(colorPrimary));
                topExpress.setBackground(getDrawable(indots3));
                category = "express";
                trainSearch(category, date);

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

                date = mYear + "-" + mMonth + "-" + mDay;

                trainSearch(date, category);

            }

        },year, month, day).show();

    }

    private void trainSearch(String date, String category) {

        adapter.listTrain.clear();
        loading.start();
        RestApi.getData().TrainSearch(date, category).enqueue(new Callback<TrainResponse>() {
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
