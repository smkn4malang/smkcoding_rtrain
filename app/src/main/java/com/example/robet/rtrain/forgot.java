package com.example.robet.rtrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.support.design.widget.TextInputEditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class forgot extends AppCompatActivity {

    Intent newAct;
    Loading loading;

    String username = "";
    String message = "";
    boolean info;

    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.btnSend)
    Button btnSend;
    @BindView(R.id.etUsername)
    TextInputEditText etUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot);
        ButterKnife.bind(this);

        loading = new Loading(this);
    }

    @OnClick({R.id.btnRegister, R.id.btnSend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:

                newAct = new Intent(getApplicationContext(), register.class);
                startActivity(newAct);

                break;
            case R.id.btnSend:

                username = etUsername.getText().toString();

                if(!username.equals("")){

                    loading.start();
                    RestApi.getData().forgotUser(username).enqueue(new Callback<Value>() {
                        @Override
                        public void onResponse(Call<Value> call, Response<Value> response) {

                            loading.stop();
                            message = response.body().getMessage();
                            info = response.body().getInfo();

                            if(info == true){
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                newAct = new Intent(getApplicationContext(), forgot2.class);
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

                break;
        }
    }
}
