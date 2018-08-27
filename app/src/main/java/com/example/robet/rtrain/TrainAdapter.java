package com.example.robet.rtrain;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.MainViewHolder> {

    ArrayList<TrainItem> listTrain = new ArrayList<>();

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_booking, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listTrain.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
