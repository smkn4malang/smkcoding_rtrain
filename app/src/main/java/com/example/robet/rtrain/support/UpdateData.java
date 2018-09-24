package com.example.robet.rtrain.support;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.example.robet.rtrain.gson.CityResponse;
import com.example.robet.rtrain.gson.ItemResponse;
import com.example.robet.rtrain.gson.TimeResponse;

import java.util.HashMap;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateData {

    ACProgressFlower loading;
    Context context;
    Config config;
    String[] time, city, id, name, price, pic, desc;
    HashMap<String, String[]> map = new HashMap<>();

    public UpdateData(Context context) {
        this.context = context;
        this.config = new Config(context);
        loading = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Downloading....")
                .textSize(20)
                .fadeColor(Color.DKGRAY)
                .build();
    }

    public void update(){

        loading.show();

        RestApi.getData().ItemShow().enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {

                int size = response.body().getItem().size();
                id = new String[size];
                name = new String[size];
                price = new String[size];
                desc = new String[size];
                pic = new String[size];

                for(int i = 0; i < size; i++){
                    id[i] = response.body().getItem().get(i).getId();
                    name[i] = response.body().getItem().get(i).getName();
                    price[i] = response.body().getItem().get(i).getPrice();
                    desc[i] = response.body().getItem().get(i).getDescription();
                    pic[i] = response.body().getItem().get(i).getPic();
                }

                map.put("id", id);
                map.put("name", name);
                map.put("price", price);
                map.put("desc", desc);
                map.put("pic", pic);

                config.setItem(map);

                getData1();

            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                loading.dismiss();
                onFailed();
            }
        });
    }

    private void getData1(){
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
                loading.dismiss();
                onFailed();
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
                loading.dismiss();

            }

            @Override
            public void onFailure(Call<TimeResponse> call, Throwable t) {
                loading.dismiss();
                onFailed();
            }
        });
    }

    private void onFailed(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("gagal download data");
        builder.setMessage("mohon periksa jaringan anda dan \nulangi mendownload data lagi");
        builder.setPositiveButton("Download", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                update();
            }
        }).setNegativeButton("later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                config.setUpdated(false);
                ((Activity) context).finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
