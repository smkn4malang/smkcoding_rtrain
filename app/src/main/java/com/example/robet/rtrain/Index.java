package com.example.robet.rtrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Index extends AppCompatActivity {

    @BindView(R.id.btTicket)
    LinearLayout btTicket;
    @BindView(R.id.btFeature)
    LinearLayout btFeature;
    @BindView(R.id.btShop)
    LinearLayout btShop;
    @BindView(R.id.btSetting)
    LinearLayout btSetting;
    @BindView(R.id.btLogout)
    LinearLayout btLogout;

    Config config;
    Intent newAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        ButterKnife.bind(this);

        config = new Config(this);

        if (config.getInfo("user") == true) {
            btSetting.setVisibility(View.VISIBLE);
            btLogout.setVisibility(View.GONE);
        } else if (config.getInfo("guest") == true) {
            btSetting.setVisibility(View.GONE);
            btLogout.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.btTicket, R.id.btFeature, R.id.btShop, R.id.btSetting, R.id.btLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btTicket:

                newAct = new Intent(getApplicationContext(), IndexTicket.class);
                startActivity(newAct);

                break;
            case R.id.btFeature:

                newAct = new Intent(getApplicationContext(), IndexFeatures.class);
                startActivity(newAct);

                break;
            case R.id.btShop:

                newAct = new Intent(getApplicationContext(), IndexShop.class);
                startActivity(newAct);

                break;
            case R.id.btSetting:

                newAct = new Intent(getApplicationContext(), IndexSettings.class);
                startActivity(newAct);

                break;
            case R.id.btLogout:

                config.setInfo("guest", false);
                newAct = new Intent(getApplicationContext(), MainActivity.class);
                newAct.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                newAct.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(newAct);

                break;
        }
    }
}
