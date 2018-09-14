package com.example.robet.rtrain.promo;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class Promo {

    Context context;
    boolean result;

    public Promo(Context context){
        this.context = context;
    }

    public boolean Buy5Get1(int value){

        if(value == 5){
            result = false;
        } else {
            result = true;
        }

        return result;
    }

    public boolean isBuy5Get1(int value){
        if(value == 6){
            return true;
        } else {
            return false;
        }
    }

    public boolean showDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setNegativeButton("abaikan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                result = true;
            }
        });
        builder.setPositiveButton("pilih lagi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                result = false;
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        return result;
    }

}
