package com.example.robet.rtrain;

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

public class UserAdd extends AppCompatActivity {

    @BindView(R.id.etName)
    TextInputEditText etName;
    @BindView(R.id.etUsername)
    TextInputEditText etUsername;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;
    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.etCredit)
    TextInputEditText etCredit;
    @BindView(R.id.btnBack)
    Button btnBack;
    @BindView(R.id.btnAdd)
    Button btnAdd;

    Loading loading;
    boolean info;
    String name, username, email, password, credit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_add);
        ButterKnife.bind(this);

        loading = new Loading(this);
    }

    @OnClick({R.id.btnBack, R.id.btnAdd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBack:

                UserAdd.this.finish();

                break;
            case R.id.btnAdd:

                name = etName.getText().toString();
                username = etUsername.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                credit = etCredit.getText().toString();

                if (name.isEmpty()){
                    Toast.makeText(getApplicationContext(), "kolom nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (username.isEmpty()){
                    Toast.makeText(getApplicationContext(), "kolom username tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "kolom password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()){
                    Toast.makeText(getApplicationContext(), "kolom email tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (credit.isEmpty()){
                    Toast.makeText(getApplicationContext(), "kolom credit tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    loading.start();
                    RestApi.getData().UserAdd(name, username, password, email, credit).enqueue(new Callback<Value>() {
                        @Override
                        public void onResponse(Call<Value> call, Response<Value> response) {
                            loading.stop();

                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            info = response.body().getInfo();
                            UserAdd.this.finish();

                            if(info == false){
                                startActivity(getIntent());
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
