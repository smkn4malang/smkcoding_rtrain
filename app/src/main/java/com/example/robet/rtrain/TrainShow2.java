package com.example.robet.rtrain;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import java.util.List;

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
    int i = 0;
    List<CityItem> listCity;
    List<TimeItem> listTime;
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
            public void onResponse(@NonNull Call<CityResponse> call, @NonNull Response<CityResponse> response) {
                listCity = response.body().getCity();
                int size = Integer.valueOf(listCity.size());
                cityAdapterItem = new String[size];

                for(i = 0; i < size; i++){
                    cityAdapterItem[i] = String.valueOf(listCity.get(i).getName());
                }
                CityAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.list_dialog_item,
                        cityAdapterItem);

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
                listTime = response.body().getTime();
                int size = Integer.valueOf(listTime.size());
                timeAdapterItem = new String[size];

                for(i = 0; i < size; i++){
                    timeAdapterItem[i] = String.valueOf(listTime.get(i).getTime());
                }

                TimeAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.list_dialog_item,
                        timeAdapterItem);
            }

            @Override
            public void onFailure(Call<TimeResponse> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick({R.id.btDepart, R.id.btDestination, R.id.btTime, R.id.btBack, R.id.btNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btDepart:
//                showDialog(listView, CityAdapter, 1);
                 AlertDialog.Builder s = new AlertDialog.Builder(this);
                 s.setCancelable(false);
                 s.setTitle("quiz");
                 s.setMessage("benar?");
                 s.setPositiveButton("y", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                         Toast.makeText(getApplicationContext(), "y", Toast.LENGTH_SHORT).show();
                     }
                 })
                 .setNegativeButton("n", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                         Toast.makeText(getApplicationContext(), "n", Toast.LENGTH_SHORT).show();
                     }
                 });
                 s.create().show();
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

                if(tvDepart.getText().toString().isEmpty() &&
                        tvDestination.getText().toString().isEmpty() &&
                        tvTime.getText().toString().isEmpty()){
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
//        alertDialogBuilder.setView(mListView);

        AlertDialog dialog = alertDialogBuilder.create();
//        dialog.show();
    }
}