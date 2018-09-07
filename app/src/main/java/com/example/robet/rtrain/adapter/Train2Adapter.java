package com.example.robet.rtrain.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.robet.rtrain.ClientPage.SeatPick;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.gson.CartResponse;
import com.example.robet.rtrain.support.Config;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.support.Value;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Train2Adapter {

    Context context;
    Config config;
    String[] city, time;
    AutoCompleteTextView etDepart, etDestination;
    Spinner spTime, spCart;
    HashMap<String, String> map;
    LayoutInflater layoutInflater;
    View view;
    ArrayAdapter<String> adapter;
    Loading loading;
    String[] cart;
    Button btBack, btNext;
    boolean departStat, destinationStat;

    public Train2Adapter(Context mCtx) {

        this.config = new Config(mCtx);
        this.context = mCtx;
        this.city = config.getCity();
        this.time = config.getTime();
        this.loading = new Loading(mCtx);

    }

    public void create(HashMap<String, String> mMap) {

        this.map = mMap;
        layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.train_show_2, null);
        getCart();

        etDepart = view.findViewById(R.id.etTo);
        etDestination = view.findViewById(R.id.etFrom);
        spCart = view.findViewById(R.id.spCart);
        spTime = view.findViewById(R.id.spTime);
        btBack = view.findViewById(R.id.btBack);
        btNext = view.findViewById(R.id.btNext);

    }

    public void build() {
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, cart);
        spCart.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, city);
        etDestination.setAdapter(adapter);
        etDepart.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, time);
        spTime.setAdapter(adapter);
    }

    public void show() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setCancelable(false);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String mTime, mDestination, mDepart, mCart, mTrainId, mDate, mCategory;

                mTrainId = map.get("trainId");
                mDate = map.get("date");
                mCategory = map.get("category");
                mTime = spTime.getSelectedItem().toString();
                mDepart = etDepart.getText().toString();
                mDestination = etDestination.getText().toString();
                mCart = spCart.getSelectedItem().toString();

                if (mDepart.equals("") && mDestination.equals("")) {
                    Toast.makeText(context, "isi data dengan benar", Toast.LENGTH_SHORT).show();
                } else {

                    for (int i = 0; i < city.length; i++) {

                        if (mDepart.equals(String.valueOf(city[i]))) {
                            departStat = true;
                        }

                        if (mDestination.equals(String.valueOf(city[i]))) {
                            destinationStat = true;
                        }
                    }

                    if (!departStat) {
                        Toast.makeText(context, "pilih kota asal dengan benar", Toast.LENGTH_SHORT).show();
                    } else if (!destinationStat) {
                        Toast.makeText(context, "pilih kota tujuan dengan benar", Toast.LENGTH_SHORT).show();
                    } else if (mDepart.equals(mDestination)) {
                        Toast.makeText(context, "anda harus memilih kota yang berbeda", Toast.LENGTH_SHORT).show();
                    } else {
                        String[] data = {mTrainId, mDate, mTime, mCategory, mDestination, mDepart, mCart};
                        if(!getStatus(data)){
                            Toast.makeText(context, "gerbong tersebut telah penuh", Toast.LENGTH_SHORT).show();
                        } else {
                            map.put("time", mTime);
                            map.put("depart", mDepart);
                            map.put("destination", mDestination);
                            map.put("cart", mCart);

                            Intent intent = new Intent(context, SeatPick.class);
                            intent.putExtra("extra", map);
                            view.getContext().startActivity(intent);
                        }
                    }
                }
            }
        });

    }

    private void getCart() {
        loading.start();
        RestApi.getData().cartShow(map.get("trainId")).enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                loading.stop();
                int size = response.body().getCart().size();
                String[] data = new String[size];
                for (int i = 0; i < size; i++) {
                    data[i] = "Gerbong ";
                    data[i] += String.valueOf(response.body().getCart().get(i).getNum());
                }
                cart = data;

            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                loading.stop();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean getStatus(String[] mData) {

        loading.start();
        RestApi.getData().trainStatus(mData[0], mData[1], mData[2], mData[3], mData[4], mData[5], mData[6]).enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                loading.stop();
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                loading.stop();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return true;
    }
}
