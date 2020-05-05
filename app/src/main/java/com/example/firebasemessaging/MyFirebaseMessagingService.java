package com.example.firebasemessaging;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static com.example.firebasemessaging.App.FCM_CHANNEL_ID;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(remoteMessage.getNotification()!=null){
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            Notification notification = new NotificationCompat
                    .Builder(this,FCM_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_chat_black)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setColor(Color.BLUE)
                    .build();
            NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            manager.notify(1002,notification);
        }
        if(remoteMessage.getData().size()>0){
            System.out.println(remoteMessage.getData().size());
            // Data payload
            String score = remoteMessage.getData().get("score");
            String target = remoteMessage.getData().get("target");
            String overs = remoteMessage.getData().get("overs");

        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }
}
