package com.example.robet.rtrain;

import android.content.Context;
import android.graphics.Color;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class Loading {

    ACProgressFlower loading;

    public Loading(Context ctx){
        loading = new ACProgressFlower.Builder(ctx)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Loading...")
                .fadeColor(Color.DKGRAY)
                .build();
    }

    public void start(){
        loading.show();
    }

    public void stop(){
        loading.dismiss();
    }
}
