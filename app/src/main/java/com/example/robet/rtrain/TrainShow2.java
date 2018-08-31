package com.example.robet.rtrain;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainShow2 extends Activity {


    Loading loading;
    HashMap<String, String> map;
    Bundle bundle;
    String time, destination, depart, text, name;
    ListView listView;
    int i = 0, j = 0;
    String[] cityAdapterItem;
    String[] timeAdapterItem;
    ArrayAdapter<String> CityAdapter;
    ArrayAdapter<String> TimeAdapter;

    @BindView(R.id.ImgBack)
    ImageView ImgBack;
    @BindView(R.id.tvDepart)
    TextView tvDepart;
    @BindView(R.id.btDepart)
    LinearLayout btDepart;
    @BindView(R.id.tvDestination)
    TextView tvDestination;
    @BindView(R.id.btDestination)
    LinearLayout btDestination;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.btTime)
    LinearLayout btTime;
    @BindView(R.id.btBack)
    Button btBack;
    @BindView(R.id.btNext)
    Button btNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_show_2);
        ButterKnife.bind(this);

        listView = new ListView(this);
        loading = new Loading(this);
        bundle = getIntent().getExtras();
        map = (HashMap<String, String>) bundle.get("extra");

        loading.start();
        RestApi.getData().CityList().enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                j = Integer.valueOf(response.body().getCity().size());
                cityAdapterItem = new String[j];
                for(i = 0; i < j; i++){
                    name = response.body().getCity().get(i).getName();
                    cityAdapterItem[i] = name;
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RestApi.getData().TimeList().enqueue(new Callback<TimeResponse>() {
            @Override
            public void onResponse(Call<TimeResponse> call, Response<TimeResponse> response) {
                loading.stop();
                j = Integer.valueOf(response.body().getTime().size());
                for(i = 0; i < j; i++){
                    name = response.body().getTime().get(i).getTime();
                    timeAdapterItem[i] = name;
                }
            }

            @Override
            public void onFailure(Call<TimeResponse> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        CityAdapter = new ArrayAdapter<String>(this, R.layout.list_dialog_item,
                R.id.tvItem, cityAdapterItem);

        TimeAdapter = new ArrayAdapter<String>(this, R.layout.list_dialog_item,
                R.id.tvItem, timeAdapterItem);


    }

    @OnClick({R.id.btDepart, R.id.btDestination, R.id.btTime, R.id.btBack, R.id.btNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btDepart:
                showDialog(listView, CityAdapter, 1);
                break;
            case R.id.btDestination:
                showDialog(listView, CityAdapter, 2);
                break;
            case R.id.btTime:
                showDialog(listView, TimeAdapter, 3);
                break;
            case R.id.btBack:

                TrainShow2.this.finish();

                break;
            case R.id.btNext:

                if(depart.isEmpty() && destination.isEmpty() && time.isEmpty()){
                    Toast.makeText(getApplicationContext(), "isi data dengan benar", Toast.LENGTH_SHORT).show();
                } else {

                    String message;
                    message = depart + "\n" + destination + "\n" + time;
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                    map.put("time", time);
                    map.put("destination", destination);
                    map.put("depart", depart);

                    Intent intent = new Intent(getApplicationContext(), SeatPick.class);
                    intent.putExtra("extra", map);
                    startActivity(intent);

                }

                break;
        }
    }

    public void showDialog(ListView mListView, ArrayAdapter<String> arrayAdapter, final int type){

        mListView.setAdapter(arrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ViewGroup viewGroup = (ViewGroup) view;
                TextView tvText = viewGroup.findViewById(R.id.tvItem);
                text = tvText.getText().toString();

                switch (type){
                    case 1:
                        depart = text;
                        tvDepart.setText(text);
                        break;
                    case 2:
                        destination = text;
                        tvDestination.setText(text);
                        break;
                    case 3:
                        time = text;
                        tvTime.setText(text);
                        break;
                }

            }
        });

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());

        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setPositiveButton("ok", null);
        alertDialogBuilder.setView(mListView);

        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
    }
}
