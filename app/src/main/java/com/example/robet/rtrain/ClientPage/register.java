package com.example.robet.rtrain.ClientPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.support.design.widget.TextInputEditText;
import android.widget.Toast;

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

public class register extends AppCompatActivity {

    Intent newAct;
    Loading loading;

    public static String name = "";
    public static String username = "";
    public static String password = "";
    public static String email = "";
    public static String message = "";

    @BindView(R.id.etName)
    TextInputEditText etName;
    @BindView(R.id.etUsername)
    TextInputEditText etUsername;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;
    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.btnForgot)
    Button btnForgot;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ButterKnife.bind(this);

        loading = new Loading(this);
    }

    @OnClick({R.id.btnForgot, R.id.btnRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnForgot:

                newAct = new Intent(getApplicationContext(), forgot.class);
                startActivity(newAct);

                break;
            case R.id.btnRegister:

                name = etName.getText().toString();
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
                email = etEmail.getText().toString();

                if (!name.equals("")) {
                    if (!username.equals("")) {
                        if (!password.equals("")) {
                            if (!email.equals("")) {

                                loading.start();
                                RestApi.getData().registerUser(name, username, password, email).enqueue(new Callback<Value>() {
                                    @Override
                                    public void onResponse(Call<Value> call, Response<Value> response) {

                                        loading.stop();
                                        message = response.body().getMessage();
                                        if (message.equals("Registrasi berhasil")) {
                                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                            newAct = new Intent(getApplicationContext(), userLogin.class);
                                            newAct.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            newAct.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(newAct);
                                        } else {
                                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Value> call, Throwable t) {
                                        loading.stop();
                                        Toast.makeText(getApplicationContext(), "jaringan error", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            } else {
                                Toast.makeText(getApplicationContext(), "kolom email tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "kolom password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "kolom username tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "kolom name tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
