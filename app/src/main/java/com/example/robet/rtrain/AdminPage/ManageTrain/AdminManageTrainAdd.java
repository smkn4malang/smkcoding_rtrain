package com.example.robet.rtrain.AdminPage.ManageTrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.support.design.widget.TextInputEditText;
import android.widget.Spinner;
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

public class AdminManageTrainAdd extends AppCompatActivity {

    @BindView(R.id.etTrainName)
    TextInputEditText etTrainName;
    @BindView(R.id.etDestination)
    TextInputEditText etDestination;
    @BindView(R.id.etDepart)
    TextInputEditText etDepart;
    @BindView(R.id.etCars)
    TextInputEditText etCars;
    @BindView(R.id.etPrice)
    TextInputEditText etPrice;
    @BindView(R.id.spTime)
    Spinner spTime;
    @BindView(R.id.spCategory)
    Spinner spCategory;
    @BindView(R.id.btBack)
    Button btBack;
    @BindView(R.id.btAdd)
    Button btAdd;
    Loading loading;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_manage_train_add);
        ButterKnife.bind(this);

        loading = new Loading(this);
    }

    @OnClick({R.id.btBack, R.id.btAdd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btBack:

                intent = new Intent(getApplicationContext(), AdminManageTrainShow.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                break;
            case R.id.btAdd:

                String trainName, destination, depart, time, cars, price, category;
                trainName = etTrainName.getText().toString();
                destination = etDestination.getText().toString();
                depart = etDepart.getText().toString();
                cars = etCars.getText().toString();
                price = etPrice.getText().toString();
                time = spTime.getSelectedItem().toString();
                category = spCategory.getSelectedItem().toString();

                loading.start();
                RestApi.getData().TrainAdd(trainName, category, price, cars).enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {

                        loading.stop();
                        Toast.makeText(getApplicationContext(), response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        AdminManageTrainAdd.this.finish();
                        startActivity(new Intent(getApplicationContext(), AdminManageTrainShow.class));

                        if(response.body().getInfo() == false) {
                            startActivity(getIntent());
                        }
                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        loading.stop();
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                break;
        }
    }
}
