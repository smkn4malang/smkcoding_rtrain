package com.example.robet.rtrain.AdminPage.manageAdmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.robet.rtrain.AdminPage.manageAdmin.AdminAdd;
import com.example.robet.rtrain.AdminPage.manageUser.AdminManageUser;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.adapter.AdminShowAdapter;
import com.example.robet.rtrain.gson.AdminResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminManageAdmin extends AppCompatActivity {

    Intent intent;
    Loading loading;
    AdminShowAdapter adapter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_manage_admin);
        ButterKnife.bind(this);

        loading = new Loading(this);
        adapter = new AdminShowAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        loading.start();
        RestApi.getData().AdminShow().enqueue(new Callback<AdminResponse>() {
            @Override
            public void onResponse(@NonNull Call<AdminResponse> call, @NonNull Response<AdminResponse> response) {
                loading.stop();
                adapter.listAdmin.addAll(response.body().getAdmin());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<AdminResponse> call, @NonNull Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        intent = new Intent(getApplicationContext(), AdminAdd.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int resultCode, int requestCode, Intent data){
        if(resultCode == 250){
            AdminManageAdmin.this.finish();
            startActivity(getIntent());
        }
    }
}
