package com.example.robet.rtrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndexAdmin extends AppCompatActivity {

    Intent newAct;
    Config config;

    @BindView(R.id.ManageUser)
    LinearLayout ManageUser;
    @BindView(R.id.ManageData)
    LinearLayout ManageData;
    @BindView(R.id.Setting)
    LinearLayout Setting;
    @BindView(R.id.ManageAdmin)
    LinearLayout ManageAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_admin);
        ButterKnife.bind(this);

        config = new Config(this);
    }

    @OnClick({R.id.ManageUser, R.id.ManageData, R.id.Setting, R.id.ManageAdmin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ManageUser:
                newAct = new Intent(getApplicationContext(), AdminManageUser.class);
                startActivity(newAct);
                break;
            case R.id.ManageData:
                newAct = new Intent(getApplicationContext(), AdminManageData.class);
                startActivity(newAct);
                break;
            case R.id.Setting:
                newAct = new Intent(getApplicationContext(), AdminSetting.class);
                startActivity(newAct);
                break;
            case R.id.ManageAdmin:

                if(!config.getUsername().equals("root")){
                    Toast.makeText(getApplicationContext(), "hanya admin ROOT yang dapat mengakses halaman ini!", Toast.LENGTH_SHORT).show();
                } else {
                    newAct = new Intent(getApplicationContext(), AdminManageAdmin.class);
                    startActivity(newAct);
                }

                break;
        }
    }
}
