package com.example.robet.rtrain;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IndexTicket extends AppCompatActivity {

    TicketAdapter adapter;
    Loading loading;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_ticket);
        ButterKnife.bind(this);

        adapter = new TicketAdapter();
        loading = new Loading(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        loading.start();
        RestApi.getData().BookingShow().enqueue(new Callback<TrainResponse>() {
            @Override
            public void onResponse(Call<TrainResponse> call, Response<TrainResponse> response) {
                loading.stop();
                adapter.trainList.addAll(response.body().getTrain());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TrainResponse> call, Throwable t) {
                loading.stop();
            }
        });
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
    }
}
