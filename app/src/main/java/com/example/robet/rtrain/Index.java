package com.example.robet.rtrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

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
    @BindView(R.id.drawSettings)
    ImageView drawSettings;
    @BindView(R.id.scrollHorizontal)
    HorizontalScrollView scrollHorizontal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        ButterKnife.bind(this);

        config = new Config(this);
        loading = new Loading(this);
        scrollHorizontal.setVerticalScrollBarEnabled(false);
    }

    @OnClick({R.id.btTicket, R.id.btShop, R.id.btSetting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btTicket:

                startActivity(new Intent(getApplicationContext(), TrainShow.class));

                break;
            case R.id.btShop:
                break;
            case R.id.btSetting:

                if (config.getInfo("guest")) {

                    config.setInfo("guest", false);
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else if (config.getInfo("user")) {

                    startActivity(new Intent(getApplicationContext(), UserSetting.class));

                }

                break;
        }
    }
}
