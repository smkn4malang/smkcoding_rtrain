package com.example.robet.rtrain.AdminPage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.robet.rtrain.AdminPage.ManageTrain.AdminManageTrainShow;
import com.example.robet.rtrain.AdminPage.manageAdmin.AdminManageAdmin;
import com.example.robet.rtrain.AdminPage.manageItem.ItemShow;
import com.example.robet.rtrain.AdminPage.manageUser.AdminManageUser;
import com.example.robet.rtrain.MainActivity;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.support.Config;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndexAdmin extends AppCompatActivity {

    Config config;
    @BindView(R.id.btLogout)
    ImageView btLogout;
    @BindView(R.id.btManageUsers)
    CardView btManageUsers;
    @BindView(R.id.btManageData)
    CardView btManageData;
    @BindView(R.id.btManageAdmins)
    CardView btManageAdmins;
    @BindView(R.id.btManageCities)
    CardView btManageCities;
    @BindView(R.id.btManageTime)
    CardView btManageTime;
    @BindView(R.id.Settings)
    CardView Settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config = new Config(this);
        setTheme(R.style.CatChilds);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_admin);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btManageUsers, R.id.btManageAdmins, R.id.btManageData})
    public void onRow1Clicked(View view){
        switch (view.getId()){
            case R.id.btManageUsers:
                startActivity(new Intent(getApplicationContext(), AdminManageUser.class));
                break;
            case R.id.btManageData:
                showDialog();
                break;
            case R.id.btManageAdmins:
                if(config.getUsername().equals("root")){
                    startActivity(new Intent(getApplicationContext(), AdminManageAdmin.class));
                } else {
                    Toast.makeText(getApplicationContext(), "hanya root admin yang dapat masuk", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @OnClick({R.id.btManageCities, R.id.btManageTime, R.id.Settings})
    public void onRow2Clicked(View view){
        switch (view.getId()){
            case R.id.btManageCities:
                startActivity(new Intent(getApplicationContext(), ManageCity.class));
                break;
            case R.id.btManageTime:
                startActivity(new Intent(getApplicationContext(), ManageTime.class));
                break;
            case R.id.Settings:
                startActivity(new Intent(getApplicationContext(), AdminSetting.class));
                break;
        }
    }

    @OnClick(R.id.btLogout)
    public void onLogoutClicked(View view){
        config.setInfo("admin", false);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showDialog(){

        LayoutInflater inflater = LayoutInflater.from(IndexAdmin.this);
        View view = inflater.inflate(R.layout.admin_manage_data, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(IndexAdmin.this);
        builder.setView(view);
        builder.setCancelable(true);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        CardView btTrain, btItem;
        Button btCancel;

        btTrain = view.findViewById(R.id.btTrain);
        btItem = view.findViewById(R.id.btItem);
        btCancel = view.findViewById(R.id.btCancel);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ItemShow.class));
                dialog.cancel();
            }
        });

        btTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminManageTrainShow.class));
                dialog.cancel();
            }
        });
    }
}
