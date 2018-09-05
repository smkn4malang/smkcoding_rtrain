package com.example.robet.rtrain.ClientPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robet.rtrain.R;
import com.example.robet.rtrain.support.Config;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.support.Value;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseTicket extends AppCompatActivity {

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
    @BindView(R.id.btBuy)
    Button btBuy;

    Bundle bundle;
    HashMap<String, String> map;
    Config config;
    String trainId, trainName, userId, date, seat;
    String time, cart, category, destination, depart;
    int credit = 0;
    int price = 0;
    Loading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_ticket);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();
        map = (HashMap<String, String>) bundle.get("extra");
        config = new Config(this);
        loading = new Loading(this);

        trainId = map.get("trainId");
        trainName = map.get("trainName");
        date = map.get("date");
        seat = map.get("choose");
        destination = map.get("destination");
        depart = map.get("depart");
        time = map.get("time");
        price = Integer.valueOf(map.get("amount"));
        cart = map.get("cart");
        category = map.get("category");
        userId = String.valueOf(config.getId());
        credit = config.getCredit();

        tvTrainName.setText(trainName);
        tvCategory.setText(category);
        tvDepart.setText(depart);
        tvDestination.setText(destination);
        tvDate.setText(date);
        tvTime.setText(time);
        tvCart.setText(cart);
        tvSeat.setText(seat);
        tvPrice.setText(String.valueOf(price));
        tvCredit.setText(String.valueOf(credit));

    }

    @OnClick({R.id.btBack, R.id.btBuy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btBack:
                map.remove("choose");
                PurchaseTicket.this.finish();
                break;
            case R.id.btBuy:

                if(credit < price){
                    Toast.makeText(getApplicationContext(), "uang anda kurang", Toast.LENGTH_SHORT).show();
                } else {

                    loading.start();
                    RestApi.getData().ticketPurchase(
                            trainId, userId, date, seat, destination, depart,
                            time, String.valueOf(price), String.valueOf(credit), cart, "user"
                    ).enqueue(new Callback<Value>() {
                        @Override
                        public void onResponse(Call<Value> call, Response<Value> response) {
                            loading.stop();
                            if(response.body().getInfo()){

                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                config.setCredit(response.body().getCredit());

                                Intent intent = new Intent(getApplicationContext(), Index.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<Value> call, Throwable t) {
                            loading.stop();
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }

                break;
        }
    }
}
