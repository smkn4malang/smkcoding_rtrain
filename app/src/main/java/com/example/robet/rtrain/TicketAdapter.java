package com.example.robet.rtrain;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MainViewHolder>{

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

        color = Color.argb(255, r.nextInt(256), r.nextInt(256), r.nextInt(256));
        holder.leftLayout.setBackgroundColor(color);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        LinearLayout leftLayout, ShowTicket;
        TextView destination, depart, time, date, seat, booked;

        public MainViewHolder(@NonNull View v) {
            super(v);

            leftLayout = v.findViewById(R.id.LeftLayout);
            ShowTicket = v.findViewById(R.id.ShowTicket);
            destination = v.findViewById(R.id.tvDestination);
            depart = v.findViewById(R.id.tvDepart);
            seat = v.findViewById(R.id.tvSeat);
            booked = v.findViewById(R.id.tvBooked);

        }
    }
}
