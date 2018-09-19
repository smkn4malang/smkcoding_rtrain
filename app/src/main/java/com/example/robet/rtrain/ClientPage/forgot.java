package com.example.robet.rtrain.ClientPage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class forgot extends AppCompatActivity {

    Intent intent;
    Loading loading;
    String username = "";
    String message = "";
    Config config;
    boolean info;
    TextInputEditText token, password, repassword;
    Button cancel, ok;
    @BindView(R.id.etUsername)
    TextInputEditText etUsername;
    @BindView(R.id.btSend)
    Button btSend;
    @BindView(R.id.btRegister)
    TextView btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config = new Config(this);
        setTheme(config.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot);
        ButterKnife.bind(this);

        loading = new Loading(this);
    }

    @OnClick({R.id.btRegister, R.id.btSend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btRegister:

                intent = new Intent(getApplicationContext(), register.class);
                startActivity(intent);

                break;
            case R.id.btSend:

                username = etUsername.getText().toString();

                if (!username.equals("")) {

                    onForgotSend();

                } else {
                    Toast.makeText(getApplicationContext(), "kolom email tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void showDialog() {

        LayoutInflater inflater = LayoutInflater.from(forgot.this);
        View view = inflater.inflate(R.layout.item_forgot, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(forgot.this);
        builder.setView(view);
        builder.setCancelable(false);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        token = view.findViewById(R.id.etToken);
        password = view.findViewById(R.id.etPassword);
        repassword = view.findViewById(R.id.etRePassword);
        cancel = view.findViewById(R.id.btCancel);
        ok = view.findViewById(R.id.btOk);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (token.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "masukkan token anda", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "masukkan password baru anda", Toast.LENGTH_SHORT).show();
                } else if (repassword.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "masukkan kembali password anda", Toast.LENGTH_SHORT).show();
                } else if (!password.getText().toString().equals(repassword.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "password tidak sama", Toast.LENGTH_SHORT).show();
                } else {

                    loading.start();
                    RestApi.getData().forgot2User(token.getText().toString(), password.getText().toString()).enqueue(new Callback<Value>() {
                        @Override
                        public void onResponse(Call<Value> call, Response<Value> response) {
                            loading.stop();
                            String message = response.body().getMessage();
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), userLogin.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
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

    }

    private void onForgotSend(){
        loading.start();
        RestApi.getData().forgotUser(username).enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {

                loading.stop();
                message = response.body().getMessage();
                info = response.body().getInfo();

                if (info == true) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    showDialog();
                } else {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
