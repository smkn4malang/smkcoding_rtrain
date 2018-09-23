package com.example.robet.rtrain.notification;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.lang.reflect.Method;

public class pushNotificationToken extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh(){
        String tokens = FirebaseInstanceId.getInstance().getToken();
        Log.d("TOKEN: ", tokens);
    }
}
