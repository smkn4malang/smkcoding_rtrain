package com.example.robet.rtrain.ClientPage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.robet.rtrain.R;

import java.util.HashMap;

public class PurchaseTicket extends AppCompatActivity {

    Bundle bundle;
    HashMap<String, String> map;
    boolean status = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_ticket);

        bundle = getIntent().getExtras();
        map = (HashMap<String, String>) bundle.get("extra");

        Toast.makeText(getApplicationContext(), "purchase ticket", Toast.LENGTH_SHORT).show();
    }
}
