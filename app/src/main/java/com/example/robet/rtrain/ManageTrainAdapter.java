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

    List<ManageTrainItem> trainList = new ArrayList<>();
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

        holder.tvId.setText(trainList.get(position).getId());
        holder.tvName.setText(trainList.get(position).getName());
        holder.tvDepart.setText("asal   : \n" + trainList.get(position).getDepart());
        holder.tvDestination.setText("tujuan : \n" +trainList.get(position).getDestination());
        holder.tvCategory.setText(trainList.get(position).getCategory());

        if(i % 2 == 0){
            holder.backId.setBackgroundResource(R.drawable.button3);
        } else {
            holder.backId.setBackgroundResource(R.drawable.button2);
        }

        i += 1;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.put("id", trainList.get(position).getId());
                map.put("name", trainList.get(position).getName());
                map.put("category", trainList.get(position).getCategory());
                map.put("depart", trainList.get(position).getDepart());
                map.put("destination", trainList.get(position).getDestination());
                map.put("cars", trainList.get(position).getCars());
                map.put("price", trainList.get(position).getPrice());
                map.put("time", trainList.get(position).getTime());

                Intent intent = new Intent(holder.itemView.getContext(), AdminManageTrainEdit.class);
                intent.putExtra("extra", map);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return trainList.size();
    }

    public class MainViewAdapter extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvCategory;
        TextView tvDepart;
        TextView tvDestination;
        TextView tvId;
        LinearLayout backId;
        LinearLayout trainEdit;

        public MainViewAdapter(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDepart = itemView.findViewById(R.id.tvDepart);
            tvDestination = itemView.findViewById(R.id.tvDestination);
            tvId = itemView.findViewById(R.id.tvId);
            backId = itemView.findViewById(R.id.backId);
            trainEdit = itemView.findViewById(R.id.trainEdit);

        }
    }
}
