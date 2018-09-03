package com.example.robet.rtrain.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robet.rtrain.AdminPage.AdminEdit;
import com.example.robet.rtrain.gson.AdminItem;
import com.example.robet.rtrain.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminShowAdapter extends RecyclerView.Adapter<AdminShowAdapter.MainViewAdapter> {

    public ArrayList<AdminItem> listAdmin = new ArrayList<>();
    int i = 0;

    @NonNull
    @Override
    public MainViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_admin_show, parent, false);
        return new MainViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainViewAdapter holder, final int position) {

        holder.tvId.setText(listAdmin.get(position).getId());
        holder.tvName.setText(listAdmin.get(position).getName());
        holder.tvUsername.setText(listAdmin.get(position).getUsername());

        if(i % 2 == 0){
            holder.backId.setBackgroundResource(R.drawable.button3);
        } else {
            holder.backId.setBackgroundResource(R.drawable.button2);
        }

        i += 1;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    HashMap<String, String> data = new HashMap<>();

                    data.put("id", listAdmin.get(position).getId());
                    data.put("name", listAdmin.get(position).getName());
                    data.put("username", listAdmin.get(position).getUsername());
                    data.put("email", listAdmin.get(position).getEmail());

                    Intent intent = new Intent(holder.itemView.getContext(), AdminEdit.class);
                    intent.putExtra("extra", data);
                    holder.itemView.getContext().startActivity(intent);
                } catch (Exception e){
                    Toast.makeText(holder.itemView.getContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listAdmin.size();
    }

    public class MainViewAdapter extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvUsername;
        TextView tvId;
        LinearLayout backId;
        LinearLayout adminEdit;

        public MainViewAdapter(@NonNull View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvId = (TextView) itemView.findViewById(R.id.tvId);
            backId = (LinearLayout) itemView.findViewById(R.id.backId);
            adminEdit = (LinearLayout) itemView.findViewById(R.id.adminEdit);
        }
    }
}
