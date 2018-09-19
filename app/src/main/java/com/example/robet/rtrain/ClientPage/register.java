package com.example.robet.rtrain.ClientPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
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

public class register extends AppCompatActivity {

    Config config;
    Loading loading;
    @BindView(R.id.etName)
    TextInputEditText etName;
    @BindView(R.id.etUsername)
    TextInputEditText etUsername;
    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;
    @BindView(R.id.etRePassword)
    TextInputEditText etRePassword;
    @BindView(R.id.btCancel)
    Button btCancel;
    @BindView(R.id.btRegister)
    Button btRegister;

    String name, username, email, password, repassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config = new Config(this);
        setTheme(config.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ButterKnife.bind(this);

        loading = new Loading(this);
    }


    @OnClick({R.id.btCancel, R.id.btRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btCancel:
                register.this.finish();
                break;
            case R.id.btRegister:

                name = etName.getText().toString();
                username = etUsername.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                repassword = etRePassword.getText().toString();

                if(name.equals("")){
                    etName.setError("wajib diisi");
                } else if(username.equals("")){
                    etUsername.setError("wajib diisi");
                } else if(email.equals("")){
                    etEmail.setError("wajib diisi");
                } else if(password.equals("")){
                    etPassword.setError("wajib diisi");
                } else if(repassword.equals("")){
                    etRePassword.setError("wajib diisi");
                } else if(!password.equals(repassword)){
                    etRePassword.setError("password tidak match");
                } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmail.setError("email tidak valid");
                } else {

                    onRegister();

                }

                break;
        }
    }
    private void onRegister(){
        loading.start();
        RestApi.getData().registerUser(name, username, password, email).enqueue(new Callback<Value>() {
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
                Toast.makeText(getApplicationContext(), "jaringan bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
