package com.example.robet.rtrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.support.design.widget.TextInputEditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adminForgot extends AppCompatActivity {

    Intent newAct;
    Loading loading;

    public static String message = "";
    public static String username = "";
    public static boolean info;

    @BindView(R.id.etUsername)
    TextInputEditText etUsername;
    @BindView(R.id.btnSend)
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_forgot);
        ButterKnife.bind(this);

        loading = new Loading(this);
    }

    @OnClick(R.id.btnSend)
    public void onViewClicked() {

        username = etUsername.getText().toString();

        if(!username.equals("")){

            loading.start();
            RestApi.getData().forgotAdmin(username).enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {

                    loading.stop();
                    message = response.body().getMessage();
                    info = response.body().getInfo();

                    if(info == true){
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        newAct = new Intent(getApplicationContext(), adminForgot2.class);
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
            Toast.makeText(getApplicationContext(),"kolom email tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }
    }
}
