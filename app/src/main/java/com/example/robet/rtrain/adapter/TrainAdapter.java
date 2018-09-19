package com.example.robet.rtrain.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.robet.rtrain.ClientPage.SeatPick;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.gson.CartResponse;
import com.example.robet.rtrain.gson.TrainItem;
import com.example.robet.rtrain.support.Config;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.support.Value;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.MainViewHolder> {

    public ArrayList<TrainItem> listTrain = new ArrayList<>();
    private HashMap<String, String> map = new HashMap<>();
    private Random random = new Random();
    int color;
    boolean stat;

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_train, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainViewHolder holder, final int position) {

        if (listTrain.get(position).getSeat().equals(listTrain.get(position).getBooked())) {
            holder.showTicket.setCardBackgroundColor(Color.rgb(255, 106, 106));
        }

        color = Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));
        holder.topLayout.setBackgroundColor(color);

        holder.name.setText(listTrain.get(position).getName());
        holder.category.setText(listTrain.get(position).getCategory());
        holder.price.setText("Rp " + listTrain.get(position).getPrice());
        holder.seat.setText("seat: " + listTrain.get(position).getSeat());

        holder.showTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                map.put("trainId", listTrain.get(position).getId());
                map.put("trainName", listTrain.get(position).getName());
                map.put("category", listTrain.get(position).getCategory());
                map.put("price", listTrain.get(position).getPrice());
                map.put("date", listTrain.get(position).getDate());
                map.put("seat", listTrain.get(position).getSeat());

                pickDetails(holder.itemView.getContext(), map.get("trainId"));

            }
        });

    }

    @Override
    public int getItemCount() {
        return listTrain.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        LinearLayout topLayout;
        CardView showTicket;
        TextView name, category, price, seat;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvName);
            category = itemView.findViewById(R.id.tvCategory);
            price = itemView.findViewById(R.id.tvPrice);
            seat = itemView.findViewById(R.id.tvCars);
            topLayout = itemView.findViewById(R.id.TopLayout);
            showTicket = itemView.findViewById(R.id.ShowTicket);

        }
    }

    private void pickDetails(final Context mCtx, final String mId) {

        final Config config = new Config(mCtx);
        final String[] city, time, cart;
        ArrayAdapter<String> adapter;
        final Loading loading = new Loading(mCtx);
        final AutoCompleteTextView etDepart, etDestination;
        Button btBack, btNext;
        final Spinner spTime, spCart;

        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View view = layoutInflater.inflate(R.layout.train_show_2, null);

        etDepart = view.findViewById(R.id.etFrom);
        etDestination = view.findViewById(R.id.etTo);
        spCart = view.findViewById(R.id.spCart);
        spTime = view.findViewById(R.id.spTime);
        btBack = view.findViewById(R.id.btBack);
        btNext = view.findViewById(R.id.btNext);

        getData(mCtx, mId);

        city = config.getCity();
        time = config.getTime();
        cart = config.getCart();

        adapter =new ArrayAdapter<String>(mCtx, android.R.layout.simple_spinner_dropdown_item, time);
        spTime.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(mCtx, android.R.layout.simple_list_item_1, city);
        etDepart.setAdapter(adapter);
        etDestination.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(mCtx, android.R.layout.simple_spinner_dropdown_item, cart);
        spCart.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        builder.setCancelable(true);
        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String mTime, mDestination, mDepart, mCart, mTrainId, mDate, mCategory;
                boolean departStat = true;
                boolean destinationStat = true;

                mTrainId = map.get("trainId");
                mDate = map.get("date");
                mCategory = map.get("category");
                mTime = spTime.getSelectedItem().toString();
                mDepart = etDepart.getText().toString();
                mDestination = etDestination.getText().toString();
                mCart = spCart.getSelectedItem().toString();

                if (mDepart.equals("") && mDestination.equals("")) {
                    Toast.makeText(mCtx, "isi data dengan benar", Toast.LENGTH_SHORT).show();
                } else {

                    for (int i = 0; i < city.length; i++) {

                        if (mDepart.equals(String.valueOf(city[i]))) {
                            departStat = true;
                        }

                        if (mDestination.equals(String.valueOf(city[i]))) {
                            destinationStat = true;
                        }
                    }

                    if (!departStat) {
                        etDepart.setError("pilih dengan benar");
                    } else if (!destinationStat) {
                        etDestination.setError("pilih dengan benar");
                    } else if (mDepart.equals(mDestination)) {
                        etDestination.setError("pilih tujuan yang berbeda");
                    } else {
                        String[] data = {mTrainId, mDate, mTime, mCategory, mDestination, mDepart, mCart};
                        getStatus(mCtx, data, mId, dialog);
                        if(!config.getStatus()){
                            Toast.makeText(mCtx, "gerbong telah penuh", Toast.LENGTH_SHORT).show();
                        } else {
                            map.put("time", mTime);
                            map.put("depart", mDepart);
                            map.put("destination", mDestination);
                            map.put("cart", mCart);

                            dialog.cancel();
                            Intent intent = new Intent(mCtx, SeatPick.class);
                            intent.putExtra("extra", map);
                            view.getContext().startActivity(intent);
                        }
                    }
                }
            }
        });
    }

    private void getStatus(final Context mCtx, final String[] mData, final String mId, final AlertDialog dialog) {

        final Loading loading = new Loading(mCtx);
        final Config config = new Config(mCtx);

        loading.start();
        RestApi.getData().trainStatus(mData[0], mData[1], mData[2], mData[3], mData[4], mData[5], mData[6]).enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                stat = true;
                loading.stop();
                config.setStatus(response.body().getInfo());
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                loading.stop();
                Toast.makeText(mCtx, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData(final Context context, final String id){
        final Loading loading = new Loading(context);
        final Config config = new Config(context);

        loading.start();
        RestApi.getData().cartShow(id).enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                loading.stop();
                int size = response.body().getCart().size();
                String[] data = new String[size];
                for (int i = 0; i < size; i++) {
                    data[i] = "Gerbong " + String.valueOf(response.body().getCart().get(i).getNum());
                }
                config.setCart(data);
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                loading.stop();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
