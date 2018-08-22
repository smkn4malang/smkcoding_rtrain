package com.example.robet.rtrain;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminManageTrainEdit extends AppCompatActivity {

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
    @BindView(R.id.btDelete)
    Button btDelete;
    @BindView(R.id.btUpdate)
    Button btUpdate;

    Loading loading;
    Bundle bundle;
    HashMap<String , String> data;
    String id, name, category, destination, depart, cars, price, time;
    int indexCategory, indexTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_manage_train_edit);
        ButterKnife.bind(this);

        loading = new Loading(this);
        bundle = getIntent().getExtras();
        data = (HashMap<String, String>) bundle.get("extra");

        id = data.get("id");
        name = data.get("name");
        category = data.get("category");
        destination = data.get("destination");
        depart = data.get("depart");
        cars = data.get("cars");
        price = data.get("price");
        time = data.get("time");

        switch (category) {
            case "ekonomi":
                indexCategory = 0;
                break;
            case "bisnis":
                indexCategory = 1;
                break;
            case "express":
                indexCategory = 2;
                break;
        }

        switch (time) {
            case "08.00":
                indexTime = 0;
                break;
            case "12.00":
                indexTime = 1;
                break;
            case "17.00":
                indexTime = 2;
                break;
        }

        etTrainName.setText(name);
        etDestination.setText(destination);
        etDepart.setText(depart);
        etCars.setText(cars);
        etPrice.setText(price);
        spCategory.setSelection(indexCategory);
        spTime.setSelection(indexTime);

    }

    @OnClick({R.id.btDelete, R.id.btUpdate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btDelete:

                loading.start();
                RestApi.getData().TrainDelete(id).enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {

                        loading.stop();
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        AdminManageTrainEdit.this.finish();
                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        loading.stop();
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case R.id.btUpdate:

                boolean empty = true;
                boolean same = true;

                if(!etTrainName.getText().toString().equals(name)
                        || !etDestination.getText().toString().equals(destination)
                        || !etDepart.getText().toString().equals(depart)
                        || !etCars.getText().toString().equals(cars)
                        || !etPrice.getText().toString().equals(price)
                        || !spCategory.getSelectedItem().toString().equals(category)
                        || !spTime.getSelectedItem().toString().equals(time)){
                    same = false;
                }

                if(!etTrainName.getText().toString().equals("")
                        || !etDestination.getText().toString().equals("")
                        || !etDepart.getText().toString().equals("")
                        || !etCars.getText().toString().equals("")
                        || !etPrice.getText().toString().equals("")
                        || !spCategory.getSelectedItem().toString().equals("")
                        || !spTime.getSelectedItem().toString().equals("")){
                    empty = false;
                }

                if(empty){
                    Toast.makeText(getApplicationContext(), "harus mengisi semua data", Toast.LENGTH_SHORT).show();
                }

                if(!empty && !same){

                    name = etTrainName.getText().toString();
                    destination = etDestination.getText().toString();
                    depart = etDepart.getText().toString();
                    cars = etPrice.getText().toString();
                    price = etPrice.getText().toString();
                    category = spCategory.getSelectedItem().toString();
                    time = spTime.getSelectedItem().toString();

                    loading.start();
                    RestApi.getData().TrainUpdate(id, name, category, destination, depart, cars, price, time).enqueue(new Callback<Value>() {
                        @Override
                        public void onResponse(Call<Value> call, Response<Value> response) {

                            loading.stop();
                            boolean info = response.body().getInfo();
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            AdminManageTrainEdit.this.finish();
                            if(!info){
                                startActivity(getIntent());
                            } else {
                                new AdminManageTrainShow().startActivity(getIntent());
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
