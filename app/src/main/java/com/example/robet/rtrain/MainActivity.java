package com.example.robet.rtrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.robet.rtrain.AdminPage.adminLogin;
import com.example.robet.rtrain.ClientPage.guestLogin;
import com.example.robet.rtrain.ClientPage.userLogin;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    Intent newAct;
    int i = 0;
    @BindView(R.id.btAdmin)
    ImageView btAdmin;
    @BindView(R.id.btUser)
    Button btUser;
    @BindView(R.id.btGuest)
    TextView btGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        i = 0;
    }

    @OnClick({R.id.btAdmin, R.id.btUser, R.id.btGuest})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btAdmin:
                if (i >= 10) {
                    i = 0;
                    newAct = new Intent(MainActivity.this, adminLogin.class);
                    startActivity(newAct);
                } else {
                    i += 1;
                }
                break;
            case R.id.btUser:
                i = 0;
                newAct = new Intent(MainActivity.this, userLogin.class);
                startActivity(newAct);
                break;
            case R.id.btGuest:
                i = 0;
                newAct = new Intent(MainActivity.this, guestLogin.class);
                startActivity(newAct);
                break;
        }
    }
}
