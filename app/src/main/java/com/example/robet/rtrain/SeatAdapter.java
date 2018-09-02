package com.example.robet.rtrain;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.ArrayList;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.MainViewHolder> {

    ArrayList<SeatItem> ListSeat = new ArrayList<>();
    int i = 0;
    boolean[] seat;

    public  SeatAdapter(){
        this.seat = new boolean[ListSeat.size()];
        for(i = 0; i < ListSeat.size(); i++){
            seat[i] = false;
        }
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.seat_item, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainViewHolder holder, final int position) {

        if(!ListSeat.get(position).isStatus()){
            holder.seatList.setCardBackgroundColor(Color.RED);
        }

        holder.seatList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ListSeat.get(position).isStatus()){
                    if(!seat[position]){
                        holder.seatList.setCardBackgroundColor(Color.YELLOW);
//                        seat[position] = true;
                    } else {
//                        seat[position] = false;
                        holder.seatList.setCardBackgroundColor(Color.WHITE);
                    }
                } else {
                    Toast.makeText(holder.itemView.getContext(), "tempat tersebut telah di pesan", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return ListSeat.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        CardView seatList;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            seatList = itemView.findViewById(R.id.seatList);
        }
    }
}
