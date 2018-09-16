package com.example.robet.rtrain.AdminPage.ManageTrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.robet.rtrain.gson.CategoryResponse;
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

public class AdminManageTrainEdit extends AppCompatActivity {

    @BindView(R.id.etTrainName)
    TextInputEditText etTrainName;
    @BindView(R.id.etCars)
    TextInputEditText etCars;
    @BindView(R.id.etPrice)
    TextInputEditText etPrice;
    @BindView(R.id.spCategory)
    Spinner spCategory;
    @BindView(R.id.btDelete)
    Button btDelete;
    @BindView(R.id.btUpdate)
    Button btUpdate;

    Loading loading;
    Bundle bundle;
    HashMap<String , String> data;
    String id, name, category, cars, price;
    int index;
    String[] mCategory;

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
        cars = data.get("cars");
        price = data.get("price");

        loading.start();
        RestApi.getData().showCategory().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                loading.stop();
                int size = response.body().getCategory().size();
                mCategory = new String[size];
                for(int i = 0; i < size; i++){
                    mCategory[i] = response.body().getCategory().get(i).getName();
                    if(mCategory[i].equals(category)){
                        index = i;
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminManageTrainEdit.this, android.R.layout.simple_spinner_dropdown_item, mCategory);
                spCategory.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        etTrainName.setText(name);
        etCars.setText(cars);
        etPrice.setText(price);
        spCategory.setSelection(index);

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
                        startActivity(new Intent(getApplicationContext(), AdminManageTrainShow.class));
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
                        || !etCars.getText().toString().equals(cars)
                        || !etPrice.getText().toString().equals(price)
                        || !spCategory.getSelectedItem().toString().equals(category)){
                    same = false;
                }

                if(!etTrainName.getText().toString().equals("")
                        || !etCars.getText().toString().equals("")
                        || !etPrice.getText().toString().equals("")
                        || !spCategory.getSelectedItem().toString().equals("")){
                    empty = false;
                }

                if(empty){
                    Toast.makeText(getApplicationContext(), "harus mengisi semua data", Toast.LENGTH_SHORT).show();
                }

                if(!empty && !same){

                    name = etTrainName.getText().toString();
                    cars = etPrice.getText().toString();
                    price = etPrice.getText().toString();
                    category = spCategory.getSelectedItem().toString();

                    loading.start();
                    RestApi.getData().TrainUpdate(id, name, category, price, cars).enqueue(new Callback<Value>() {
                        @Override
                        public void onResponse(Call<Value> call, Response<Value> response) {

                            loading.stop();
                            boolean info = response.body().getInfo();
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            AdminManageTrainEdit.this.finish();
                            if(!info){
                                startActivity(getIntent());
                            } else {
                                startActivity(new Intent(getApplicationContext(), AdminManageTrainShow.class));
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
