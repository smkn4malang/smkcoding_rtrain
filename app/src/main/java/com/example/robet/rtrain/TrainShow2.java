package com.example.robet.rtrain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
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
    AutoCompleteTextView etFrom;
    @BindView(R.id.etTo)
    AutoCompleteTextView etTo;
    @BindView(R.id.spTime)
    Spinner spTime;
    @BindView(R.id.btBack)
    Button btBack;
    @BindView(R.id.btNext)
    Button btNext;

    Loading loading;
    HashMap<String, String> map;
    Bundle bundle;
    String[] time, city;
    int i = 0;
    boolean departStat = false;
    boolean destinationStat = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_show_2);
        ButterKnife.bind(this);

        loading = new Loading(this);
        bundle = getIntent().getExtras();
        map = (HashMap<String, String>) bundle.get("extra");

        loading.start();
        RestApi.getData().CityList().enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(@NonNull Call<CityResponse> call, @NonNull Response<CityResponse> response) {
                int size = Integer.valueOf(response.body().getCity().size());
                city = new String[size];
                for(i = 0; i < size; i++){
                    city[i] = String.valueOf(response.body().getCity().get(i).getName());
                }
                ArrayAdapter adapter = new ArrayAdapter(TrainShow2.this, android.R.layout.simple_list_item_1, city);
                etFrom.setAdapter(adapter);
                etTo.setAdapter(adapter);

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
                ArrayAdapter adapter = new ArrayAdapter(TrainShow2.this,
                        android.R.layout.simple_spinner_dropdown_item, time);
                spTime.setAdapter(adapter);
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
                TrainShow2.this.finish();
                break;
            case R.id.btNext:

                String mTime, mDestination, mDepart;
                mTime = spTime.getSelectedItem().toString();
                mDepart = etFrom.getText().toString();
                mDestination = etTo.getText().toString();

                if(mDepart.equals("") && mDestination.equals("")){
                    Toast.makeText(getApplicationContext(), "isi data dengan benar", Toast.LENGTH_SHORT).show();
                } else {

                    for(i = 0; i < city.length; i++){

                        if(mDepart.equals(String.valueOf(city[i]))){
                            departStat = true;
                        }

                        if(mDestination.equals(String.valueOf(city[i]))){
                            destinationStat = true;
                        }
                    }

                    if(!departStat){
                        Toast.makeText(getApplicationContext(), "pilih kota asal dengan benar", Toast.LENGTH_SHORT).show();
                    } else if (!destinationStat){
                        Toast.makeText(getApplicationContext(), "pilih kota tujuan dengan benar", Toast.LENGTH_SHORT).show();
                    } else {
                        map.put("time", mTime);
                        map.put("depart", mDepart);
                        map.put("destination", mDestination);

                        Intent intent = new Intent(getApplicationContext(), SeatPick.class);
                        intent.putExtra("extra", map);
                        startActivity(intent);
                    }
                }

                break;
        }
    }
}
