package com.example.robet.rtrain.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.robet.rtrain.AdminPage.manageItem.ItemEdit;
import com.example.robet.rtrain.ClientPage.ItemMore;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.gson.ItemItem;
import com.example.robet.rtrain.support.Config;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.support.Value;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MainViewAdapter> {

    public ArrayList<ItemItem> listItem = new ArrayList<>();
    String id, name, price, pic, desc, mBank, address;
    String mId, mName, mPrice, mPic, userId, Price, mPay;
    int amount = 1;
    int credit, tax = 0;
    Config config;
    boolean bank = false;
    Loading loading;
    HashMap<String, String> map = new HashMap<>();

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
                .apply(RequestOptions.skipMemoryCacheOf(true).diskCacheStrategy(DiskCacheStrategy.NONE))
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

        holder.btMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                map.put("id", listItem.get(position).getId());
                map.put("name", listItem.get(position).getName());
                map.put("price", listItem.get(position).getPrice());
                map.put("pic", listItem.get(position).getPic());
                map.put("desc", listItem.get(position).getDescription());

                Intent intent = new Intent(holder.itemView.getContext(), ItemMore.class);
                intent.putExtra("data", map);
                holder.itemView.getContext().startActivity(intent);
            }
        });

        if (holder.config.getInfo("admin")) {
            holder.btMore.setVisibility(View.GONE);
            holder.btBuy.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(holder.itemPic.getDrawable() != null) {
                        map.put("id", listItem.get(position).getId());
                        map.put("name", listItem.get(position).getName());
                        map.put("price", listItem.get(position).getPrice());
                        map.put("pic", listItem.get(position).getPic());
                        map.put("desc", listItem.get(position).getDescription());
                        Intent intent = new Intent(holder.itemView.getContext(), ItemEdit.class);
                        intent.putExtra("extra", map);
                        ((Activity) holder.itemView.getContext()).startActivityForResult(intent, 253);
                    } else {
                        Toast.makeText(holder.itemView.getContext(), "masih loading", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

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
        Config config;

        public MainViewAdapter(@NonNull View itemView) {
            super(itemView);

            top = itemView.findViewById(R.id.top);
            itemPic = itemView.findViewById(R.id.itemPic);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btBuy = itemView.findViewById(R.id.btBuy);
            btMore = itemView.findViewById(R.id.btMore);
            config = new Config((Activity) itemView.getContext());

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
        builder.setView(view).setCancelable(true);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        if (config.getInfo("user")) {
            user(mCtx, view, dialog);
        } else {
            guest(mCtx, view, dialog);
        }

    }

    private void user(final Context mCtx, View view, final AlertDialog dialog) {

        de.hdodenhof.circleimageview.CircleImageView itemPic;
        final TextView tvName, tvAmount;
        ImageView btPlus, btMin;
        Button btCancel, btBuy;
        final TextInputEditText tvPrice, tvCredit, etAddress;

        etAddress = view.findViewById(R.id.etAddress);
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
                if (amount > 1) {
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
                address = etAddress.getText().toString();

                if (address.equals("")) {
                    Toast.makeText(mCtx, "masukkan alamat anda", Toast.LENGTH_SHORT).show();
                } else if (Integer.valueOf(Price) <= credit) {
                    loading.start();
                    RestApi.getData().itemBuy(userId, mId, String.valueOf(amount), Price, address, config.getEmail(), "1").enqueue(new Callback<Value>() {
                        @Override
                        public void onResponse(Call<Value> call, Response<Value> response) {
                            loading.stop();
                            Toast.makeText(mCtx, "detail pembelian akan dikirim melalui email", Toast.LENGTH_SHORT).show();
                            config.setCredit(config.getCredit() - Integer.valueOf(Price));
                            credit = config.getCredit() - Integer.valueOf(Price);
                            amount = 1;
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

    private void guest(final Context mCtx, View view, final AlertDialog dialog) {

        final String[] pay = {"indomaret", "alfamaret", "bca", "bri", "mandiri"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mCtx, android.R.layout.simple_spinner_dropdown_item, pay);

        de.hdodenhof.circleimageview.CircleImageView itemPic;
        final TextInputLayout layoutPay;
        final TextView tvName, tvAmount;
        final TextInputEditText tvPrice, etAddress, etPay, etRekening;
        ImageView btMin, btPlus;
        Button btCancel, btBuy;
        Spinner spPay;

        etRekening = view.findViewById(R.id.etRekening);
        etAddress = view.findViewById(R.id.etAddress);
        itemPic = view.findViewById(R.id.itemPic);
        tvName = view.findViewById(R.id.tvName);
        tvAmount = view.findViewById(R.id.tvAmount);
        btPlus = view.findViewById(R.id.btPlus);
        btMin = view.findViewById(R.id.btMin);
        btCancel = view.findViewById(R.id.btCancel);
        btBuy = view.findViewById(R.id.btBuy);
        tvPrice = view.findViewById(R.id.tvPrice);
        layoutPay = view.findViewById(R.id.layoutPay);
        etPay = view.findViewById(R.id.etPay);
        spPay = view.findViewById(R.id.spPay);

        Glide.with(mCtx).load(mPic).into(itemPic);
        userId = String.valueOf(config.getId());
        tvName.setText(mName);
        tvAmount.setText(String.valueOf(amount));
        credit = config.getCredit();
        layoutPay.setVisibility(View.GONE);
        spPay.setAdapter(adapter);
        tax = 2500;
        tvPrice.setText(String.valueOf(Integer.valueOf(mPrice) + tax));

        spPay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = adapterView.getSelectedItemPosition();
                switch (index) {
                    case 0:
                        tax = 2500;
                        layoutPay.setVisibility(View.GONE);
                        tvPrice.setText(String.valueOf((Integer.valueOf(mPrice) * amount) + tax));
                        bank = false;
                        break;
                    case 1:
                        tax = 2500;
                        layoutPay.setVisibility(View.GONE);
                        tvPrice.setText(String.valueOf((Integer.valueOf(mPrice) * amount) + tax));
                        bank = false;
                        break;
                    case 2:
                        tax = 5000;
                        layoutPay.setVisibility(View.VISIBLE);
                        tvPrice.setText(String.valueOf((Integer.valueOf(mPrice) * amount) + tax));
                        bank = true;
                        break;
                    case 3:
                        tax = 7500;
                        layoutPay.setVisibility(View.VISIBLE);
                        tvPrice.setText(String.valueOf((Integer.valueOf(mPrice) * amount) + tax));
                        bank = true;
                        break;
                    case 4:
                        tax = 5000;
                        layoutPay.setVisibility(View.VISIBLE);
                        tvPrice.setText(String.valueOf((Integer.valueOf(mPrice) * amount) + tax));
                        bank = true;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount += 1;
                tvAmount.setText(String.valueOf(amount));
                tvPrice.setText(String.valueOf((Integer.valueOf(mPrice) * amount) + tax));
            }
        });

        btMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amount > 1) {
                    amount -= 1;
                    tvAmount.setText(String.valueOf(amount));
                    tvPrice.setText(String.valueOf(Integer.valueOf(mPrice) * amount));
                }
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mBank = etRekening.getText().toString();
                price = tvPrice.getText().toString();
                mPay = etPay.getText().toString();
                address = etAddress.getText().toString();

                if (bank) {
                    if (mBank.equals("")) {
                        bank = false;
                    } else {
                        bank = true;
                    }
                } else {
                    bank = true;
                }

                if (!bank) {
                    Toast.makeText(mCtx, "masukkan nomor rekening", Toast.LENGTH_SHORT).show();
                    bank = false;
                } else if (mPay.equals("")) {
                    Toast.makeText(mCtx, "masukkan uang pembayaran anda", Toast.LENGTH_SHORT).show();
                    bank = false;
                } else if (address.equals("")) {
                    Toast.makeText(mCtx, "masukkan alamat anda", Toast.LENGTH_SHORT).show();
                    bank = false;
                } else if (Integer.valueOf(price) > Integer.valueOf(mPay)) {
                    Toast.makeText(mCtx, "uang anda kurang", Toast.LENGTH_SHORT).show();
                    bank = false;
                } else {

                    bank = false;
                    loading.start();
                    RestApi.getData().itemBuy(userId, mId, String.valueOf(amount), price, address, config.getEmail(), "2").enqueue(new Callback<Value>() {
                        @Override
                        public void onResponse(Call<Value> call, Response<Value> response) {
                            loading.stop();
                            Toast.makeText(mCtx, "detail pembelian akan dikirim melalui email", Toast.LENGTH_SHORT).show();
                            amount = 1;
                            dialog.cancel();
                        }

                        @Override
                        public void onFailure(Call<Value> call, Throwable t) {
                            loading.stop();
                            Toast.makeText(mCtx, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

}
