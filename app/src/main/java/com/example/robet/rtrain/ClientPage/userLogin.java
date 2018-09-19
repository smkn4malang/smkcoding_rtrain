package com.example.robet.rtrain.ClientPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
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

public class userLogin extends AppCompatActivity {

    Config config;
    Intent intent;
    Loading loading;

    String name = "";
    boolean info;
    String message = "";
    String username = "";
    String password = "";
    @BindView(R.id.etUsername)
    TextInputEditText etUsername;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;
    @BindView(R.id.btForgot)
    TextView btForgot;
    @BindView(R.id.btLogin)
    Button btLogin;
    @BindView(R.id.btRegister)
    TextView btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config = new Config(this);
        setTheme(config.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlogin);
        ButterKnife.bind(this);

        loading = new Loading(this);
    }

    @OnClick({R.id.btForgot, R.id.btLogin, R.id.btRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btForgot:

                intent = new Intent(getApplicationContext(), forgot.class);
                startActivity(intent);

                break;
            case R.id.btLogin:

                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                if (!username.equals("")) {
                    if (!password.equals("")) {
                        onLogin();
                    } else {
                        etPassword.setError("wajib diisi");
                    }
                } else {
                    etUsername.setError("wajib diisi");
                }

                break;
            case R.id.btRegister:

                intent = new Intent(getApplicationContext(), register.class);
                startActivity(intent);

                break;
        }
    }

    private void onLogin(){

        loading.start();
        RestApi.getData().loginUser(username, password).enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {

                loading.stop();
                info = response.body().getLogin();

                if (info) {

                    name = response.body().getName();
                    message = response.body().getMessage();

                    config.setName(name);
                    config.setInfo("user", info);
                    config.setCredit(response.body().getCredit());
                    config.setUsername(response.body().getUsername());
                    config.setPassword(response.body().getPassword());
                    config.setId(response.body().getId());

                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    intent = new Intent(getApplicationContext(), Index.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    message = response.body().getMessage();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<Value> call, @NonNull Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), "jaringan bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
