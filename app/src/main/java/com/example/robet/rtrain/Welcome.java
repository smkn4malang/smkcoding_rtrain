package com.example.robet.rtrain;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robet.rtrain.ClientPage.Index;
import com.example.robet.rtrain.AdminPage.IndexAdmin;
import com.example.robet.rtrain.support.Config;

import java.io.File;

public class Welcome extends AppCompatActivity {

    private ViewPager VPager;
    private MyViewPagerAdapter MvpAdapter = new MyViewPagerAdapter();
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button introSkip, introNext;
    private Config pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //buat object intro
        pref = new Config(this);

        if (!pref.IsFlaunch()){
            launchHomeScreen();
        }

        if (Build.VERSION.SDK_INT >= 21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_welcome);

        //mengambil nilai dari xml
        VPager = findViewById(R.id.VPager);
        dotsLayout = findViewById(R.id.dotsLayout);
        introSkip = findViewById(R.id.introSkip);
        introNext = findViewById(R.id.introNext);

        //mendeklarasikan array layout slider
        layouts = new int[]{
                R.layout.slide1,
                R.layout.slide2,
                R.layout.slide3
        };

        addBotDots(0);
        changeStatBarCol();

        VPager.setAdapter(MvpAdapter);
        VPager.addOnPageChangeListener(VpListener);

        introSkip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                launchHomeScreen();
            }
        });

        introNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int current = getItem(+1);
                if(current < layouts.length){
                    VPager.setCurrentItem(current);
                }else{
                    launchHomeScreen();
                }
            }
        });
    }

    private void makeFiles(){
        String[] path = {
                "/R-Train/history/item/",
                "/R-Train/history/ticket/",
        };
        for(int i = 0; i < path.length; i++){
            File dir = new File(Environment.getExternalStorageDirectory().getPath() + path[i]);
            if(!dir.exists()){
                if(!dir.mkdir()){
                    Toast.makeText(getApplicationContext(), "cant make external directory files", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void addBotDots(int currPage){
        dots = new TextView[layouts.length];

        int[] ColAc = getResources().getIntArray(R.array.acdots_array);
        int[] ColIn = getResources().getIntArray(R.array.indots_array);

        dotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(ColIn[currPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0){
            dots[currPage].setTextColor(ColAc[currPage]);
        }
    }

    private int getItem(int i){
        return VPager.getCurrentItem() + i;
    }

    private void launchHomeScreen(){
        pref.setFlaunch(false);

        if(pref.getInfo("admin")){
            Intent intent = new Intent(getApplicationContext(), IndexAdmin.class);
            startActivity(intent);
        } else if(pref.getInfo("user") || pref.getInfo("guest")) {
            Intent intent = new Intent(getApplicationContext(), Index.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        finish();
    }

    ViewPager.OnPageChangeListener VpListener = new ViewPager.OnPageChangeListener(){
        @Override
        public void onPageSelected(int position) {
            addBotDots(position);


            if (position == layouts.length - 1) {
                
                introNext.setText(getString(R.string.start));
                introSkip.setVisibility(View.GONE);
            } else {

                introNext.setText(getString(R.string.next));
                introSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2){}

        @Override
        public void onPageScrollStateChanged(int arg0){}
    };

    private void changeStatBarCol(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater InflAy;

        @Override
        public Object instantiateItem(ViewGroup container, int pos){
            InflAy = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = InflAy.inflate(layouts[pos], container, false);
            container.addView(v);

            return v;
        }

        @Override
        public int getCount(){
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View v, Object o){
            return v == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int pos, Object o){
            View v = (View) o;
            container.removeView(v);
        }
    }
}