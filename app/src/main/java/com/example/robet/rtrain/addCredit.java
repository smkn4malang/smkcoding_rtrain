package com.example.robet.rtrain;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.support.design.widget.TextInputEditText;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addCredit extends AppCompatActivity {

    Config config;
    Intent newAct;
    String status = "indomaret";
    Drawable[] draw;
    Loading loading;
    String message;
    int tax = 2500;

    @BindView(R.id.etCredit)
    TextInputEditText etCredit;
    @BindView(R.id.imgIndomaret)
    ImageView imgIndomaret;
    @BindView(R.id.imgAlfamaret)
    ImageView imgAlfamaret;
    @BindView(R.id.imgBca)
    ImageView imgBca;
    @BindView(R.id.imgBri)
    ImageView imgBri;
    @BindView(R.id.imgMandiri)
    ImageView imgMandiri;
    @BindView(R.id.etPay)
    TextInputEditText etPay;
    @BindView(R.id.btBack)
    Button btBack;
    @BindView(R.id.btPurchase)
    Button btPurchase;
    @BindView(R.id.etTax)
    EditText etTax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_credit);
        ButterKnife.bind(this);

        config = new Config(this);
        draw = new Drawable[]{
                getDrawable(R.drawable.stroke1),
                getDrawable(R.drawable.stroke2),
        };
        loading = new Loading(this);
        etTax.setText("Tax: "+tax);
    }

    @OnClick({R.id.imgIndomaret, R.id.imgAlfamaret, R.id.imgBca, R.id.imgBri, R.id.imgMandiri, R.id.btPurchase, R.id.btBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgIndomaret:

                tax = 2500;
                imgIndomaret.setBackground(draw[1]);
                imgAlfamaret.setBackground(draw[0]);
                imgBca.setBackground(draw[0]);
                imgBri.setBackground(draw[0]);
                imgMandiri.setBackground(draw[0]);
                status = "indomaret";
                etTax.setText("Tax: "+tax);

                break;
            case R.id.imgAlfamaret:

                tax = 2500;
                imgIndomaret.setBackground(draw[0]);
                imgAlfamaret.setBackground(draw[1]);
                imgBca.setBackground(draw[0]);
                imgBri.setBackground(draw[0]);
                imgMandiri.setBackground(draw[0]);
                status = "alfamaret";
                etTax.setText("Tax: "+tax);

                break;
            case R.id.imgBca:

                tax = 2500;
                imgIndomaret.setBackground(draw[0]);
                imgAlfamaret.setBackground(draw[0]);
                imgBca.setBackground(draw[1]);
                imgBri.setBackground(draw[0]);
                imgMandiri.setBackground(draw[0]);
                status = "bca";
                etTax.setText("Tax: "+tax);

                break;
            case R.id.imgBri:

                tax = 5000;
                imgIndomaret.setBackground(draw[0]);
                imgAlfamaret.setBackground(draw[0]);
                imgBca.setBackground(draw[0]);
                imgBri.setBackground(draw[1]);
                imgMandiri.setBackground(draw[0]);
                status = "bri";
                etTax.setText("Tax: "+tax);

                break;
            case R.id.imgMandiri:

                tax = 5000;
                imgIndomaret.setBackground(draw[0]);
                imgAlfamaret.setBackground(draw[0]);
                imgBca.setBackground(draw[0]);
                imgBri.setBackground(draw[0]);
                imgMandiri.setBackground(draw[1]);
                status = "mandiri";
                etTax.setText("Tax: "+tax);

                break;
            case R.id.btPurchase:

                RestApi.getData().creditAdd(config.getId(), etCredit.getText().toString(), tax, etPay.getText().toString()).enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {

                        loading.stop();
                        message = response.body().getMessage();
                        config.setCredit(response.body().getCredit());
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        newAct = new Intent(getApplicationContext(), IndexSettings.class);
                        newAct.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        newAct.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(newAct);

                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        loading.stop();
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case R.id.btBack:

                newAct = new Intent(getApplicationContext(), IndexSettings.class);
                startActivity(newAct);

                break;
        }
    }
}