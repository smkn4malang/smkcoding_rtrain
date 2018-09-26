package com.example.robet.rtrain.notification;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.robet.rtrain.ClientPage.Index;
import com.example.robet.rtrain.R;
import com.example.robet.rtrain.support.Config;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class PushNotificationService extends FirebaseMessagingService{

    private Config config;

    @Override
    public void onCreate(){
        super.onCreate();
        config = new Config(PushNotificationService.this);
    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        SendNotification(message.getNotification().getBody());
        for(String key : message.getData().keySet()){
            Toast.makeText(getApplicationContext(), key, Toast.LENGTH_SHORT).show();
        }
    }

    private void SendNotification(String message){
        Intent intent = new Intent(this, Index.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.trains)
                .setContentTitle("update notification")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(sound)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());

    }

}
