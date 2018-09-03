package com.example.robet.rtrain.AdminPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.robet.rtrain.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminManageData extends AppCompatActivity {

    @BindView(R.id.ManageTrain)
    LinearLayout ManageTrain;
    @BindView(R.id.ManageItem)
    LinearLayout ManageItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_manage_data);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ManageTrain, R.id.ManageItem})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ManageTrain:
                startActivity(new Intent(getApplicationContext(), AdminManageTrainShow.class));
                break;
            case R.id.ManageItem:
                startActivity(new Intent(getApplicationContext(), ItemShow.class));
                break;
        }
    }
}
