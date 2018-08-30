package com.example.robet.rtrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityList extends AppCompatActivity {

    @BindView(R.id.SpDestination)
    Spinner SpDestination;
    @BindView(R.id.SpDepart)
    Spinner SpDepart;
    @BindView(R.id.SpTime)
    Spinner SpTime;
    @BindView(R.id.btBack)
    AppCompatButton btBack;
    @BindView(R.id.BtNext)
    AppCompatButton BtNext;
    @BindView(R.id.container)
    CardView container;

    Loading loading;
    HashMap<String, String> map;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_list);
        ButterKnife.bind(this);

        loading = new Loading(this);
        bundle = getIntent().getExtras();
        map = (HashMap<String, String>) bundle.get("extra");

        AddCity(SpDepart);
        AddCity(SpDestination);
        AddTime(SpTime);
        container.setVisibility(View.VISIBLE);
    }

    private void AddCity(final Spinner sp) {
        loading.start();
        RestApi.getData().CityList().enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                loading.stop();

                List<CityItem> city = response.body().getCity();
                List<String> spinner = new ArrayList<String>();
                for (int i = 0; i < city.size(); i++) {
                    spinner.add(city.get(i).getName());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.support_simple_spinner_dropdown_item, spinner);
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                sp.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void AddTime(final Spinner sp) {
        loading.start();
        RestApi.getData().TimeShow().enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                loading.stop();

                List<CityItem> city = response.body().getCity();
                List<String> spinner = new ArrayList<String>();
                for (int i = 0; i < city.size(); i++) {
                    spinner.add(city.get(i).getName());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.support_simple_spinner_dropdown_item, spinner);
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                sp.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.btBack, R.id.BtNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btBack:
                CityList.this.finish();
                break;
            case R.id.BtNext:

                String depart, destination, time;
                depart = SpDepart.getSelectedItem().toString();
                destination = SpDestination.getSelectedItem().toString();
                time = SpDepart.getSelectedItem().toString();

                map.put("depart", depart);
                map.put("destination", destination);
                map.put("time", time);

                Intent intent = new Intent(getApplicationContext(), PickSeat.class);
                intent.putExtra("extra", map);
                startActivity(intent);

                break;
        }
    }
}
