package com.example.robet.rtrain.ClientPage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.support.v7.graphics.Palette;
import com.example.robet.rtrain.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemMore extends AppCompatActivity {

    @BindView(R.id.Collapse)
    CollapsingToolbarLayout Collapse;
    @BindView(R.id.AppBar)
    AppBarLayout AppBar;
    @BindView(R.id.imgHeader)
    ImageView imgHeader;

    int mutedColor = R.attr.colorPrimary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_more);
        ButterKnife.bind(this);

        Collapse.setTitle("nama item");
        Collapse.setExpandedTitleColor(getResources().getColor(R.color.back));

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.train_track);

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@NonNull Palette palette) {
                mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                Collapse.setContentScrimColor(mutedColor);
            }
        });
    }
}
