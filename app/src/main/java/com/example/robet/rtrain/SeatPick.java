package com.example.robet.rtrain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.HashMap;

public class SeatPick extends AppCompatActivity {

    Bundle bundle;
    HashMap<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_pick);

        bundle = getIntent().getExtras();
        map = (HashMap<String, String>) bundle.get("extra");
        Toast.makeText(getApplicationContext(), "berhasil", Toast.LENGTH_SHORT).show();
    }
}
