package com.example.robet.rtrain.ClientPage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.gson.CityResponse;
import com.example.robet.rtrain.gson.HistoryItem;
import com.example.robet.rtrain.support.Config;
import com.example.robet.rtrain.support.ItemHistory;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.support.TicketHistory;
import com.example.robet.rtrain.support.Value;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryItems extends AppCompatActivity {

    Bundle bundle;
    Loading loading;
    Config config;
    String itemName, desc, Qty, pic, price, date, address, id, pid;

    @BindView(R.id.itemPic)
    CircleImageView itemPic;
    @BindView(R.id.tvItemName)
    TextView tvItemName;
    @BindView(R.id.tvQty)
    TextView tvQty;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvDesc)
    TextView tvDesc;
    @BindView(R.id.btBack)
    Button btBack;
    @BindView(R.id.btDelete)
    Button btDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config = new Config(this);
        setTheme(config.getResourche());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_items);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();
        id = (String) bundle.get("id");
        pid = (String) bundle.get("pid");
        loading = new Loading(this);

        loading.start();
        RestApi.getData().historyItem(pid).enqueue(new Callback<ItemHistory>() {
            @Override
            public void onResponse(Call<ItemHistory> call, Response<ItemHistory> response) {

                loading.stop();

                itemName = response.body().getItemName();
                desc = response.body().getDesc();
                Qty = response.body().getQty();
                pic = response.body().getPic();
                price = response.body().getPrice();
                date = response.body().getDate();
                address = response.body().getAddress();

                Glide.with(getApplicationContext()).load(pic).into(itemPic);

                tvItemName.setText(itemName);
                tvDesc.setText(desc);
                tvQty.setText(Qty);
                tvPrice.setText(price);
                tvDate.setText(date);
                tvAddress.setText(address);

            }

            @Override
            public void onFailure(Call<ItemHistory> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick({R.id.btBack, R.id.btDelete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btBack:
                HistoryItems.this.finish();
                break;
            case R.id.btDelete:

                loading.start();
                RestApi.getData().historyDelete(id).enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        loading.stop();
                        Toast.makeText(getApplicationContext(), "berhasil hapus data", Toast.LENGTH_SHORT).show();
                        setResult(25);
                        HistoryItems.this.finish();
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
