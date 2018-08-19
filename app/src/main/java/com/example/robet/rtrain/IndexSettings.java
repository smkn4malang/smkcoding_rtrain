package com.example.robet.rtrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IndexSettings extends AppCompatActivity {

    Config config;
    Intent newAct;
    Loading loading;

    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.btName)
    Button btName;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btPassword)
    Button btPassword;
    @BindView(R.id.btCredit)
    Button btCredit;
    @BindView(R.id.btHistory)
    Button btHistory;
    @BindView(R.id.btLogout)
    Button btLogout;
    @BindView(R.id.etCredit)
    EditText etCredit;
    @BindView(R.id.btBack)
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_settings);
        ButterKnife.bind(this);

        config = new Config(this);
        etName.setText(config.getName());
        etCredit.setText(String.valueOf(config.getCredit()));
        etPassword.setText(config.getPassword());

        loading = new Loading(this);
    }

    @OnClick({R.id.btName, R.id.btPassword, R.id.btCredit, R.id.btHistory, R.id.btLogout, R.id.btBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btName:

                if(btName.getText().toString().equals("edit")){
                    etName.setEnabled(true);
                    btName.setText("change");
                } else if(btName.getText().toString().equals("change")){
                    btName.setText("edit");
                    etName.setEnabled(false);
                    loading.start();

                    RestApi.getData().ChangeName(config.getId(), etName.getText().toString()).enqueue(new Callback<Value>() {
                        @Override
                        public void onResponse(Call<Value> call, Response<Value> response) {
                            loading.stop();
                            config.setName(response.body().getName());
                            String message = response.body().getMessage();
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Value> call, Throwable t) {
                            loading.stop();
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }

                break;
            case R.id.btPassword:

                if(btPassword.getText().toString().equals("edit")){
                    etPassword.setEnabled(true);
                    btPassword.setText("change");
                } else if (btPassword.getText().toString().equals("change")){
                    etPassword.setEnabled(false);
                    btPassword.setText("edit");
                    loading.start();
                    RestApi.getData().ChangePW(config.getId(), etPassword.getText().toString()).enqueue(new Callback<Value>() {
                        @Override
                        public void onResponse(Call<Value> call, Response<Value> response) {
                            loading.stop();
                            String message = response.body().getMessage();
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Value> call, Throwable t) {
                            loading.stop();
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                break;
            case R.id.btCredit:

                newAct = new Intent(getApplicationContext(), addCredit.class);
                startActivity(newAct);

                break;
            case R.id.btHistory:

                newAct = new Intent(getApplicationContext(), showHistory.class);
                startActivity(newAct);

                break;
            case R.id.btLogout:

                config.setInfo("user", false);
                newAct = new Intent(getApplicationContext(), MainActivity.class);
                newAct.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                newAct.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(newAct);

                break;
            case R.id.btBack:

                newAct = new Intent(getApplicationContext(), Index.class);
                newAct.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                newAct.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(newAct);

                break;
        }
    }
}
