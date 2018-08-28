package com.example.robet.rtrain;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.MainViewHolder> {

    ArrayList<TrainItem> listTrain = new ArrayList<>();
    private HashMap<String, String> map = new HashMap<>();
    private Random random = new Random();
    int color;

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_train, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainViewHolder holder, final int position) {

        color = Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));
        holder.leftLayout.setBackgroundColor(color);

        holder.name.setText(listTrain.get(position).getName());
        holder.category.setText(listTrain.get(position).getCategory());
        holder.price.setText("Rp " + listTrain.get(position).getPrice());
        holder.seat.setText("seat: " + listTrain.get(position).getSeat());
        holder.time.setText("  " + listTrain.get(position).getTime());
        holder.date.setText("  " + listTrain.get(position).getDate());
        holder.booked.setText("booked: " + listTrain.get(position).getBooked());

        holder.showTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                map.put("trainId", listTrain.get(position).getId());
                map.put("trainName", listTrain.get(position).getName());
                map.put("category", listTrain.get(position).getCategory());
                map.put("price", listTrain.get(position).getPrice());
                map.put("time", listTrain.get(position).getTime());
                map.put("date", listTrain.get(position).getDate());
                map.put("seat", listTrain.get(position).getSeat());

                Intent intent = new Intent(holder.itemView.getContext(), CityList.class);
                intent.putExtra("extra", map);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listTrain.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        LinearLayout leftLayout;
        CardView showTicket;
        TextView name, category, price, seat, time, date, booked;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvName);
            category = itemView.findViewById(R.id.tvCategory);
            price = itemView.findViewById(R.id.tvPrice);
            seat = itemView.findViewById(R.id.tvSeat);
            time = itemView.findViewById(R.id.tvTime);
            date = itemView.findViewById(R.id.tvDate);
            booked = itemView.findViewById(R.id.tvBooked);
            leftLayout = itemView.findViewById(R.id.LeftLayout);
            showTicket = itemView.findViewById(R.id.ShowTicket);

        }
    }
}
