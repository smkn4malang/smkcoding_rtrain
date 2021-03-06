package com.example.robet.rtrain.ClientPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.robet.rtrain.R;
import com.example.robet.rtrain.adapter.HistoryAdapter;
import com.example.robet.rtrain.gson.HistoryResponse;
import com.example.robet.rtrain.support.Config;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.support.Value;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class History extends AppCompatActivity {

    HistoryAdapter adapter;
    Loading loading;
    Config config;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btBack)
    CardView btBack;
    @BindView(R.id.btClear)
    CardView btClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config = new Config(this);
        setTheme(config.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        ButterKnife.bind(this);

        adapter = new HistoryAdapter();
        loading = new Loading(this);
        config = new Config(this);

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(adapter);

        getData();

    }

    @OnClick({R.id.btBack, R.id.btClear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btBack:
                History.this.finish();
                break;
            case R.id.btClear:

                onClear();

                break;
        }
    }

    @Override
    protected void onActivityResult(int RequestCode, int resultCode, Intent intent){
        if(RequestCode == 25){
            Intent i = getIntent();
            finish();
            startActivity(i);
        }
    }

    private void getData(){
        loading.start();
        RestApi.getData().historyShow(String.valueOf(config.getId())).enqueue(new Callback<HistoryResponse>() {
            @Override
            public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                loading.stop();
                if(response.body().getInfo()){
                    adapter.listHistory.addAll(response.body().getHistory());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "anda belum memiliki history", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HistoryResponse> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), "jaringan bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClear(){
        loading.start();
        RestApi.getData().historyDeleteAll(String.valueOf(config.getId())).enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                loading.stop();
                Toast.makeText(getApplicationContext(), "berhasil menghapus history", Toast.LENGTH_SHORT).show();
                History.this.finish();
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), "jaringan bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
