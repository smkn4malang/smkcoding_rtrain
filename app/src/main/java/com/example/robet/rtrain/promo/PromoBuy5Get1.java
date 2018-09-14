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

public class PromoBuy5Get1 extends AppCompatActivity {

    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.buy)
    Button buy;

    String msg1, msg2;
    Calendar calendar;
    Config config;
    int month, year, date, startDate, endDate;
    String promoEnd, promoPending, notUser;
    String promoEndHeader, promoPendingHeader, notUserHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config = new Config(this);
        setTheme(config.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promo_buy5_get1);
        ButterKnife.bind(this);

        msg1 = "Kami selaku admin dari \nR-Train merayakan awal diluncurkanya \naplikasi R-Train.";
        msg2 = "Kami akan memberikan 1 ticket kereta secara gratis \nbagi kalian yang membeli 5 ticket sekaligus";
        msg2 += "\npromo ini berlaku hanya di bulan september 2018.";
        msg2 += "\n\n*nb: tidak berlaku kelipatan.";

        text1.setText(msg1);
        text2.setText(msg2);
        startDate = 201809;
        endDate = 201810;

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        date = (year * 100) + month;

        promoEnd = "\nkami mohon maaf sebesar besarnya \nmasa berlaku promo tersebut telah habis";
        notUser = "\nhanya user yang dapat menikmati promo";
        promoPending = "\npromo tersebut belum dimulai";

        promoEndHeader = "masa berlaku telah habis";
        notUserHeader = "User Required";
        promoPending = "Promo belum dimulai";

    }

    @OnClick(R.id.buy)
    public void onViewClicked() {
        if(config.getInfo("guest")){
            showDialog(notUser, notUserHeader);
        } else if(date > startDate){
            showDialog(promoPending, promoPendingHeader);
        } else if(date > endDate){
            showDialog(promoEnd, promoEndHeader);
        } else {
            startActivity(new Intent(getApplicationContext(), TrainShow.class));
        }
    }

    private void showDialog(String message, String header){
        AlertDialog.Builder builder = new AlertDialog.Builder(PromoBuy5Get1.this);
        builder.setCancelable(false);
        builder.setTitle(header);
        builder.setMessage(message);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
