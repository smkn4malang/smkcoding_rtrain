package com.example.robet.rtrain.promo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robet.rtrain.ClientPage.TrainShow;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.support.Config;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class promoMuharram extends AppCompatActivity {

    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.buy)
    Button buy;

    String msg1, msg2;
    Calendar calendar;
    Config config;
    int day, month, year, date, promoDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config = new Config(this);
        setTheme(config.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promo_muharram);
        ButterKnife.bind(this);

        msg1 = "kami selaku admin dari \nR-Train mengucapkan selamat tahun baru 1439 H.";
        msg2 = "Dengan ini kami akan memberikan ticket gratis untuk semua user R-Train";
        text1.setText(msg1);
        text2.setText(msg2);
        promoDate = 20180911;

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        date = (year * 10000) + (month * 100) + day;

    }

    @OnClick(R.id.buy)
    public void onViewClicked() {
        if(config.getInfo("guest")){
            Toast.makeText(getApplicationContext(), "hanya user yang dapat menikmati promo", Toast.LENGTH_SHORT).show();
        } else if(config.getInfo("user")){
            if(date > promoDate){

                AlertDialog.Builder builder = new AlertDialog.Builder(promoMuharram.this);
                builder.setCancelable(false);
                builder.setTitle("masa berlaku telah habis");
                builder.setMessage("\nkami mohon maaf sebesar besarnya \nmasa berlaku promo tersebut telah habis");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                builder.show();

            } else {
                startActivity(new Intent(getApplicationContext(), TrainShow.class));
            }
        }
    }
}
