package com.example.robet.rtrain.AdminPage.manageUser;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.robet.rtrain.AdminPage.manageAdmin.AdminManageAdmin;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.adapter.UserShowAdapter;
import com.example.robet.rtrain.gson.UserShowResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminManageUser extends AppCompatActivity {

    Intent newAct;
    UserShowAdapter adapter;
    Loading loading;
    @BindView(R.id.RecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_manage_user);
        ButterKnife.bind(this);

        loading = new Loading(this);
        adapter = new UserShowAdapter();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

        loading.start();
        RestApi.getData().UserShow().enqueue(new Callback<UserShowResponse>() {
            @Override
            public void onResponse(Call<UserShowResponse> call, Response<UserShowResponse> response) {
                loading.stop();
                adapter.listUser.addAll(response.body().getUser());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<UserShowResponse> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        newAct = new Intent(getApplicationContext(), UserAdd.class);
        startActivity(newAct);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(resultCode == 250){
            AdminManageUser.this.finish();
            startActivity(getIntent());
        }
    }
}
