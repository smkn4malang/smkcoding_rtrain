package com.example.robet.rtrain.ClientPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
    @BindView(R.id.etPay)
    TextInputEditText etPay;
    @BindView(R.id.btBack)
    Button btBack;
    @BindView(R.id.btBuy)
    Button btBuy;
    @BindView(R.id.spPay)
    Spinner spPay;
    @BindView(R.id.etRekening)
    TextInputEditText etRekening;
    @BindView(R.id.cvRekening)
    CardView cvRekening;

    HashMap<String, String> map;
    Bundle bundle;
    Loading loading;
    Config config;
    String trainId, trainName, guestId, date, seat, mPay;
    String time, cart, category, destination, depart, rekening;
    boolean bank = false;
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

        cvRekening.setVisibility(View.GONE);

        String[] mPay = {"alfamaret", "indomaret", "bca", "bri", "mandiri"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(PurchaseTicketGuest.this,
                android.R.layout.simple_spinner_dropdown_item, mPay);
        spPay.setAdapter(adapter);
        spPay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = adapterView.getSelectedItemPosition();
                switch (index) {
                    case 0:
                        tax = 2500;
                        cvRekening.setVisibility(View.GONE);
                        bank = false;
                        break;
                    case 1:
                        tax = 2500;
                        cvRekening.setVisibility(View.GONE);
                        bank = false;
                        break;
                    case 2:
                        tax = 5000;
                        cvRekening.setVisibility(View.VISIBLE);
                        bank = true;
                        break;
                    case 3:
                        tax = 7500;
                        cvRekening.setVisibility(View.VISIBLE);
                        bank = true;
                        break;
                    case 4:
                        tax = 5000;
                        cvRekening.setVisibility(View.VISIBLE);
                        bank = true;
                        break;
                }
                finalPrice = price + tax;
                tvPrice.setText(String.valueOf(finalPrice));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @OnClick({R.id.btBack, R.id.btBuy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btBack:
                map.remove("choose");
                PurchaseTicketGuest.this.finish();
                break;
            case R.id.btBuy:

                mPay = etPay.getText().toString();
                pay = Integer.valueOf(mPay);
                rekening = etRekening.getText().toString();

                if(bank){
                    if(rekening.equals("")){
                        bank = false;
                    } else {
                        bank = true;
                    }
                } else {
                    bank = true;
                }

                if(!bank){
                    Toast.makeText(getApplicationContext(), "masukkan nomor rekening anda", Toast.LENGTH_SHORT).show();
                } else if (mPay.equals("")) {
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

