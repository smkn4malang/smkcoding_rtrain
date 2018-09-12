package com.example.robet.rtrain.ClientPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.support.design.widget.TextInputEditText;
import android.widget.Toast;

import com.example.robet.rtrain.support.Config;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.R;
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
    Intent newAct;
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
    @BindView(R.id.btnForgot)
    Button btnForgot;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config = new Config(this);
        setTheme(config.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlogin);
        ButterKnife.bind(this);

        loading = new Loading(this);
    }

    @OnClick({R.id.btnForgot, R.id.btnLogin, R.id.btnRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnForgot:

                newAct = new Intent(getApplicationContext(), forgot.class);
                startActivity(newAct);

                break;
            case R.id.btnRegister:

                newAct = new Intent(getApplicationContext(), register.class);
                startActivity(newAct);

                break;
            case R.id.btnLogin:

                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                if (!username.equals("")){
                    if (!password.equals("")){

                        loading.start();
                        RestApi.getData().loginUser(username, password).enqueue(new Callback<Value>() {
                            @Override
                            public void onResponse(Call<Value> call, Response<Value> response) {

                                loading.stop();
                                info = response.body().getLogin();

                                if(info){

                                    name = response.body().getName();
                                    message = response.body().getMessage();

                                    config.setName(name);
                                    config.setInfo("user", info);
                                    config.setCredit(response.body().getCredit());
                                    config.setUsername(response.body().getUsername());
                                    config.setPassword(response.body().getPassword());
                                    config.setId(response.body().getId());

                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                    newAct = new Intent(getApplicationContext(), Index.class);
                                    newAct.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    newAct.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(newAct);
                                } else {
                                    message = response.body().getMessage();
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(@NonNull Call<Value> call, @NonNull Throwable t) {
                                loading.stop();
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        Toast.makeText(getApplicationContext(), "kolo password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "kolom username tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
