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
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.robet.rtrain.MainActivity;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.support.Config;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.support.Value;
import com.jgabrielfreitas.core.BlurImageView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSetting extends AppCompatActivity {

    @BindView(R.id.imgTop)
    BlurImageView imgTop;
    @BindView(R.id.btName)
    TextInputLayout btName;
    @BindView(R.id.btPassword)
    TextInputLayout btPassword;
    @BindView(R.id.btBack)
    CardView btBack;
    @BindView(R.id.btLogout)
    CardView btLogout;
    @BindView(R.id.tvName)
    TextInputEditText tvName;
    @BindView(R.id.tvPassword)
    TextInputEditText tvPassword;

    Config config;
    Loading loading;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_setting);
        ButterKnife.bind(this);

        config = new Config(this);
        tvName.setText(config.getName());
        tvPassword.setText(config.getPassword());
        loading = new Loading(this);
    }

    @OnClick({R.id.btName, R.id.btPassword, R.id.btBack, R.id.btLogout})
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
        }
    }

    private void editDialog(final int type){

        LayoutInflater layoutInflater = LayoutInflater.from(UserSetting.this);
        View view;

        if(type == 1){
            view = layoutInflater.inflate(R.layout.item_edit_name, null);
        } else {
            view = layoutInflater.inflate(R.layout.item_edit_password, null);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(UserSetting.this);
        builder.setView(view);
        builder.setCancelable(false);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        final TextInputEditText etText;
        CardView btChange, btCancel;

        etText = view.findViewById(R.id.etText);
        btChange = view.findViewById(R.id.btChange);
        btCancel = view.findViewById(R.id.btCancel);

        if(type == 1){
            etText.setText(config.getName());
        } else {
            etText.setText(config.getPassword());
        }

        btChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (type){
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
}
