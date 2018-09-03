package com.example.robet.rtrain.ClientPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robet.rtrain.R;
import com.example.robet.rtrain.adapter.SeatAdapter;
import com.example.robet.rtrain.gson.SeatResponse;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.support.RestApi;

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
    boolean seat[];
    int i = 0;
    String choose = "";

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
    @BindView(R.id.tvCart)
    TextView tvCart;
    @BindView(R.id.footer)
    LinearLayout footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_pick);
        ButterKnife.bind(this);

        if (!new PurchaseTicket().status) {
            SeatPick.this.finish();
        }

        bundle = getIntent().getExtras();
        map = (HashMap<String, String>) bundle.get("extra");
        adapter = new SeatAdapter();
        loading = new Loading(this);

        tvTrainName.setText(map.get("trainName"));
        tvCategory.setText(map.get("category"));
        tvCart.setText(map.get("cart"));

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setAdapter(adapter);

        loading.start();
        RestApi.getData().SeatList(map.get("trainId"),
                map.get("date"),
                map.get("time"),
                map.get("category"),
                map.get("destination"),
                map.get("depart"),
                map.get("cart")).enqueue(new Callback<SeatResponse>() {
            @Override
            public void onResponse(Call<SeatResponse> call, Response<SeatResponse> response) {
                loading.stop();

                int size = response.body().getSeat().size();
                seat = new boolean[size];
                for (i = 0; i < size; i++) {
                    seat[i] = false;
                }

                adapter.seat = seat;
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

                seat = adapter.seat;
                int size = seat.length;

                for (i = 0; i < size; i++) {
                    if (seat[i]) {
                        if (choose.equals("")) {
                            choose = String.valueOf(i + 1);
                        } else {
                            choose += "," + String.valueOf(i + 1);
                        }
                    }
                }

                if (!choose.equals("")) {
                    map.put("choose", choose);
                    Intent intent = new Intent(getApplicationContext(), PurchaseTicket.class);
                    intent.putExtra("extra", map);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "anda harus memilih minimal satu tempat", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
