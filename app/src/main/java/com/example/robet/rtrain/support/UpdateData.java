package com.example.robet.rtrain.support;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

import com.example.robet.rtrain.gson.CityResponse;
import com.example.robet.rtrain.gson.TimeResponse;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateData {

    ACProgressFlower loading;
    Context context;
    Config config;
    String[] time, city;

    public UpdateData(Context context) {
        this.context = context;
        this.config = new Config(context);
        loading = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Mini Update")
                .fadeColor(Color.DKGRAY)
                .build();

    }

    public void update(){
        RestApi.getData().CityList().enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {

                int size = response.body().getCity().size();
                city = new String[size];
                for(int i = 0; i < size; i++){
                    city[i] = response.body().getCity().get(i).getName();
                }
                config.setCity(city);
                getData2();

            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData2(){
        RestApi.getData().TimeList().enqueue(new Callback<TimeResponse>() {
            @Override
            public void onResponse(Call<TimeResponse> call, Response<TimeResponse> response) {

                int size = response.body().getTime().size();
                time = new String[size];
                for(int i = 0; i < size; i++){
                    time[i] = response.body().getTime().get(i).getTime();
                }
                config.setTime(time);
                config.setUpdated(true);

            }

            @Override
            public void onFailure(Call<TimeResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
