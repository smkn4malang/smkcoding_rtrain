package com.example.robet.rtrain;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class historyAdapter extends RecyclerView.Adapter<historyAdapter.ViewAdapter>{

    ArrayList<HistoryItem> listHistory = new ArrayList<>();

    @NonNull
    @Override
    public historyAdapter.ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_history, parent, false);
        return new ViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull historyAdapter.ViewAdapter holder, int position) {
        holder.historyId.setText(listHistory.get(position).getHistoryId());
        holder.item.setText(listHistory.get(position).getItem());
        holder.qty.setText("qty: " + listHistory.get(position).getQty());
    }

    @Override
    public int getItemCount() {
        return listHistory.size();
    }

    public class ViewAdapter extends RecyclerView.ViewHolder {

//        @BindView(R.id.historyId)
        TextView historyId;
//        @BindView(R.id.item)
        TextView item;
//        @BindView(R.id.qty)
        TextView qty;

        public ViewAdapter(View view) {
            super(view);
            historyId = (TextView) view.findViewById(R.id.historyId);
            item = (TextView) view.findViewById(R.id.item);
            qty = (TextView) view.findViewById(R.id.qty);
        }
    }
}
