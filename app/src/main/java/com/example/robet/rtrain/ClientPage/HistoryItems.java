package com.example.robet.rtrain.ClientPage;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.support.Config;
import com.example.robet.rtrain.support.ItemHistory;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.support.Value;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryItems extends AppCompatActivity {

    Bundle bundle;
    Loading loading;
    Config config;
    ProgressDialog dialog;
    String itemName, desc, Qty, pic, price, date, address, id, pid, name;

    @BindView(R.id.tvItemName)
    TextView tvItemName;
    @BindView(R.id.itemPic)
    CircleImageView itemPic;
    @BindView(R.id.tvQty)
    TextInputEditText tvQty;
    @BindView(R.id.tvPrice)
    TextInputEditText tvPrice;
    @BindView(R.id.tvAddress)
    TextInputEditText tvAddress;
    @BindView(R.id.tvDesc)
    TextInputEditText tvDesc;
    @BindView(R.id.btSave)
    Button btSave;
    @BindView(R.id.btDelete)
    Button btDelete;
    @BindView(R.id.footer)
    LinearLayout footer;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.container)
    CardView container;
    @BindView(R.id.barCode)
    ImageView barCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config = new Config(this);
        setTheme(config.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_items);
        ButterKnife.bind(this);

        dialog = new ProgressDialog(HistoryItems.this);
        bundle = getIntent().getExtras();
        id = (String) bundle.get("id");
        pid = (String) bundle.get("pid");
        loading = new Loading(this);
        name = config.getName();
        barCode.setImageBitmap(config.getBarCode(id));

        getData();

    }

    @OnClick({R.id.btSave, R.id.btDelete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btSave:
                dialog.setMessage("saving History");
                File file = saveBitmap(HistoryItems.this, container);
                if (file != null) {
                    dialog.cancel();
                    Toast.makeText(getApplicationContext(), "berhasil menyimpan history", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.cancel();
                    Toast.makeText(getApplicationContext(), "gagal menyimpan history", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btDelete:

                onDelete();

                break;
        }
    }

    private void onDelete() {
        loading.start();
        RestApi.getData().historyDelete(id).enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                loading.stop();
                Toast.makeText(getApplicationContext(), "berhasil hapus data", Toast.LENGTH_SHORT).show();
                setResult(25);
                HistoryItems.this.finish();
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), "jaringan bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData() {
        loading.start();
        RestApi.getData().historyItem(pid).enqueue(new Callback<ItemHistory>() {
            @Override
            public void onResponse(Call<ItemHistory> call, Response<ItemHistory> response) {

                loading.stop();

                itemName = response.body().getItemName();
                desc = response.body().getDesc();
                Qty = response.body().getQty();
                pic = response.body().getPic();
                price = response.body().getPrice();
                date = response.body().getDate();
                address = response.body().getAddress();

                Glide.with(getApplicationContext()).load(pic).into(itemPic);

                tvItemName.setText(itemName);
                tvDesc.setText(desc);
                tvQty.setText(Qty);
                tvPrice.setText("Rp. " + price);
                tvDate.setText(date);
                tvAddress.setText(address);

            }

            @Override
            public void onFailure(Call<ItemHistory> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), "jaringan bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private File saveBitmap(Context context, View view) {

        String path = "/history/item/";
        File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/R-Train" + path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = dir.getPath() + "/" + name + "-tickets" + id + ".png";
        File file = new File(fileName);
        Bitmap bitmap = converter(view);
        try {

            file.createNewFile();
            FileOutputStream OStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, OStream);
            OStream.flush();
            OStream.close();

        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        scanGalery(context, file.getAbsolutePath());
        return file;
    }

    private Bitmap converter(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable drawable = view.getBackground();
        if (drawable != null) {
            drawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return bitmap;
    }

    private void scanGalery(Context context, String path) {
        try {

            MediaScannerConnection.scanFile(context, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                @Override
                public void onScanCompleted(String s, Uri uri) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
