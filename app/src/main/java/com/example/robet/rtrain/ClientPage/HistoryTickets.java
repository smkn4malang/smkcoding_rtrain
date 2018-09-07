package com.example.robet.rtrain.ClientPage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robet.rtrain.R;
import com.example.robet.rtrain.gson.HistoryResponse;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.support.TicketHistory;
import com.example.robet.rtrain.support.Value;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryTickets extends AppCompatActivity {

    @BindView(R.id.tvTrainName)
    TextView tvTrainName;
    @BindView(R.id.tvCategory)
    TextView tvCategory;
    @BindView(R.id.tvDepart)
    TextView tvDepart;
    @BindView(R.id.tvDestination)
    TextView tvDestination;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvCart)
    TextView tvCart;
    @BindView(R.id.tvSeat)
    TextView tvSeat;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvCredit)
    TextView tvCredit;
    @BindView(R.id.btBack)
    Button btBack;
    @BindView(R.id.btDelete)
    Button btDelete;

    String id;
    Bundle bundle;
    Loading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_tickets);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();
        id = (String) bundle.get("id");
        loading = new Loading(this);

        loading.start();
        RestApi.getData().ticketHistory(id).enqueue(new Callback<TicketHistory>() {
            @Override
            public void onResponse(Call<TicketHistory> call, Response<TicketHistory> response) {

                int cars = Integer.valueOf(response.body().getSeat());
                cars = (cars + 1) /20;

                loading.stop();
                tvTrainName.setText(response.body().getTrainName());
                tvCategory.setText(response.body().getCategory());
                tvDate.setText(response.body().getDate());
                tvSeat.setText(response.body().getSeat());
                tvDestination.setText(response.body().getDestination());
                tvDepart.setText(response.body().getDepart());
                tvTime.setText(response.body().getTrainName());
                tvCart.setText(String.valueOf(cars));

            }

            @Override
            public void onFailure(Call<TicketHistory> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick({R.id.btBack, R.id.btDelete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btBack:
                HistoryTickets.this.finish();
                break;
            case R.id.btDelete:

                loading.start();
                RestApi.getData().historyDelete(id).enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        loading.stop();
                        Toast.makeText(getApplicationContext(), "berhasil hapus data", Toast.LENGTH_SHORT).show();
                        HistoryTickets.this.finish();
                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        loading.stop();
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                break;
        }
    }
}
