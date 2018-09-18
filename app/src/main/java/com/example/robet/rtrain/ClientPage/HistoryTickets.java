package com.example.robet.rtrain.ClientPage;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robet.rtrain.R;
import com.example.robet.rtrain.support.Config;
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

    String id, pid;
    Bundle bundle;
    Loading loading;
    Config config;

    @BindView(R.id.tvTrainName)
    TextView tvTrainName;
    @BindView(R.id.tvCategory)
    TextView tvCategory;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvDepart)
    TextInputEditText tvDepart;
    @BindView(R.id.tvDestination)
    TextInputEditText tvDestination;
    @BindView(R.id.tvCart)
    TextInputEditText tvCart;
    @BindView(R.id.tvSeat)
    TextInputEditText tvSeat;
    @BindView(R.id.tvKtp)
    TextInputEditText tvKtp;
    @BindView(R.id.tvPrice)
    TextInputEditText tvPrice;
    @BindView(R.id.btBack)
    Button btBack;
    @BindView(R.id.btDelete)
    Button btDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config = new Config(this);
        setTheme(config.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_tickets);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();
        id = (String) bundle.get("id");
        pid = (String) bundle.get("pid");
        loading = new Loading(this);

        loading.start();
        RestApi.getData().ticketHistory(pid).enqueue(new Callback<TicketHistory>() {
            @Override
            public void onResponse(Call<TicketHistory> call, Response<TicketHistory> response) {

                int cars = Integer.valueOf(response.body().getSeat());
                cars = (cars / 20) + 1;

                loading.stop();
                tvTrainName.setText(response.body().getTrainName());
                tvCategory.setText(response.body().getCategory());
                tvDate.setText(response.body().getDate());
                tvSeat.setText(response.body().getSeat());
                tvDestination.setText(response.body().getDestination());
                tvDepart.setText(response.body().getDepart());
                tvTime.setText(response.body().getTime());
                tvCart.setText(String.valueOf(cars));
                tvPrice.setText(response.body().getPrice());
                tvKtp.setText(response.body().getKtp());

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
                        setResult(25);
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
