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

import com.example.robet.rtrain.R;
import com.example.robet.rtrain.support.Config;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.support.TicketHistory;
import com.example.robet.rtrain.support.Value;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryTickets extends AppCompatActivity {

    ProgressDialog dialog;
    String id, pid, name;
    Bundle bundle;
    Loading loading;
    Config config;

    @BindView(R.id.tvTrainName)
    TextView tvTrainName;
    @BindView(R.id.tvCategory)
    TextView tvCategory;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvDepart)
    TextInputEditText tvDepart;
    @BindView(R.id.tvDestination)
    TextInputEditText tvDestination;
    @BindView(R.id.tvCart)
    TextInputEditText tvCart;
    @BindView(R.id.tvSeat)
    TextInputEditText tvSeat;
    @BindView(R.id.tvKtp)
    TextInputEditText tvKtp;
    @BindView(R.id.tvPrice)
    TextInputEditText tvPrice;
    @BindView(R.id.btSave)
    Button btSave;
    @BindView(R.id.btDelete)
    Button btDelete;
    @BindView(R.id.container)
    CardView container;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.barCode)
    ImageView barCode;
    @BindView(R.id.footer)
    LinearLayout footer;
    @BindView(R.id.btBack)
    ImageView btBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config = new Config(this);
        setTheme(config.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_tickets);
        ButterKnife.bind(this);

        dialog = new ProgressDialog(HistoryTickets.this);
        bundle = getIntent().getExtras();
        id = (String) bundle.get("id");
        pid = (String) bundle.get("pid");
        name = config.getName();
        loading = new Loading(this);
        barCode.setImageBitmap(config.getBarCode(id));

        getData();

    }

    @OnClick({R.id.btSave, R.id.btDelete, R.id.btBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btSave:
                dialog.setMessage("saving History");
                File file = saveBitmap(HistoryTickets.this, container);
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
            case R.id.btBack:
                HistoryTickets.this.finish();
                break;
        }
    }

    private void getData() {
        loading.start();
        RestApi.getData().ticketHistory(pid).enqueue(new Callback<TicketHistory>() {
            @Override
            public void onResponse(Call<TicketHistory> call, Response<TicketHistory> response) {

                int cars = Integer.valueOf(response.body().getSeat());
                cars = (cars / 20) + 1;

                loading.stop();
                tvTrainName.setText(response.body().getTrainName());
                tvCategory.setText(response.body().getCategory());
                tvDate.setText(response.body().getDate());
                tvSeat.setText(response.body().getSeat());
                tvDestination.setText(response.body().getDestination());
                tvDepart.setText(response.body().getDepart());
                tvTime.setText(response.body().getTime());
                tvCart.setText(String.valueOf(cars));
                tvPrice.setText(response.body().getPrice());
                tvKtp.setText(response.body().getKtp());

            }

            @Override
            public void onFailure(Call<TicketHistory> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), "jaringan bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onDelete() {
        loading.start();
        RestApi.getData().historyDelete(id).enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                loading.stop();
                Toast.makeText(getApplicationContext(), "berhasil hapus data", Toast.LENGTH_SHORT).show();
                setResult(25);
                HistoryTickets.this.finish();
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                loading.stop();
                Toast.makeText(getApplicationContext(), "jaringan bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private File saveBitmap(Context context, View view) {

        String path = "/history/ticket/";
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
