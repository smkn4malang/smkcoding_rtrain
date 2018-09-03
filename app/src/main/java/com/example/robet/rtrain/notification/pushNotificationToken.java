package com.example.robet.rtrain.notification;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class pushNotificationToken extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh(){
        String RefreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("TOKEN: ", RefreshedToken);
    }

}
