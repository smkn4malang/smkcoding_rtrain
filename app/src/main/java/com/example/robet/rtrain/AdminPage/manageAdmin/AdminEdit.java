package com.example.robet.rtrain.AdminPage.manageAdmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.support.Value;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminEdit extends AppCompatActivity {

    Bundle bundle;
    Loading loading;
    HashMap<String, String> data;

    public String id, name, username, email, message;
    public boolean info;
    @BindView(R.id.etName)
    TextInputEditText etName;
    @BindView(R.id.etUsername)
    TextInputEditText etUsername;
    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.btDelete)
    Button btDelete;
    @BindView(R.id.btUpdate)
    Button btUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_edit);
        ButterKnife.bind(this);

        loading = new Loading(this);
        bundle = getIntent().getExtras();
        data = (HashMap<String, String>) bundle.get("extra");

        id = data.get("id");
        name = data.get("name");
        username = data.get("username");
        email = data.get("email");

        etName.setText(name);
        etUsername.setText(username);
        etEmail.setText(email);
    }

    @OnClick({R.id.btDelete, R.id.btUpdate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btDelete:

                loading.start();
                RestApi.getData().AdminDelete(id).enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        loading.stop();
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        setResult(250);
                        AdminEdit.this.finish();
                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        loading.stop();
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case R.id.btUpdate:

                if(!etName.getText().toString().equals(name)
                        || !etUsername.getText().toString().equals(username)
                        || !etEmail.getText().toString().equals(email)){

                    name = etName.getText().toString();
                    username = etUsername.getText().toString();
                    email = etEmail.getText().toString();

                    loading.start();
                    RestApi.getData().AdminUpdate(id, name, username, email).enqueue(new Callback<Value>() {
                        @Override
                        public void onResponse(Call<Value> call, Response<Value> response) {

                            loading.stop();
                            info = response.body().getInfo();
                            message = response.body().getMessage();
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                            if(info){
                                setResult(250);
                                AdminEdit.this.finish();
                            }

                        }

                        @Override
                        public void onFailure(Call<Value> call, Throwable t) {
                            loading.stop();
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }

                break;
        }
    }
}
