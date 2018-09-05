package com.example.robet.rtrain.ClientPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class PurchaseTicketGuest extends AppCompatActivity {

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
    @BindView(R.id.btAlfamaret)
    LinearLayout btAlfamaret;
    @BindView(R.id.btIndomaret)
    LinearLayout btIndomaret;
    @BindView(R.id.btBri)
    LinearLayout btBri;
    @BindView(R.id.btMandiri)
    LinearLayout btMandiri;
    @BindView(R.id.btBca)
    LinearLayout btBca;
    @BindView(R.id.etPay)
    TextInputEditText etPay;
    @BindView(R.id.btBack)
    Button btBack;
    @BindView(R.id.btBuy)
    Button btBuy;

    HashMap<String, String> map;
    Bundle bundle;
    Loading loading;
    Config config;
    String trainId, trainName, guestId, date, seat, mPay;
    String time, cart, category, destination, depart;
    int pay = 0;
    int finalPrice = 0;
    int price = 0;
    int tax = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_ticket_guest);
        ButterKnife.bind(this);

        loading = new Loading(this);
        config = new Config(this);
        bundle = getIntent().getExtras();
        map = (HashMap<String, String>) bundle.get("extra");
        btAlfamaret.setBackgroundResource(R.drawable.stroke2);
        tax = 2500;

        trainId = map.get("trainId");
        trainName = map.get("trainName");
        date = map.get("date");
        seat = map.get("choose");
        destination = map.get("destination");
        depart = map.get("depart");
        time = map.get("time");
        cart = map.get("cart");
        category = map.get("category");
        guestId = String.valueOf(config.getId());
        price = Integer.valueOf(map.get("amount"));
        finalPrice = price + tax;

        tvTrainName.setText(trainName);
        tvCategory.setText(category);
        tvDepart.setText(depart);
        tvDestination.setText(destination);
        tvDate.setText(date);
        tvTime.setText(time);
        tvCart.setText(cart);
        tvSeat.setText(seat);
        tvPrice.setText(String.valueOf(finalPrice));

    }

    @OnClick({R.id.btAlfamaret, R.id.btIndomaret, R.id.btBri, R.id.btMandiri, R.id.btBca, R.id.btBack, R.id.btBuy, R.id.llButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btAlfamaret:

                btAlfamaret.setBackgroundResource(R.drawable.stroke2);
                btIndomaret.setBackgroundResource(R.drawable.stroke1);
                btMandiri.setBackgroundResource(R.drawable.stroke1);
                btBca.setBackgroundResource(R.drawable.stroke1);
                btBri.setBackgroundResource(R.drawable.stroke1);

                tax = 2500;
                finalPrice = price + tax;
                tvPrice.setText(String.valueOf(finalPrice));

                break;
            case R.id.btIndomaret:

                btAlfamaret.setBackgroundResource(R.drawable.stroke1);
                btIndomaret.setBackgroundResource(R.drawable.stroke2);
                btMandiri.setBackgroundResource(R.drawable.stroke1);
                btBca.setBackgroundResource(R.drawable.stroke1);
                btBri.setBackgroundResource(R.drawable.stroke1);

                tax = 3000;
                finalPrice = price + tax;
                tvPrice.setText(String.valueOf(finalPrice));

                break;
            case R.id.btBri:

                btAlfamaret.setBackgroundResource(R.drawable.stroke1);
                btIndomaret.setBackgroundResource(R.drawable.stroke1);
                btMandiri.setBackgroundResource(R.drawable.stroke1);
                btBca.setBackgroundResource(R.drawable.stroke1);
                btBri.setBackgroundResource(R.drawable.stroke2);

                tax = 5000;
                finalPrice = price + tax;
                tvPrice.setText(String.valueOf(finalPrice));

                break;
            case R.id.btMandiri:

                btAlfamaret.setBackgroundResource(R.drawable.stroke2);
                btIndomaret.setBackgroundResource(R.drawable.stroke1);
                btMandiri.setBackgroundResource(R.drawable.stroke1);
                btBca.setBackgroundResource(R.drawable.stroke1);
                btBri.setBackgroundResource(R.drawable.stroke1);

                tax = 2000;
                finalPrice = price + tax;
                tvPrice.setText(String.valueOf(finalPrice));

                break;
            case R.id.btBca:

                btAlfamaret.setBackgroundResource(R.drawable.stroke2);
                btIndomaret.setBackgroundResource(R.drawable.stroke1);
                btMandiri.setBackgroundResource(R.drawable.stroke1);
                btBca.setBackgroundResource(R.drawable.stroke1);
                btBri.setBackgroundResource(R.drawable.stroke1);

                tax = 5000;
                finalPrice = price + tax;
                tvPrice.setText(String.valueOf(finalPrice));

                break;
            case R.id.btBack:
                map.remove("choose");
                PurchaseTicketGuest.this.finish();
                break;
            case R.id.btBuy:

                mPay = etPay.getText().toString();
                pay = Integer.valueOf(mPay);

                if (mPay.equals("")) {
                    Toast.makeText(getApplicationContext(), "masukkan uang pembayaran dahulu", Toast.LENGTH_SHORT).show();
                } else if (pay < finalPrice) {
                    Toast.makeText(getApplicationContext(), "uang anda kurang", Toast.LENGTH_SHORT).show();
                } else {

                    loading.start();
                    RestApi.getData().ticketPurchase(
                            trainId, guestId, date, seat, destination, depart,
                            time, String.valueOf(price), String.valueOf(finalPrice), cart, "guest"
                    ).enqueue(new Callback<Value>() {
                        @Override
                        public void onResponse(Call<Value> call, Response<Value> response) {
                            loading.stop();
                            if (response.body().getInfo()) {

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

