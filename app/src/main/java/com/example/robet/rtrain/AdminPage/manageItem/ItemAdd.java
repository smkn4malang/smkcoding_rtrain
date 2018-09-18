package com.example.robet.rtrain.AdminPage.manageItem;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.support.Value;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;
import com.bumptech.glide.Glide;
import java.io.File;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemAdd extends AppCompatActivity {

    @BindView(R.id.tvHeader)
    TextView tvHeader;
    @BindView(R.id.btCamera)
    ImageView btCamera;
    @BindView(R.id.etName)
    TextInputEditText etName;
    @BindView(R.id.etPrice)
    TextInputEditText etPrice;
    @BindView(R.id.etDesc)
    TextInputEditText etDesc;
    @BindView(R.id.btAdd)
    Button btAdd;
    @BindView(R.id.btDelete)
    Button btDelete;

    private ArrayList<Image> imageLib = new ArrayList<>();
    String name, price, desc, imagePath;
    RequestBody Rname, Rprice, Rdesc, Rphoto;
    MultipartBody.Part mPhoto;
    Loading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_add);
        ButterKnife.bind(this);

        btAdd.setText("Add");
        tvHeader.setText("Tambah Barang");
        btDelete.setVisibility(View.GONE);
        loading = new Loading(this);
    }

    @OnClick(R.id.btAdd)
    public void onViewClicked() {
        setResult(253);
        if(etName.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "masukkan nama barang", Toast.LENGTH_SHORT).show();
        } else if (etPrice.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "masukkan harga barang", Toast.LENGTH_SHORT).show();
        } else if (etDesc.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "masukkan deskripsi barang dahulu", Toast.LENGTH_SHORT).show();
        } else if (imageLib.isEmpty()){
            Toast.makeText(getApplicationContext(), "masukkan gambar barang", Toast.LENGTH_SHORT).show();
        } else {

            name = etName.getText().toString();
            price = etPrice.getText().toString();
            desc = etDesc.getText().toString();

            Rname = RequestBody.create(MultipartBody.FORM, name);
            Rprice = RequestBody.create(MultipartBody.FORM, price);
            Rdesc = RequestBody.create(MultipartBody.FORM, desc);

            File file = new File(imagePath);
            Rphoto = RequestBody.create(MediaType.parse("image/*"), file);
            mPhoto = MultipartBody.Part.createFormData("photo", file.getName(), Rphoto);

            loading.start();
            RestApi.getData().itemAdd(mPhoto, Rname, Rprice, Rdesc).enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {
                    loading.stop();
                    Toast.makeText(getApplicationContext(), response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    if(response.body().getInfo()){
                        setResult(253);
                        ItemAdd.this.finish();
                    }
                }

                @Override
                public void onFailure(Call<Value> call, Throwable t) {
                    loading.stop();
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    @OnClick(R.id.btCamera)
    public void onCameraClicked(){
        ImagePicker.with(this)
                .setFolderMode(true)
                .setMaxSize(10)
                .setMultipleMode(false)
                .setCameraOnly(false)
                .setFolderTitle("Albums")
                .setSelectedImages(imageLib)
                .setAlwaysShowDoneButton(true)
                .setKeepScreenOn(true)
                .start();
    }

    @Override
    public void onActivityResult(int requestCode, int ResultCode, Intent intent){
        if(intent != null){
            imageLib = intent.getParcelableArrayListExtra(Config.EXTRA_IMAGES);

            imagePath = imageLib.get(0).getPath();
            Glide.with(ItemAdd.this).load(imagePath).into(btCamera);
        }
    }
}
