package com.example.robet.rtrain.AdminPage;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.robet.rtrain.R;
import com.example.robet.rtrain.gson.TimeResponse;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.support.Value;

import java.util.Calendar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageTime extends AppCompatActivity {

    @BindView(R.id.listView)
    android.widget.ListView listView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    Loading loading;
    String[] time;
    Calendar calendar;
    TimePickerDialog timePickerDialog;
    String old, data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_time);
        ButterKnife.bind(this);

        calendar = Calendar.getInstance();
        loading = new Loading(this);
        getData();
    }

    private void getData() {

        loading.start();

        RestApi.getData().TimeList().enqueue(new Callback<TimeResponse>() {
            @Override
            public void onResponse(Call<TimeResponse> call, Response<TimeResponse> response) {
                loading.stop();
                int size = response.body().getTime().size();
                time = new String[size];
                for (int i = 0; i < size; i++) {
                    time[i] = response.body().getTime().get(i).getTime();
                }
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ManageTime.this, android.R.layout.simple_list_item_1, time);
                listView.setAdapter(adapter);
                registerForContextMenu(listView);
            }

            @Override
            public void onFailure(Call<TimeResponse> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo info){
        super.onCreateContextMenu(menu, view, info);
        menu.setHeaderTitle("Select Action");
        menu.add(0, view.getId(), 0, "edit");
        menu.add(0, view.getId(), 0, "delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle().toString().equals("edit")){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int i = info.position;
            old = time[i];
            timePickerDialog = addDialog(2);
            timePickerDialog.show();
        } else if(item.getTitle().toString().equals("delete")){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int i = info.position;
            old = time[i];
            deleteData(old);
        }
        return true;
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        timePickerDialog = addDialog(1);
        timePickerDialog.show();
        addData(data);
    }

    private TimePickerDialog addDialog(final int value){
        return new TimePickerDialog(ManageTime.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                data = String.valueOf(i) + ":" + String.valueOf(i1) + ":00";
                switch (value){
                    case 1:
                        addData(data);
                        break;
                    case 2:
                        editData(old, data);
                        break;
                }
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
    }

    private void addData(final String data){

        loading.start();
        RestApi.getData().addTime(data).enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                loading.stop();
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                if(response.body().getInfo()){
                    timePickerDialog.cancel();
                    ManageTime.this.finish();
                    startActivity(getIntent());
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void editData(final String old, final String data) {

        loading.start();
        RestApi.getData().editTime(old, data).enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                loading.stop();
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                if(response.body().getInfo()){
                    timePickerDialog.cancel();
                    ManageTime.this.finish();
                    startActivity(getIntent());
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void deleteData(final String data){

        loading.start();
        RestApi.getData().deleteTime(data).enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                loading.stop();
                Toast.makeText(getApplicationContext(), "berhasil hapus data", Toast.LENGTH_SHORT).show();
                ManageTime.this.finish();
                startActivity(getIntent());
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
