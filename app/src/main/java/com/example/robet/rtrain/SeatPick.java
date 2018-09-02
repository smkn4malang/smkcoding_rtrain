package com.example.robet.rtrain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeatPick extends AppCompatActivity {

    Bundle bundle;
    HashMap<String, String> map;
    SeatAdapter adapter;
    Loading loading;

    @BindView(R.id.tvTrainName)
    TextView tvTrainName;
    @BindView(R.id.tvCategory)
    TextView tvCategory;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btBack)
    Button btBack;
    @BindView(R.id.btNext)
    Button btNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_pick);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();
        map = (HashMap<String, String>) bundle.get("extra");
        adapter = new SeatAdapter();
        loading = new Loading(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        loading.start();
        RestApi.getData().SeatList(map.get("trainId"),
                map.get("date"),
                map.get("time"),
                map.get("category"),
                map.get("destination"),
                map.get("depart")).enqueue(new Callback<SeatResponse>() {
            @Override
            public void onResponse(Call<SeatResponse> call, Response<SeatResponse> response) {
                loading.stop();
                adapter.ListSeat.addAll(response.body().getSeat());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<SeatResponse> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick({R.id.btBack, R.id.btNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btBack:
                SeatPick.this.finish();
                break;
            case R.id.btNext:
                break;
        }
    }
}
