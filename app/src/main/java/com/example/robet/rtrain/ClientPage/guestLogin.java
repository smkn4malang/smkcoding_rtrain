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
import com.example.robet.rtrain.MainActivity;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.support.Value;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class guestLogin extends AppCompatActivity {

    String name, email, message;

    Config config;
    Intent newAct;
    Loading loading;

    @BindView(R.id.etName)
    TextInputEditText etName;
    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.btnBack)
    Button btnBack;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guestlogin);
        ButterKnife.bind(this);
        loading = new Loading(this);
    }

    @OnClick({R.id.btnBack, R.id.btnLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBack:

                newAct = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(newAct);

                break;
            case R.id.btnLogin:

                name = etName.getText().toString();
                email = etEmail.getText().toString();

                if(!name.equals("")){
                    if(!email.equals("")){

                        loading.start();
                        RestApi.getData().loginGuest(name, email).enqueue(new Callback<Value>() {
                            @Override
                            public void onResponse(@NonNull Call<Value> call, @NonNull Response<Value> response) {
                                loading.stop();
                                message = response.body().getMessage();
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                                config.setName(name);
                                config.setEmail(email);
                                config.setId(response.body().getId());
                                config.setInfo("guest", true);

                                newAct = new Intent(getApplicationContext(), Index.class);
                                startActivity(newAct);
                            }

                            @Override
                            public void onFailure(@NonNull Call<Value> call, @NonNull Throwable t) {
                                loading.stop();
                                Toast.makeText(getApplicationContext(), "jaringan error", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        Toast.makeText(getApplicationContext(), "kolom email tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"kolom nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
