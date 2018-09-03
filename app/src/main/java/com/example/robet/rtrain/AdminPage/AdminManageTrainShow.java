package com.example.robet.rtrain.AdminPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.adapter.ManageTrainAdapter;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.gson.TrainResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminManageTrainShow extends AppCompatActivity {

    @BindView(R.id.RecyclerView)
    android.support.v7.widget.RecyclerView RecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    ManageTrainAdapter adapter;
    Loading loading;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_manage_train_show);
        ButterKnife.bind(this);

        loading = new Loading(this);
        adapter = new ManageTrainAdapter();

        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.setAdapter(adapter);
        RecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        loading.start();

        RestApi.getData().trainShow().enqueue(new Callback<TrainResponse>() {
            @Override
            public void onResponse(Call<TrainResponse> call, Response<TrainResponse> response) {
                loading.stop();
//                adapter.trainList.addAll(response.body().getTrain());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TrainResponse> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        intent = new Intent(getApplicationContext(), AdminManageTrainAdd.class);
        startActivity(intent);
    }
}
