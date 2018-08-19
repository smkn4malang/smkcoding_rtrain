package com.example.robet.rtrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminManageData extends AppCompatActivity {

    @BindView(R.id.ManageTrain)
    LinearLayout ManageTrain;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_manage_data);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ManageTrain)
    public void onViewClicked() {
        intent = new Intent(getApplicationContext(), AdminManageTrainShow.class);
        startActivity(intent);
    }
}
