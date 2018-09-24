package com.example.robet.rtrain.ClientPage;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robet.rtrain.notification.pushNotificationToken;
import com.example.robet.rtrain.promo.PromoBuy5Get1;
import com.example.robet.rtrain.promo.promoMuharram;
import com.example.robet.rtrain.MainActivity;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.gson.CityResponse;
import com.example.robet.rtrain.gson.TimeResponse;
import com.example.robet.rtrain.support.Config;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.support.UpdateData;
import com.example.robet.rtrain.support.Value;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Index extends AppCompatActivity {

    Config config;
    Loading loading;
    String rekening;
    int tax, pay;
    String[] time, city;
    boolean bank = false;
    String token;

    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvFeatures)
    TextView tvFeatures;
    @BindView(R.id.tvTicket)
    TextView tvTicket;
    @BindView(R.id.btTicket)
    CardView btTicket;
    @BindView(R.id.tvShop)
    TextView tvShop;
    @BindView(R.id.btShop)
    CardView btShop;
    @BindView(R.id.tvHistory)
    TextView tvHistory;
    @BindView(R.id.btHistory)
    CardView btHistory;
    @BindView(R.id.tvSetting)
    TextView tvSetting;
    @BindView(R.id.btSetting)
    CardView btSetting;
    @BindView(R.id.tvAddCredit)
    TextView tvAddCredit;
    @BindView(R.id.btCredit)
    CardView btCredit;
    @BindView(R.id.tvLogout)
    TextView tvLogout;
    @BindView(R.id.btLogout)
    CardView btLogout;
    @BindView(R.id.tvPromo)
    TextView tvPromo;
    @BindView(R.id.llIndex)
    LinearLayout llIndex;
    @BindView(R.id.promoMuharram)
    CardView promoMuharram;
    @BindView(R.id.buy5get1)
    CardView buy5get1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config = new Config(this);
        setTheme(config.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        ButterKnife.bind(this);

        if(!config.getUpdated()){
            new UpdateData(Index.this).update();
            config.setApply(false);
        }

        onDelete();

        loading = new Loading(this);
        tvName.setText(config.getName());

        if (config.getInfo("user")) {
            btLogout.setAlpha(0);
        } else {
            btLogout.setAlpha(1);
        }

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
                    Intent intent = new Intent(getApplicationContext(), UserSetting.class);
                    startActivityForResult(intent, 1);
                }
                break;

            case R.id.btLogout:
                if (config.getInfo("guest")) {
                    config.setInfo("guest", false);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                break;
        }
    }

    @OnClick({R.id.btHistory, R.id.btShop, R.id.btCredit})
    public void onFeatureClicked(View view) {
        switch (view.getId()) {

            case R.id.btHistory:
                startActivity(new Intent(getApplicationContext(), History.class));
                break;

            case R.id.btShop:
                if(!config.getItem().get("id")[0].equals("nothing")) {
                    Intent intent = new Intent(getApplicationContext(), Shop.class);
                    startActivityForResult(intent, 2);
                } else {
                    Toast.makeText(getApplicationContext(), "please wait", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btCredit:

                if (config.getInfo("user")) {

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
                    final CardView cvRekening = CreditView.findViewById(R.id.cvRekening);
                    final TextInputEditText etRekening = CreditView.findViewById(R.id.etRekening);
                    final TextInputEditText etPay = CreditView.findViewById(R.id.etPay);
                    final TextInputEditText etTax = CreditView.findViewById(R.id.etTax);
                    cvRekening.setVisibility(View.GONE);

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
                                    cvRekening.setVisibility(View.GONE);
                                    bank = false;
                                    break;
                                case 1:
                                    tax = 2500;
                                    cvRekening.setVisibility(View.GONE);
                                    bank = false;
                                    break;
                                case 2:
                                    tax = 5000;
                                    cvRekening.setVisibility(View.VISIBLE);
                                    bank = true;
                                    break;
                                case 3:
                                    tax = 7500;
                                    cvRekening.setVisibility(View.VISIBLE);
                                    bank = true;
                                    break;
                                case 4:
                                    tax = 5000;
                                    cvRekening.setVisibility(View.VISIBLE);
                                    bank = true;
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
                            rekening = etRekening.getText().toString();

                            if (bank) {
                                if (rekening.equals("")) {
                                    bank = false;
                                } else {
                                    bank = true;
                                }
                            } else {
                                bank = true;
                            }

                            if (!mPay.equals("")) {
                                pay = Integer.valueOf(mPay);
                            }

                            if (mPay.equals("")) {
                                bank = false;
                                Toast.makeText(getApplicationContext(), "masukkan uang pembayaran anda", Toast.LENGTH_SHORT).show();
                            } else if (!bank) {
                                bank = false;
                                Toast.makeText(getApplicationContext(), "masukkan nomor rekening anda", Toast.LENGTH_SHORT).show();
                            } else if (pay < 50000) {
                                bank = false;
                                Toast.makeText(getApplicationContext(), "anda harus topup minimal 50ribu", Toast.LENGTH_SHORT).show();
                            } else {

                                pay -= tax;
                                mPay = String.valueOf(pay);

                                loading.start();
                                RestApi.getData().creditAdd(config.getId(), mPay).enqueue(new Callback<Value>() {
                                    @Override
                                    public void onResponse(Call<Value> call, Response<Value> response) {
                                        loading.stop();
                                        Toast.makeText(getApplicationContext(), "kode pembayaran akan dikirim melalui email", Toast.LENGTH_SHORT).show();
                                        config.setCredit(config.getCredit() + pay);
                                        alertDialog.cancel();
                                    }

                                    @Override
                                    public void onFailure(Call<Value> call, Throwable t) {
                                        loading.stop();
                                        Toast.makeText(getApplicationContext(), "no internet connection", Toast.LENGTH_SHORT).show();
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
                } else {
                    Toast.makeText(getApplicationContext(), "anda harus menjadi user terlebih dahulu", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void onDelete() {
        RestApi.getData().systemHistoryDelete().enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {

            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "no internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            Intent intent = getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @OnClick({R.id.promoMuharram, R.id.buy5get1})
    public void onPromoClicked(View view) {
        switch (view.getId()) {
            case R.id.promoMuharram:
                startActivity(new Intent(getApplicationContext(), promoMuharram.class));
                break;
            case R.id.buy5get1:
                startActivity(new Intent(getApplicationContext(), PromoBuy5Get1.class));
                break;
        }
    }

}
