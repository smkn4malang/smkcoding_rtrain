package com.example.robet.rtrain.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.gson.ItemItem;
import com.example.robet.rtrain.support.Config;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.support.Value;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MainViewAdapter> {

    public ArrayList<ItemItem> listItem = new ArrayList<>();
    String id, name, price, pic, desc;
    String mId, mName, mPrice, mPic, userId, Price;
    int amount = 1;
    int credit;
    Config config;
    Loading loading;

    @NonNull
    @Override
    public MainViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_item, parent, false);
        return new MainViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainViewAdapter holder, final int position) {

        id = listItem.get(position).getId();
        name = listItem.get(position).getName();
        price = listItem.get(position).getPrice();
        pic = listItem.get(position).getPic();
        desc = listItem.get(position).getDescription();

        Glide.with(holder.itemView.getContext())
                .load(pic)
                .into(holder.itemPic);

        holder.tvName.setText(name);
        holder.tvPrice.setText(price);
        holder.btBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mId = listItem.get(position).getId();
                mName = listItem.get(position).getName();
                mPrice = listItem.get(position).getPrice();
                mPic = listItem.get(position).getPic();

                showDialog(holder.itemView.getContext());
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class MainViewAdapter extends RecyclerView.ViewHolder {

        LinearLayout top;
        de.hdodenhof.circleimageview.CircleImageView itemPic;
        TextView tvName, tvPrice, btMore;
        Button btBuy;

        public MainViewAdapter(@NonNull View itemView) {
            super(itemView);

            top = itemView.findViewById(R.id.top);
            itemPic = itemView.findViewById(R.id.itemPic);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btBuy = itemView.findViewById(R.id.btBuy);

        }
    }

    private void showDialog(Context mCtx) {

        View view = null;
        loading = new Loading(mCtx);
        config = new Config(mCtx);
        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);

        if (config.getInfo("user")) {
            view = layoutInflater.inflate(R.layout.item_buy_user, null);
        } else {
            view = layoutInflater.inflate(R.layout.item_buy_guest, null);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        builder.setView(view).setCancelable(false);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        if(config.getInfo("user")){
            user(mCtx, view, dialog);
        }

    }

    private void user(final Context mCtx, View view, final AlertDialog dialog){

        de.hdodenhof.circleimageview.CircleImageView itemPic;
        final TextView tvName, tvAmount;
        ImageView btPlus, btMin;
        Button btCancel, btBuy;
        final TextInputEditText tvPrice, tvCredit;

        itemPic = view.findViewById(R.id.itemPic);
        tvName = view.findViewById(R.id.tvName);
        tvAmount = view.findViewById(R.id.tvAmount);
        btPlus = view.findViewById(R.id.btPlus);
        btMin = view.findViewById(R.id.btMin);
        btCancel = view.findViewById(R.id.btCancel);
        btBuy = view.findViewById(R.id.btBuy);
        tvPrice = view.findViewById(R.id.tvPrice);
        tvCredit = view.findViewById(R.id.tvCredit);

        Glide.with(mCtx).load(mPic).into(itemPic);
        tvName.setText(mName);
        tvAmount.setText(String.valueOf(amount));
        credit = config.getCredit();
        tvCredit.setText(String.valueOf(credit));
        tvPrice.setText(mPrice);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount += 1;
                tvAmount.setText(String.valueOf(amount));
                tvPrice.setText(String.valueOf(Integer.valueOf(mPrice) * amount));
            }
        });

        btMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount > 1){
                    amount -= 1;
                    tvAmount.setText(String.valueOf(amount));
                    tvPrice.setText(String.valueOf(Integer.valueOf(mPrice) * amount));
                }
            }
        });

        btBuy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                userId = String.valueOf(config.getId());
                Price = tvPrice.getText().toString();

                if(Integer.valueOf(Price) <= credit) {
                    loading.start();
                    RestApi.getData().itemBuy(userId, mId, String.valueOf(amount), Price).enqueue(new Callback<Value>() {
                        @Override
                        public void onResponse(Call<Value> call, Response<Value> response) {
                            loading.stop();
                            Toast.makeText(mCtx, "detail pembelian akan dikirim melalui email", Toast.LENGTH_SHORT).show();
                            config.setCredit(config.getCredit() - Integer.valueOf(Price));
                            credit = config.getCredit() - Integer.valueOf(Price);
                            dialog.cancel();
                        }

                        @Override
                        public void onFailure(Call<Value> call, Throwable t) {
                            loading.stop();
                            Toast.makeText(mCtx, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(mCtx, "uang anda kurang", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
