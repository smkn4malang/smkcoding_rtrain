package com.example.robet.rtrain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class showHistory extends AppCompatActivity {

    @BindView(R.id.listHistory)
    RecyclerView listHistory;

    historyAdapter adapter;
    Config config;
    Loading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_history);
        ButterKnife.bind(this);

        loading = new Loading(this);
        config = new Config(this);
        adapter = new historyAdapter();
        listHistory.setLayoutManager(new LinearLayoutManager(this));
        listHistory.setAdapter(adapter);
        listHistory.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        loading.start();
        RestApi.getData().HistoryShow(config.getId()).enqueue(new Callback<HistoryResponse>() {
            @Override
            public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                loading.stop();
                adapter.listHistory.addAll(response.body().getHistory());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<HistoryResponse> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
