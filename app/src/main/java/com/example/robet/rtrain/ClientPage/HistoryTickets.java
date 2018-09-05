package com.example.robet.rtrain.ClientPage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.robet.rtrain.R;

public class HistoryTickets extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_tickets);

        Toast.makeText(getApplicationContext(), "History details", Toast.LENGTH_SHORT).show();
    }
}
