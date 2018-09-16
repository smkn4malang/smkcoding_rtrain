package com.example.robet.rtrain.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.robet.rtrain.AdminPage.ManageTrain.AdminManageTrainEdit;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.gson.ManageTrainItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ManageTrainAdapter extends RecyclerView.Adapter<ManageTrainAdapter.MainViewAdapter> {

    public ArrayList<ManageTrainItem> trainList = new ArrayList<>();
    HashMap<String, String> map = new HashMap<>();
    int color;
    Random random = new Random();

    @NonNull
    @Override
    public MainViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_train, parent, false);
        return new MainViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainViewAdapter holder, final int position) {

        color = Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));
        holder.topLayout.setBackgroundColor(color);
        holder.tvName.setText(trainList.get(position).getName());
        holder.tvCategory.setText(trainList.get(position).getName());
        holder.tvCars.setText("Gerbong: " + trainList.get(position).getCars());
        holder.tvPrice.setText("Rp. " + trainList.get(position).getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                map.put("id", trainList.get(position).getId());
                map.put("name", trainList.get(position).getName());
                map.put("cars", trainList.get(position).getCars());
                map.put("price", trainList.get(position).getPrice());

                Intent intent = new Intent(holder.itemView.getContext(), AdminManageTrainEdit.class);
                intent.putExtra("extra", map);
                ((Activity) holder.itemView.getContext()).startActivityForResult(intent, 235);
            }
        });

    }

    @Override
    public int getItemCount() {
        return trainList.size();
    }

    public class MainViewAdapter extends RecyclerView.ViewHolder {

        LinearLayout topLayout;
        TextView tvName, tvCategory, tvCars, tvPrice;

        public MainViewAdapter(@NonNull View itemView) {
            super(itemView);
            topLayout = itemView.findViewById(R.id.TopLayout);
            tvName = itemView.findViewById(R.id.tvName);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvCars = itemView.findViewById(R.id.tvCars);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
