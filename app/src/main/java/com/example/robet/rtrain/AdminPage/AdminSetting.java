package com.example.robet.rtrain.AdminPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

public class AdminSetting extends AppCompatActivity {

    @BindView(R.id.etName)
    TextInputEditText etName;
    @BindView(R.id.btName)
    Button btName;
    @BindView(R.id.etUsername)
    TextInputEditText etUsername;
    @BindView(R.id.btUsername)
    Button btUsername;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;
    @BindView(R.id.btPassword)
    Button btPassword;
    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.btEmail)
    Button btEmail;
    @BindView(R.id.btBack)
    Button btBack;
    @BindView(R.id.btLogout)
    Button btLogout;

    Config config;
    Intent intent;
    Loading loading;

    String name, email, password, username;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_setting);
        ButterKnife.bind(this);

        loading = new Loading(this);
        config = new Config(this);

        id = config.getId();
        name = config.getName();
        username = config.getUsername();
        password = config.getPassword();
        email = config.getEmail();

        etName.setText(name);
        etUsername.setText(username);
        etPassword.setText(password);
        etEmail.setText(email);

    }

    @OnClick({R.id.btName, R.id.btUsername, R.id.btPassword, R.id.btEmail, R.id.btBack, R.id.btLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btName:

                if (btName.getText().toString().equals("edit")){
                    btName.setText("change");
                    etName.setEnabled(true);
                } else if (btName.getText().toString().equals("change")){

                    if(!etName.getText().toString().equals("")){
                        if(!etName.getText().toString().equals(name)) {

                            loading.start();
                            RestApi.getData().AdminChangeName(id, etName.getText().toString()).enqueue(new Callback<Value>() {
                                @Override
                                public void onResponse(Call<Value> call, Response<Value> response) {
                                    loading.stop();
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
                                    if (response.body().getInfo()) {

                                        btName.setText("edit");
                                        etName.setEnabled(false);
                                        config.setName(etName.getText().toString());
                                        etName.setText(config.getName());
                                    }

                                }

                                @Override
                                public void onFailure(Call<Value> call, Throwable t) {
                                    loading.stop();
                                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            btName.setText("edit");
                            etName.setEnabled(false);
                        }
                    } else {
                        btName.setText("edit");
                        etName.setEnabled(false);
                        Toast.makeText(getApplicationContext(), "kolom nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.btUsername:

                if(config.getUsername().equals("root")){
                    btName.setText("edit");
                    etName.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "anda tidak bisa merubah ADMIN root", Toast.LENGTH_SHORT).show();
                } else if (btUsername.getText().toString().equals("edit")){
                    etUsername.setEnabled(true);
                    btUsername.setText("change");
                } else if (btUsername.getText().toString().equals("change")){

                    if(!etUsername.getText().toString().equals("")){

                        if(!etUsername.getText().toString().equals(username)){

                            loading.start();
                            RestApi.getData().AdminChangeUsername(id, etUsername.getText().toString()).enqueue(new Callback<Value>() {
                                @Override
                                public void onResponse(Call<Value> call, Response<Value> response) {
                                    loading.stop();
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                    if(response.body().getInfo()){
                                        btUsername.setText("edit");
                                        etUsername.setEnabled(false);
                                        config.setUsername(etUsername.getText().toString());
                                        etUsername.setText(config.getUsername());
                                    }

                                }

                                @Override
                                public void onFailure(Call<Value> call, Throwable t) {
                                    loading.stop();
                                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {

                            btUsername.setText("edit");
                            etUsername.setEnabled(false);
                        }

                    } else {
                        btUsername.setText("edit");
                        etUsername.setEnabled(false);
                        Toast.makeText(getApplicationContext(), "kolom username tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    }

                }

                break;
            case R.id.btPassword:

                if (btPassword.getText().toString().equals("edit")){
                    btPassword.setText("change");
                    etPassword.setEnabled(true);
                } else if (btPassword.getText().toString().equals("change")){

                    if (!etPassword.getText().toString().equals("")){

                        if (!etPassword.getText().toString().equals(password)){

                            loading.start();
                            RestApi.getData().AdminChangePassword(id, etPassword.getText().toString()).enqueue(new Callback<Value>() {
                                @Override
                                public void onResponse(Call<Value> call, Response<Value> response) {
                                    loading.stop();
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                    if(response.body().getInfo()){
                                        etPassword.setEnabled(false);
                                        btPassword.setText("edit");
                                        config.setPassword(etPassword.getText().toString());
                                        etPassword.setText(config.getPassword());
                                    }
                                }

                                @Override
                                public void onFailure(Call<Value> call, Throwable t) {
                                    loading.stop();
                                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {

                            etPassword.setEnabled(false);
                            btPassword.setText("edit");
                        }

                    } else {
                        etPassword.setEnabled(false);
                        btPassword.setText("edit");
                        Toast.makeText(getApplicationContext(), "kolom password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    }

                }

                break;
            case R.id.btEmail:

                if (btEmail.getText().toString().equals("edit")){
                    btEmail.setText("change");
                    etEmail.setEnabled(true);
                } else if (btEmail.getText().toString().equals("change")){

                    if(!etEmail.getText().toString().equals("")){

                        if(!etEmail.getText().toString().equals(email)){

                            loading.start();
                            RestApi.getData().AdminChangeEmail(id, etEmail.getText().toString()).enqueue(new Callback<Value>() {
                                @Override
                                public void onResponse(Call<Value> call, Response<Value> response) {
                                    loading.stop();
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                    if(response.body().getInfo()){
                                        etEmail.setEnabled(false);
                                        btEmail.setText("edit");
                                        config.setEmail(etEmail.getText().toString());
                                        etEmail.setText(config.getEmail());
                                    }

                                }

                                @Override
                                public void onFailure(Call<Value> call, Throwable t) {
                                    loading.stop();
                                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            etEmail.setEnabled(false);
                            btEmail.setText("edit");

                        }

                    } else {
                        etEmail.setEnabled(false);
                        btEmail.setText("edit");
                        Toast.makeText(getApplicationContext(), "kolom email tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    }

                }

                break;
            case R.id.btBack:

                AdminSetting.this.finish();

                break;
            case R.id.btLogout:

                config.setInfo("admin", false);
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                break;
        }
    }
}
