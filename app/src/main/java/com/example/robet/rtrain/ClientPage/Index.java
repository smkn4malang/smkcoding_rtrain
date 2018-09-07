package com.example.robet.rtrain.ClientPage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robet.rtrain.gson.CityResponse;
import com.example.robet.rtrain.gson.TimeResponse;
import com.example.robet.rtrain.support.Config;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.MainActivity;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.support.Value;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Index extends AppCompatActivity{

    Config config;
    Intent intent;
    Loading loading;
    int tax, pay;
    String[] time, city;

    @BindView(R.id.btTicket)
    CardView btTicket;
    @BindView(R.id.btShop)
    CardView btShop;
    @BindView(R.id.btSetting)
    CardView btSetting;
    @BindView(R.id.tvPromo)
    TextView tvPromo;
    @BindView(R.id.Promo)
    RecyclerView Promo;
    @BindView(R.id.btLogout)
    CardView btLogout;
    @BindView(R.id.btHistory)
    CardView btHistory;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvCredit)
    TextView tvCredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        ButterKnife.bind(this);

        RestApi.getData().systemHistoryDelete();

        config = new Config(this);
        loading = new Loading(this);

        tvName.setText(config.getName());
        tvCredit.setText("Credit: Rp " + String.valueOf(config.getCredit()));

        RestApi.getData().TimeList().enqueue(new Callback<TimeResponse>() {
            @Override
            public void onResponse(Call<TimeResponse> call, Response<TimeResponse> response) {
                int size = response.body().getTime().size();
                time = new String[size];
                for(int i = 0; i < size; i++){
                    time[i] = response.body().getTime().get(i).getTime();
                }
                config.setTime(time);
            }

            @Override
            public void onFailure(Call<TimeResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RestApi.getData().CityList().enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                int size = response.body().getCity().size();
                city = new String[size];
                for(int i = 0; i < size; i++){
                    city[i] = response.body().getCity().get(i).getName();
                }
                config.setCity(city);
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.btTicket, R.id.btSetting, R.id.btLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btTicket:
                startActivity(new Intent(getApplicationContext(), TrainShow.class));
                break;

            case R.id.btSetting:
                if (config.getInfo("guest")) {
                    Toast.makeText(getApplicationContext(), "anda harus menjadi user dahulu", Toast.LENGTH_SHORT).show();
                } else if (config.getInfo("user")) {
                    startActivity(new Intent(getApplicationContext(), UserSetting.class));
                }
                break;
            case R.id.btLogout:

                config.setInfo("user", false);
                config.setInfo("guest", false);
                config.setInfo("admin", false);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                break;
        }
    }

    @OnClick({R.id.btHistory, R.id.btShop, R.id.btCredit})
    public void onFeatureClicked(View view){
        switch (view.getId()){

            case R.id.btHistory:
                startActivity(new Intent(getApplicationContext(), History.class));
                break;

            case R.id.btShop:
                startActivity(new Intent(getApplicationContext(), Shop.class));
                break;

            case R.id.btCredit:

                LayoutInflater layoutInflater = LayoutInflater.from(Index.this);
                final View CreditView = layoutInflater.inflate(R.layout.add_credit, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(Index.this);
                builder.setView(CreditView);
                builder.setCancelable(false);

                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();

                Button btBack = CreditView.findViewById(R.id.btBack);
                Button btAdd = CreditView.findViewById(R.id.btAdd);
                Spinner spPay = CreditView.findViewById(R.id.spPay);
                final TextInputEditText etPay = CreditView.findViewById(R.id.etPay);
                final TextInputEditText etTax = CreditView.findViewById(R.id.etTax);

                String[] payMethod = {"indomaret", "alfamaret", "bca", "bri", "mandiri"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Index.this,
                        android.R.layout.simple_spinner_dropdown_item, payMethod);
                spPay.setAdapter(adapter);
                spPay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        int index = adapterView.getSelectedItemPosition();
                        switch (index) {
                            case 0:
                                tax = 2500;
                                break;
                            case 1:
                                tax = 2500;
                                break;
                            case 2:
                                tax = 5000;
                                break;
                            case 3:
                                tax = 7500;
                                break;
                            case 4:
                                tax = 5000;
                                break;
                        }
                        etTax.setText(String.valueOf(tax));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                btAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mPay = etPay.getText().toString();
                        pay = Integer.valueOf(mPay);
                        if(pay < 50000){
                            Toast.makeText(getApplicationContext(), "anda harus topup minimal 50ribu", Toast.LENGTH_SHORT).show();
                        } else {

                            pay -= tax;
                            mPay = String.valueOf(pay);

                            loading.start();
                            RestApi.getData().creditAdd(config.getId(), mPay).enqueue(new Callback<Value>() {
                                @Override
                                public void onResponse(Call<Value> call, Response<Value> response) {
                                    loading.stop();
                                    Toast.makeText(getApplicationContext(), "berhasil tambah credit", Toast.LENGTH_SHORT).show();
                                    config.setCredit(config.getCredit() + pay);
                                    tvCredit.setText("Credit: Rp" + config.getCredit());
                                    alertDialog.cancel();
                                }

                                @Override
                                public void onFailure(Call<Value> call, Throwable t) {
                                    loading.stop();
                                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                });

                btBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                    }
                });

                break;
        }
    }
}
