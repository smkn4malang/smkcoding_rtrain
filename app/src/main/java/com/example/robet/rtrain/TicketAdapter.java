package com.example.robet.rtrain;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MainViewHolder>{

    ArrayList<TrainItem> trainList = new ArrayList<>();
    Random r = new Random();
    int color;

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_booking, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {

//        holder.destination.setText(trainList.get(position).getDestination());
//        holder.depart.setText(trainList.get(position).getDepart());
//        holder.time.setText(trainList.get(position).getTime());
//        holder.date.setText(trainList.get(position).getDate());
//        holder.seat.setText(trainList.get(position).getSeat());
//        holder.booked.setText(trainList.get(position).getBooked());
//
//        color = Color.argb(255, r.nextInt(256), r.nextInt(256), r.nextInt(256));
//        holder.leftLayout.setBackgroundColor(color);

    }

    @Override
    public int getItemCount() {
        return trainList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        LinearLayout leftLayout;
        CardView ShowTicket;
        TextView destination, depart, time, date, seat, booked;

        public MainViewHolder(@NonNull View v) {
            super(v);

            leftLayout = v.findViewById(R.id.LeftLayout);
            ShowTicket = v.findViewById(R.id.ShowTicket);
//            destination = v.findViewById(R.id.tvDestination);
//            depart = v.findViewById(R.id.tvDepart);
//            seat = v.findViewById(R.id.tvSeat);
//            booked = v.findViewById(R.id.tvBooked);

        }
    }
}
