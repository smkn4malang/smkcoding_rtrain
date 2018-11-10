package com.example.robet.rtrain.ClientPage;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robet.rtrain.AdminPage.IndexAdmin;
import com.example.robet.rtrain.MainActivity;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.promo.PromoBuy5Get1;
import com.example.robet.rtrain.promo.promoMuharram;
import com.example.robet.rtrain.support.Config;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.support.UpdateData;
import com.example.robet.rtrain.support.Value;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Index extends AppCompatActivity {

    Config config;
    Loading loading;
    String rekening;
    AlertDialog dialog;
    int tax, pay;
    boolean bank = false;
    boolean sPromo = false;
    boolean sProfile = false;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.promoMuharram)
    CardView promoMuharram;
    @BindView(R.id.buy5get1)
    CardView buy5get1;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @BindView(R.id.tvType)
    TextView tvType;
    @BindView(R.id.tvId)
    TextView tvId;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvUsername)
    TextView tvUsername;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvCredit)
    TextView tvCredit;
    @BindView(R.id.btProfile)
    CardView btProfile;
    @BindView(R.id.profile)
    CardView profile;
    @BindView(R.id.btPromo)
    CardView btPromo;
    @BindView(R.id.promo)
    CardView promo;
    @BindView(R.id.btShop2)
    LinearLayout btShop2;
    @BindView(R.id.btTicket2)
    LinearLayout btTicket2;
    @BindView(R.id.btHistory2)
    LinearLayout btHistory2;
    @BindView(R.id.btCredit2)
    LinearLayout btCredit2;
    @BindView(R.id.tvProfile)
    TextView tvProfile;
    @BindView(R.id.tvPromo)
    TextView tvPromo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        config = new Config(this);
        setTheme(config.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        ButterKnife.bind(this);
        loading = new Loading(this);

        if(config.getInfo("admin")){
            finish();
            startActivity(new Intent(getApplicationContext(), IndexAdmin.class));
        }

        if (!config.getUpdated()) {
            new UpdateData(Index.this).update();
            config.setApply(false);
        }

        onDelete();
        initComponent();

        navView.setNavigationItemSelectedListener(navItem());

    }

    private void initComponent() {

        if (config.getInfo("user")) {
            tvType.setText("User Id");
            tvId.setText(": " + String.valueOf(config.getId()));
            tvName.setText(": " + config.getName());
            tvUsername.setText(": " + config.getUsername());
            tvEmail.setText(": " + config.getEmail());
            tvCredit.setText(tvCredit.getText().toString() + String.valueOf(config.getCredit()));
        } else if (config.getInfo("guest")) {
            tvType.setText("Guest Id");
            tvId.setText(": " + String.valueOf(config.getId()));
            tvName.setText(": " + config.getName());
            tvEmail.setText(": " + config.getEmail());
            tvUsername.setVisibility(View.GONE);
            tvCredit.setVisibility(View.GONE);
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("R-Train");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private NavigationView.OnNavigationItemSelectedListener navItem() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btTicket:
                        startActivity(new Intent(getApplicationContext(), TrainShow.class));
                        break;

                    case R.id.btShop:
                        Intent intent = new Intent(getApplicationContext(), Shop.class);
                        startActivityForResult(intent, 2);
                        break;

                    case R.id.btHistory:
                        startActivity(new Intent(getApplicationContext(), History.class));
                        break;

                    case R.id.btCredit:
                        addCredit();
                        break;

                    case R.id.btSetting:
                        if (config.getInfo("guest")) {
                            Toast.makeText(getApplicationContext(), "anda harus menjadi user dahulu", Toast.LENGTH_SHORT).show();
                        } else if (config.getInfo("user")) {
                            intent = new Intent(getApplicationContext(), UserSetting.class);
                            startActivityForResult(intent, 1);
                        }
                        break;

                    case R.id.btLogout:
                        config.setInfo("guest", false);
                        config.setInfo("user", false);
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;

                    case R.id.btAbout:
                        showAbout();
                        break;
                }
                drawer_layout.closeDrawers();
                return true;
            }
        };
    }

    private void onDelete() {
        RestApi.getData().systemHistoryDelete().enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {

            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "no internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            Intent intent = getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @OnClick({R.id.promoMuharram, R.id.buy5get1})
    public void onPromoClicked(View view) {
        switch (view.getId()) {
            case R.id.promoMuharram:
                startActivity(new Intent(getApplicationContext(), promoMuharram.class));
                break;
            case R.id.buy5get1:
                startActivity(new Intent(getApplicationContext(), PromoBuy5Get1.class));
                break;
        }
    }

    private void addCredit() {
        if (config.getInfo("user")) {

            LayoutInflater layoutInflater = LayoutInflater.from(Index.this);
            final View CreditView = layoutInflater.inflate(R.layout.add_credit, null);
            final AlertDialog.Builder builder = new AlertDialog.Builder(Index.this);
            builder.setView(CreditView);
            builder.setCancelable(false);

            final AlertDialog alertDialog = builder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();

            Button btBack = CreditView.findViewById(R.id.btBack);
            Button btAdd = CreditView.findViewById(R.id.btAdd);
            Spinner spPay = CreditView.findViewById(R.id.spPay);
            final CardView cvRekening = CreditView.findViewById(R.id.cvRekening);
            final TextInputEditText etRekening = CreditView.findViewById(R.id.etRekening);
            final TextInputEditText etPay = CreditView.findViewById(R.id.etPay);
            final TextInputEditText etTax = CreditView.findViewById(R.id.etTax);
            cvRekening.setVisibility(View.GONE);

            String[] payMethod = {"indomaret", "alfamaret", "bca", "bri", "mandiri"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Index.this,
                    android.R.layout.simple_spinner_dropdown_item, payMethod);
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
                    etTax.setText(String.valueOf(tax));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            btAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String mPay = etPay.getText().toString();
                    rekening = etRekening.getText().toString();

                    if (bank) {
                        if (rekening.equals("")) {
                            bank = false;
                        } else {
                            bank = true;
                        }
                    } else {
                        bank = true;
                    }

                    if (!mPay.equals("")) {
                        pay = Integer.valueOf(mPay);
                    }

                    if (mPay.equals("")) {
                        bank = false;
                        Toast.makeText(getApplicationContext(), "masukkan uang pembayaran anda", Toast.LENGTH_SHORT).show();
                    } else if (!bank) {
                        bank = false;
                        Toast.makeText(getApplicationContext(), "masukkan nomor rekening anda", Toast.LENGTH_SHORT).show();
                    } else if (pay < 50000) {
                        bank = false;
                        Toast.makeText(getApplicationContext(), "anda harus topup minimal 50ribu", Toast.LENGTH_SHORT).show();
                    } else {

                        pay -= tax;
                        mPay = String.valueOf(pay);

                        loading.start();
                        RestApi.getData().creditAdd(config.getId(), mPay).enqueue(new Callback<Value>() {
                            @Override
                            public void onResponse(Call<Value> call, Response<Value> response) {
                                loading.stop();
                                Toast.makeText(getApplicationContext(), "kode pembayaran akan dikirim melalui email", Toast.LENGTH_SHORT).show();
                                config.setCredit(config.getCredit() + pay);
                                alertDialog.cancel();
                            }

                            @Override
                            public void onFailure(Call<Value> call, Throwable t) {
                                loading.stop();
                                Toast.makeText(getApplicationContext(), "no internet connection", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            });

            btBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.cancel();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "anda harus menjadi user terlebih dahulu", Toast.LENGTH_SHORT).show();
        }
    }

    private void showAbout(){
        LayoutInflater inflater = LayoutInflater.from(Index.this);
        View view = inflater.inflate(R.layout.about, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(Index.this);
        builder.setView(view);
        builder.setCancelable(true);

        LinearLayout btClose = view.findViewById(R.id.btClose);

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
    }

    @OnClick({R.id.btPromo, R.id.btProfile})
    public void onButtonClicked(View view){
        switch (view.getId()){
            case R.id.btPromo:

                if(sPromo){
                    tvPromo.setText("Show Promo");
                    promo.setVisibility(View.GONE);
                    sPromo = false;
                } else {
                    tvPromo.setText("Hide Promo");
                    promo.setVisibility(View.VISIBLE);
                    sPromo = true;
                }

                break;
            case R.id.btProfile:

                if(sProfile){
                    tvProfile.setText("Show Profile");
                    profile.setVisibility(View.GONE);
                    sProfile = false;
                } else {
                    tvProfile.setText("Hide Profile");
                    profile.setVisibility(View.VISIBLE);
                    sProfile = true;
                }

                break;
        }
    }

    @OnClick({R.id.btShop2, R.id.btTicket2, R.id.btCredit2, R.id.btHistory2})
    public void onPageClicked(View view){
        switch (view.getId()){
            case R.id.btShop2:
                Intent intent = new Intent(getApplicationContext(), Shop.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.btTicket2:
                startActivity(new Intent(getApplicationContext(), TrainShow.class));
                break;
            case R.id.btHistory2:
                startActivity(new Intent(getApplicationContext(), History.class));
                break;
            case R.id.btCredit2:
                addCredit();
                break;
        }
    }
}
