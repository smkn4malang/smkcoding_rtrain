package com.example.robet.rtrain.AdminPage;

import android.content.Intent;
import android.os.Bundle;
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

public class adminLogin extends AppCompatActivity {

    public static boolean info;
    public static String message;
    public static String name;
    public static String username;
    public static String password;

    Loading loading;
    Config config;
    Intent newAct;

    @BindView(R.id.etUsername)
    TextInputEditText etUsername;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;
    @BindView(R.id.btnForgot)
    Button btnForgot;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminlogin);
        ButterKnife.bind(this);

        config = new Config(this);
        loading = new Loading(this);
    }

    @OnClick({R.id.btnForgot, R.id.btnLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnForgot:

                newAct = new Intent(getApplicationContext(), adminForgot.class);
                startActivity(newAct);

                break;

            case R.id.btnLogin:

                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                if(!username.equals("")){
                    if(!password.equals("")){

                        loading.start();
                        RestApi.getData().loginAdmin(username, password).enqueue(new Callback<Value>() {
                            @Override
                            public void onResponse(Call<Value> call, Response<Value> response) {

                                info = response.body().getLogin();
                                loading.stop();
//
                                if(info == true){

                                    name = response.body().getName();
                                    message = response.body().getMessage();

                                    config.setName(name);
                                    config.setInfo("admin", true);
                                    config.setId(response.body().getId());
                                    config.setUsername(username);
                                    config.setEmail(response.body().getEmail());
                                    config.setPassword(password);

                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                    newAct = new Intent(getApplicationContext(), IndexAdmin.class);
                                    newAct.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    newAct.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(newAct);

                                } else {
                                    message = response.body().getMessage();
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<Value> call, Throwable t) {
                                loading.stop();
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        Toast.makeText(getApplicationContext(), "kolom password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "kolom username tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}