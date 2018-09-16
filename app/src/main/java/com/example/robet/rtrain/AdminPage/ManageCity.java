package com.example.robet.rtrain.AdminPage;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.robet.rtrain.R;
import com.example.robet.rtrain.gson.CityResponse;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.support.Value;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageCity extends AppCompatActivity {

    String[] city;
    Loading loading;
    String mCity, old;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_city);
        ButterKnife.bind(this);
        loading = new Loading(this);

        getData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                old = city[i];
                editData(old);
            }
        });
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        addData();
    }

    private void getData() {

        loading.start();

        RestApi.getData().CityList().enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                loading.stop();
                int size = response.body().getCity().size();
                city = new String[size];
                for (int i = 0; i < size; i++) {
                    city[i] = response.body().getCity().get(i).getName();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ManageCity.this, android.R.layout.simple_list_item_1, city);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                loading.stop();
            }
        });
    }

    private void addData() {

        LayoutInflater inflater = LayoutInflater.from(ManageCity.this);
        View view = inflater.inflate(R.layout.item_city_edit, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(ManageCity.this);
        builder.setCancelable(true);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        final TextInputEditText tvCity;
        Button button, button2;

        tvCity = view.findViewById(R.id.tvCity);
        button = view.findViewById(R.id.button);
        button2 = view.findViewById(R.id.button2);

        button2.setVisibility(View.GONE);
        button.setText("add");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCity = tvCity.getText().toString();
                if (mCity.equals("")) {
                    Toast.makeText(getApplicationContext(), "isi data dengan benar", Toast.LENGTH_SHORT).show();
                } else {

                    loading.start();
                    RestApi.getData().addCity(mCity).enqueue(new Callback<Value>() {
                        @Override
                        public void onResponse(Call<Value> call, Response<Value> response) {

                            loading.stop();
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.body().getInfo()) {
                                ManageCity.this.finish();
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

            }
        });
    }

    private void editData(final String old) {

        LayoutInflater inflater = LayoutInflater.from(ManageCity.this);
        View view = inflater.inflate(R.layout.item_city_edit, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(ManageCity.this);
        builder.setCancelable(true);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        final TextInputEditText tvCity;
        Button button, button2;

        tvCity = view.findViewById(R.id.tvCity);
        button = view.findViewById(R.id.button);
        button2 = view.findViewById(R.id.button2);

        tvCity.setText(old);
        button2.setVisibility(View.VISIBLE);
        button.setText("edit");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCity = tvCity.getText().toString();
                if (mCity.equals("")) {
                    Toast.makeText(getApplicationContext(), "isi data dengan benar", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getApplicationContext(), mCity, Toast.LENGTH_SHORT).show();
                    loading.start();
                    RestApi.getData().editCity(old, mCity).enqueue(new Callback<Value>() {
                        @Override
                        public void onResponse(Call<Value> call, Response<Value> response) {
                            loading.stop();
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.body().getInfo()) {
                                ManageCity.this.finish();
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

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loading.start();
                RestApi.getData().deleteCity(old).enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        loading.stop();
                        Toast.makeText(getApplicationContext(), "berhasil hapus data", Toast.LENGTH_SHORT).show();
                        ManageCity.this.finish();
                        startActivity(getIntent());
                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {

                    }
                });

            }
        });
    }
}
