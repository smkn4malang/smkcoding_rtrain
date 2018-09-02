package com.example.robet.rtrain;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManageTrainAdapter extends RecyclerView.Adapter<ManageTrainAdapter.MainViewAdapter> {

    ArrayList<ManageTrainItem> trainList = new ArrayList<>();
    int i = 0;
    HashMap<String, String> map = new HashMap<>();

    @NonNull
    @Override
    public MainViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_train, parent, false);
        return new MainViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainViewAdapter holder, final int position) {



    }

    @Override
    public int getItemCount() {
        return trainList.size();
    }

    public class MainViewAdapter extends RecyclerView.ViewHolder {

        public MainViewAdapter(@NonNull View itemView) {
            super(itemView);
        }
    }
}
