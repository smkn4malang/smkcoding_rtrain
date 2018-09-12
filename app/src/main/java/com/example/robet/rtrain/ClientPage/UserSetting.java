package com.example.robet.rtrain.ClientPage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robet.rtrain.MainActivity;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.support.Config;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.support.Value;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSetting extends AppCompatActivity {


    Config config;
    Loading loading;
    Intent intent;
    @BindView(R.id.imgTop)
    ImageView imgTop;
    @BindView(R.id.tvName)
    TextInputEditText tvName;
    @BindView(R.id.tvBtName)
    TextView tvBtName;
    @BindView(R.id.btName)
    TextInputLayout btName;
    @BindView(R.id.tvPassword)
    TextInputEditText tvPassword;
    @BindView(R.id.tvBtPassword)
    TextView tvBtPassword;
    @BindView(R.id.btPassword)
    TextInputLayout btPassword;
    @BindView(R.id.tvFont)
    TextInputEditText tvFont;
    @BindView(R.id.tvBtFont)
    TextView tvBtFont;
    @BindView(R.id.btFont)
    TextInputLayout btFont;
    @BindView(R.id.tvBack)
    TextView tvBack;
    @BindView(R.id.btBack)
    CardView btBack;
    @BindView(R.id.tvLogout)
    TextView tvLogout;
    @BindView(R.id.btLogout)
    CardView btLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config = new Config(this);
        setTheme(config.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_setting);
        ButterKnife.bind(this);

        config = new Config(this);
        tvName.setText(config.getName());
        tvPassword.setText(config.getPassword());
        loading = new Loading(this);
        tvFont.setText(config.getFont());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent = new Intent();
        setResult(1, intent);
        UserSetting.this.finish();
    }

    @OnClick({R.id.btName, R.id.btPassword, R.id.btBack, R.id.btLogout, R.id.btFont})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btName:
                editDialog(1);
                break;
            case R.id.btPassword:
                editDialog(2);
                break;
            case R.id.btBack:
                intent = new Intent();
                setResult(1, intent);
                UserSetting.this.finish();
                break;
            case R.id.btLogout:

                config.setInfo("admin", false);
                config.setInfo("user", false);
                config.setInfo("guest", false);

                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                break;
            case R.id.btFont:
                changeFont();
                break;
        }
    }

    private void editDialog(final int type) {

        LayoutInflater layoutInflater = LayoutInflater.from(UserSetting.this);
        View view;

        if (type == 1) {
            view = layoutInflater.inflate(R.layout.item_edit_name, null);
        } else {
            view = layoutInflater.inflate(R.layout.item_edit_password, null);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(UserSetting.this);
        builder.setView(view);
        builder.setCancelable(true);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        final TextInputEditText etText;
        CardView btChange, btCancel;

        etText = view.findViewById(R.id.etText);
        btChange = view.findViewById(R.id.btChange);
        btCancel = view.findViewById(R.id.btCancel);

        btChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (type) {
                    case 1:

                        loading.start();
                        RestApi.getData().ChangeName(config.getId(), etText.getText().toString()).enqueue(new Callback<Value>() {
                            @Override
                            public void onResponse(Call<Value> call, Response<Value> response) {

                                loading.stop();
                                String message = response.body().getMessage();
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                config.setName(etText.getText().toString());
                                tvName.setText(etText.getText().toString());
                                dialog.cancel();
                            }

                            @Override
                            public void onFailure(Call<Value> call, Throwable t) {
                                loading.stop();
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        break;
                    case 2:

                        loading.start();
                        RestApi.getData().ChangePW(config.getId(), config.getPassword()).enqueue(new Callback<Value>() {
                            @Override
                            public void onResponse(Call<Value> call, Response<Value> response) {

                                loading.stop();
                                String message;
                                message = response.body().getMessage();
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                config.setPassword(etText.getText().toString());
                                tvPassword.setText(etText.getText().toString());
                                dialog.cancel();

                            }

                            @Override
                            public void onFailure(Call<Value> call, Throwable t) {
                                loading.stop();
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        break;
                }
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

    }

    private void changeFont() {

        LayoutInflater layoutInflater = LayoutInflater.from(UserSetting.this);
        View view = layoutInflater.inflate(R.layout.item_font, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(UserSetting.this);
        builder.setView(view);
        builder.setCancelable(true);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        CardView cat, fette, gloria, ray, fantasque, kanit, sarpanch, walleye;
        cat = view.findViewById(R.id.cat);
        fette = view.findViewById(R.id.fette);
        gloria = view.findViewById(R.id.gloria);
        ray = view.findViewById(R.id.ray);
        fantasque = view.findViewById(R.id.fantasque);
        kanit = view.findViewById(R.id.kanit);
        sarpanch = view.findViewById(R.id.sarpanch);
        walleye = view.findViewById(R.id.walleye);

        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config.setTheme(R.style.CatChilds);
                config.setFont("Default");
                alertDialog.cancel();
                UserSetting.this.finish();
                startActivity(getIntent());
            }
        });

        fette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config.setTheme(R.style.FetteMikado);
                config.setFont("Fette Mikado");
                alertDialog.cancel();
                UserSetting.this.finish();
                startActivity(getIntent());
            }
        });

        gloria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config.setTheme(R.style.Gloria);
                config.setFont("Gloria");
                alertDialog.cancel();
                UserSetting.this.finish();
                startActivity(getIntent());
            }
        });

        ray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config.setTheme(R.style.RayJohns);
                config.setFont("Ray Johns");
                alertDialog.cancel();
                UserSetting.this.finish();
                startActivity(getIntent());
            }
        });

        fantasque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config.setTheme(R.style.Fantasque);
                config.setFont("Fantasque");
                alertDialog.cancel();
                UserSetting.this.finish();
                startActivity(getIntent());
            }
        });

        kanit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config.setTheme(R.style.Kanit);
                config.setFont("Kanit");
                alertDialog.cancel();
                UserSetting.this.finish();
                startActivity(getIntent());
            }
        });

        sarpanch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config.setTheme(R.style.Sarpanch);
                config.setFont("Sarpanch");
                alertDialog.cancel();
                UserSetting.this.finish();
                startActivity(getIntent());
            }
        });

        walleye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config.setTheme(R.style.Walleye);
                config.setFont("Walleye");
                alertDialog.cancel();
                UserSetting.this.finish();
                startActivity(getIntent());
            }
        });

    }
}
