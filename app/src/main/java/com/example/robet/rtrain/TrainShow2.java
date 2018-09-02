package com.example.robet.rtrain;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainShow2 extends Activity {

    @BindView(R.id.ImgBack)
    ImageView ImgBack;
    @BindView(R.id.etFrom)
    EditText etFrom;
    @BindView(R.id.etTo)
    EditText etTo;
    @BindView(R.id.spTime)
    Spinner spTime;
    @BindView(R.id.btBack)
    Button btBack;
    @BindView(R.id.btNext)
    Button btNext;

    Loading loading;
    HashMap<String, String> map;
    Bundle bundle;
    String[] time, destination, depart;
    ListView listView;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_show_2);
        ButterKnife.bind(this);

        listView = new ListView(this);
        loading = new Loading(this);
        bundle = getIntent().getExtras();
        map = (HashMap<String, String>) bundle.get("extra");

        loading.start();
        RestApi.getData().CityList().enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(@NonNull Call<CityResponse> call, @NonNull Response<CityResponse> response) {
                int size = Integer.valueOf(response.body().getCity().size());
                destination = new String[size];
                depart = new String[size];
                for(i = 0; i < size; i++){
                    destination[i] = String.valueOf(response.body().getCity().get(i).getName());
                    depart[i] = destination[i];
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RestApi.getData().TimeList().enqueue(new Callback<TimeResponse>() {
            @Override
            public void onResponse(Call<TimeResponse> call, Response<TimeResponse> response) {
                loading.stop();
                int size = Integer.valueOf(response.body().getTime().size());
                time = new String[size];
                for(i = 0; i < size; i++){
                    time[i] = String.valueOf(response.body().getTime().get(i).getTime());
                }
            }

            @Override
            public void onFailure(Call<TimeResponse> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick({R.id.btBack, R.id.btNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btBack:
                break;
            case R.id.btNext:
                break;
        }
    }
}
