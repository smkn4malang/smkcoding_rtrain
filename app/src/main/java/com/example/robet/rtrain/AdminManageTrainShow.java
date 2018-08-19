package com.example.robet.rtrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminManageTrainShow extends AppCompatActivity {

    @BindView(R.id.RecyclerView)
    android.support.v7.widget.RecyclerView RecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_manage_train_show);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        intent = new Intent(getApplicationContext(), AdminManageTrainAdd.class);
        startActivity(intent);
    }
}
