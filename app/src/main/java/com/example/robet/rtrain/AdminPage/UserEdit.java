package com.example.robet.rtrain.AdminPage;

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

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserEdit extends AppCompatActivity {

    @BindView(R.id.etName)
    TextInputEditText etName;
    @BindView(R.id.etUsername)
    TextInputEditText etUsername;
    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.etCredit)
    TextInputEditText etCredit;
    @BindView(R.id.btDelete)
    Button btDelete;
    @BindView(R.id.btUpdate)
    Button btUpdate;

    Bundle bundle;
    Loading loading;
    HashMap<String, String> data;

    String id, name, username, email, credit, message;
    boolean info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_edit);
        ButterKnife.bind(this);

        loading = new Loading(this);
        bundle = getIntent().getExtras();
        data = (HashMap<String, String>) bundle.get("extra");

        id = data.get("id");
        name = data.get("name");
        username = data.get("username");
        email = data.get("email");
        credit = data.get("credit");

        etName.setText(name);
        etUsername.setText(username);
        etEmail.setText(email);
        etCredit.setText(credit);
    }

    @OnClick({R.id.btDelete, R.id.btUpdate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btDelete:

                loading.start();
                RestApi.getData().UserDelete(id).enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        loading.stop();
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        UserEdit.this.finish();
                        startActivity(new Intent(getApplicationContext(), AdminManageUser.class));
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
                        || !etEmail.getText().toString().equals(email)
                        || !etCredit.getText().toString().equals(credit)){

                    name = etName.getText().toString();
                    username = etUsername.getText().toString();
                    email = etEmail.getText().toString();
                    credit = etCredit.getText().toString();

                    loading.start();
                    RestApi.getData().UserUpdate(id, name, username, email, credit).enqueue(new Callback<Value>() {
                        @Override
                        public void onResponse(Call<Value> call, Response<Value> response) {

                            loading.stop();
                            info = response.body().getInfo();
                            message = response.body().getMessage();
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            UserEdit.this.finish();

                            if(!info){
                                startActivity(getIntent());
                            } else {
                                startActivity(new Intent(getApplicationContext(), AdminManageUser.class));
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
