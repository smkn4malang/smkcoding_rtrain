package com.example.robet.rtrain.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.robet.rtrain.AdminPage.ItemEdit;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.gson.ItemItem;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MainViewAdapter> {

    public ArrayList<ItemItem> listItem = new ArrayList<>();
    int i = 0;
    HashMap<String , String> map = new HashMap<>();

    @NonNull
    @Override
    public MainViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_item, parent, false);
        return new MainViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainViewAdapter holder, final int position) {

        holder.name.setText(listItem.get(position).getName());
        holder.price.setText("Rp " + listItem.get(position).getPrice());
        holder.id.setText(listItem.get(position).getId());

        if(i % 2 == 0 ){
            holder.backId.setBackgroundResource(R.drawable.button2);
        } else {
            holder.backId.setBackgroundResource(R.drawable.button1);
        }

        Glide.with(holder.itemView.getContext())
                .load(listItem.get(position).getPic())
                .into(holder.itemPic);

        holder.itemEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                map.put("id", listItem.get(position).getId());
                map.put("name", listItem.get(position).getName());
                map.put("price", listItem.get(position).getPrice());
                map.put("desc", listItem.get(position).getDescription());
                map.put("pic", listItem.get(position).getPic());

                Intent intent = new Intent(holder.itemView.getContext(), ItemEdit.class);
                intent.putExtra("extra", map);
                holder.itemView.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class MainViewAdapter extends RecyclerView.ViewHolder {

        LinearLayout itemEdit, backId;
        TextView name, price, id;
        ImageView itemPic;

        public MainViewAdapter(@NonNull View itemView) {
            super(itemView);

            itemEdit = itemView.findViewById(R.id.itemEdit);
            backId = itemView.findViewById(R.id.backId);
            name = itemView.findViewById(R.id.tvName);
            price = itemView.findViewById(R.id.tvPrice);
            id = itemView.findViewById(R.id.tvId);
            itemPic = itemView.findViewById(R.id.itemPic);

        }
    }
}
