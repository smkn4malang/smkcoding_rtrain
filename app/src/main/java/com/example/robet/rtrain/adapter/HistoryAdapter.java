package com.example.robet.rtrain.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.robet.rtrain.ClientPage.HistoryItems;
import com.example.robet.rtrain.ClientPage.HistoryTickets;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.gson.HistoryItem;
import java.util.ArrayList;
import java.util.Random;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MainViewHolder> {

    public ArrayList<HistoryItem> listHistory = new ArrayList<>();
    Random random = new Random();
    int color;

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_history, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainViewHolder holder, final int position) {

        color = Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));
        holder.topLayout.setBackgroundColor(color);

        holder.purchaseId.setText("id " + listHistory.get(position).getPurchaseId());
        holder.type.setText(listHistory.get(position).getType());
        holder.date.setText(listHistory.get(position).getDate());

        holder.ShowHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                if(listHistory.get(position).getType().equals("ticket")){
                    intent = new Intent(holder.itemView.getContext(), HistoryTickets.class);
                } else {
                    intent = new Intent(holder.itemView.getContext(), HistoryItems.class);
                }

                intent.putExtra("id", listHistory.get(position).getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listHistory.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        TextView purchaseId, type, date;
        CardView ShowHistory;
        LinearLayout topLayout;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            purchaseId = itemView.findViewById(R.id.tvPurchaseId);
            type = itemView.findViewById(R.id.tvType);
            date = itemView.findViewById(R.id.tvDate);
            ShowHistory = itemView.findViewById(R.id.ShowHistory);
            topLayout = itemView.findViewById(R.id.TopLayout);
        }
    }
}
