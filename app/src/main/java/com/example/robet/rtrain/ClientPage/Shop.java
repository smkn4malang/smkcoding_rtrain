package com.example.robet.rtrain.ClientPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robet.rtrain.R;
import com.example.robet.rtrain.adapter.ItemAdapter;
import com.example.robet.rtrain.gson.ItemResponse;
import com.example.robet.rtrain.support.Config;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.support.RestApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Shop extends AppCompatActivity {

    Intent intent;
    ItemAdapter adapter;
    Loading loading;
    Config config;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvHappy)
    TextView tvHappy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config = new Config(this);
        setTheme(config.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);
        ButterKnife.bind(this);

        adapter = new ItemAdapter();
        loading = new Loading(this);

        recyclerView.setLayoutManager(new GridLayoutManager(Shop.this, 2));
        recyclerView.setAdapter(adapter);

        loading.start();
        RestApi.getData().ItemShow().enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                loading.stop();
                adapter.listItem.addAll(response.body().getItem());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (config.getInfo("user")) {
            intent = new Intent();
            setResult(2, intent);
        }
        Shop.this.finish();
    }
}
