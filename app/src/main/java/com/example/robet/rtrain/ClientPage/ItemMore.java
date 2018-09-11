package com.example.robet.rtrain.ClientPage;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.robet.rtrain.R;
import com.bumptech.glide.Glide;
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

public class ItemMore extends AppCompatActivity {


    @BindView(R.id.itemPic)
    ImageView itemPic;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvDesc)
    TextView tvDesc;
    @BindView(R.id.btBack)
    CardView btBack;
    @BindView(R.id.btBuy)
    CardView btBuy;

    String id, name, price, pic, userId, Price, address, mPay, mBank, desc;
    boolean bank;
    Loading loading;
    Config config;
    int amount = 1, credit, tax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_more);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        HashMap<String, String> map = (HashMap<String, String>) bundle.get("data");
        loading = new Loading(this);
        config = new Config(this);

        id = map.get("id");
        name = map.get("name");
        price = map.get("price");
        pic = map.get("pic");
        desc = map.get("desc");
        credit = config.getCredit();

        tvName.setText(tvName.getText().toString() + name);
        tvPrice.setText(tvPrice.getText().toString() + price);
        tvDesc.setText(desc);
        Glide.with(getApplicationContext()).load(pic).into(itemPic);

    }

    @OnClick({R.id.btBack, R.id.btBuy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btBack:
                ItemMore.this.finish();
                break;
            case R.id.btBuy:
                showDialog(ItemMore.this);
                break;
        }
    }

    private void showDialog(Context mCtx) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);

        if (config.getInfo("user")) {
            view = layoutInflater.inflate(R.layout.item_buy_user, null);
        } else {
            view = layoutInflater.inflate(R.layout.item_buy_guest, null);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        builder.setView(view).setCancelable(true);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        if(config.getInfo("user")){
            user(mCtx, view, dialog);
        } else {
            guest(mCtx, view, dialog);
        }

    }

    private void user(final Context mCtx, View view, final AlertDialog dialog){

        de.hdodenhof.circleimageview.CircleImageView itemPic;
        final TextView tvName, tvAmount;
        ImageView btPlus, btMin;
        Button btCancel, btBuy;
        final TextInputEditText tvPrice, tvCredit, etAddress;

        etAddress = view.findViewById(R.id.etAddress);
        itemPic = view.findViewById(R.id.itemPic);
        tvName = view.findViewById(R.id.tvName);
        tvAmount = view.findViewById(R.id.tvAmount);
        btPlus = view.findViewById(R.id.btPlus);
        btMin = view.findViewById(R.id.btMin);
        btCancel = view.findViewById(R.id.btCancel);
        btBuy = view.findViewById(R.id.btBuy);
        tvPrice = view.findViewById(R.id.tvPrice);
        tvCredit = view.findViewById(R.id.tvCredit);

        Glide.with(mCtx).load(pic).into(itemPic);
        tvName.setText(name);
        tvAmount.setText(String.valueOf(amount));
        credit = config.getCredit();
        tvCredit.setText(String.valueOf(credit));
        tvPrice.setText(price);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount += 1;
                tvAmount.setText(String.valueOf(amount));
                tvPrice.setText(String.valueOf(Integer.valueOf(price) * amount));
            }
        });

        btMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount > 1){
                    amount -= 1;
                    tvAmount.setText(String.valueOf(amount));
                    tvPrice.setText(String.valueOf(Integer.valueOf(price) * amount));
                }
            }
        });

        btBuy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                userId = String.valueOf(config.getId());
                Price = tvPrice.getText().toString();
                address = etAddress.getText().toString();

                if(address.equals("")){
                    Toast.makeText(mCtx, "masukkan alamat anda", Toast.LENGTH_SHORT).show();
                } else if (Integer.valueOf(Price) <= credit) {
                    loading.start();
                    RestApi.getData().itemBuy(userId, id, String.valueOf(amount), Price, address, config.getEmail(), "1").enqueue(new Callback<Value>() {
                        @Override
                        public void onResponse(Call<Value> call, Response<Value> response) {
                            loading.stop();
                            Toast.makeText(mCtx, "detail pembelian akan dikirim melalui email", Toast.LENGTH_SHORT).show();
                            config.setCredit(config.getCredit() - Integer.valueOf(Price));
                            credit = config.getCredit() - Integer.valueOf(Price);
                            amount = 1;
                            dialog.cancel();
                        }

                        @Override
                        public void onFailure(Call<Value> call, Throwable t) {
                            loading.stop();
                            Toast.makeText(mCtx, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(mCtx, "uang anda kurang", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void guest(final Context mCtx, View view, final AlertDialog dialog){

        final String[] pay = {"indomaret", "alfamaret", "bca", "bri", "mandiri"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mCtx, android.R.layout.simple_spinner_dropdown_item, pay);

        de.hdodenhof.circleimageview.CircleImageView itemPic;
        final TextInputLayout layoutPay;
        final TextView tvName, tvAmount;
        final TextInputEditText tvPrice, etAddress, etPay, etRekening;
        ImageView btMin, btPlus;
        Button btCancel, btBuy;
        Spinner spPay;

        etRekening = view.findViewById(R.id.etRekening);
        etAddress = view.findViewById(R.id.etAddress);
        itemPic = view.findViewById(R.id.itemPic);
        tvName = view.findViewById(R.id.tvName);
        tvAmount = view.findViewById(R.id.tvAmount);
        btPlus = view.findViewById(R.id.btPlus);
        btMin = view.findViewById(R.id.btMin);
        btCancel = view.findViewById(R.id.btCancel);
        btBuy = view.findViewById(R.id.btBuy);
        tvPrice = view.findViewById(R.id.tvPrice);
        layoutPay = view.findViewById(R.id.layoutPay);
        etPay = view.findViewById(R.id.etPay);
        spPay = view.findViewById(R.id.spPay);

        Glide.with(mCtx).load(pic).into(itemPic);
        userId = String.valueOf(config.getId());
        tvName.setText(name);
        tvAmount.setText(String.valueOf(amount));
        credit = config.getCredit();
        layoutPay.setVisibility(View.GONE);
        spPay.setAdapter(adapter);
        tax = 2500;
        tvPrice.setText(String.valueOf(Integer.valueOf(price) + tax));

        spPay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = adapterView.getSelectedItemPosition();
                switch (index) {
                    case 0:
                        tax = 2500;
                        layoutPay.setVisibility(View.GONE);
                        tvPrice.setText(String.valueOf((Integer.valueOf(price) * amount) + tax));
                        bank = false;
                        break;
                    case 1:
                        tax = 2500;
                        layoutPay.setVisibility(View.GONE);
                        tvPrice.setText(String.valueOf((Integer.valueOf(price) * amount) + tax));
                        bank = false;
                        break;
                    case 2:
                        tax = 5000;
                        layoutPay.setVisibility(View.VISIBLE);
                        tvPrice.setText(String.valueOf((Integer.valueOf(price) * amount) + tax));
                        bank = true;
                        break;
                    case 3:
                        tax = 7500;
                        layoutPay.setVisibility(View.VISIBLE);
                        tvPrice.setText(String.valueOf((Integer.valueOf(price) * amount) + tax));
                        bank = true;
                        break;
                    case 4:
                        tax = 5000;
                        layoutPay.setVisibility(View.VISIBLE);
                        tvPrice.setText(String.valueOf((Integer.valueOf(price) * amount) + tax));
                        bank = true;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount += 1;
                tvAmount.setText(String.valueOf(amount));
                tvPrice.setText(String.valueOf((Integer.valueOf(price) * amount) + tax));
            }
        });

        btMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount > 1){
                    amount -= 1;
                    tvAmount.setText(String.valueOf(amount));
                    tvPrice.setText(String.valueOf(Integer.valueOf(price) * amount));
                }
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mBank = etRekening.getText().toString();
                price = tvPrice.getText().toString();
                mPay = etPay.getText().toString();
                address = etAddress.getText().toString();

                if(bank){
                    if(mBank.equals("")){
                        bank = false;
                    } else {
                        bank = true;
                    }
                } else {
                    bank = true;
                }

                if(!bank){
                    Toast.makeText(mCtx, "masukkan nomor rekening", Toast.LENGTH_SHORT).show();
                    bank = false;
                } else if(mPay.equals("")){
                    Toast.makeText(mCtx, "masukkan uang pembayaran anda", Toast.LENGTH_SHORT).show();
                    bank = false;
                } else if(address.equals("")){
                    Toast.makeText(mCtx, "masukkan alamat anda", Toast.LENGTH_SHORT).show();
                    bank = false;
                } else if(Integer.valueOf(price) > Integer.valueOf(mPay) ) {
                    Toast.makeText(mCtx, "uang anda kurang", Toast.LENGTH_SHORT).show();
                    bank = false;
                } else {

                    bank = false;
                    loading.start();
                    RestApi.getData().itemBuy(userId, id, String.valueOf(amount), price, address, config.getEmail(),"2").enqueue(new Callback<Value>() {
                        @Override
                        public void onResponse(Call<Value> call, Response<Value> response) {
                            loading.stop();
                            Toast.makeText(mCtx, "detail pembelian akan dikirim melalui email", Toast.LENGTH_SHORT).show();
                            amount = 1;
                            dialog.cancel();
                        }

                        @Override
                        public void onFailure(Call<Value> call, Throwable t) {
                            loading.stop();
                            Toast.makeText(mCtx, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

}
