package com.example.robet.rtrain.ClientPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robet.rtrain.support.Config;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.MainActivity;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.support.RestApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Index extends AppCompatActivity {

    Config config;
    Intent intent;
    Loading loading;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        ButterKnife.bind(this);

        RestApi.getData().systemHistoryDelete();

        config = new Config(this);
        loading = new Loading(this);
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
                startActivity(new Intent(getApplicationContext(), AddCredit.class));
                break;
        }
    }
}
