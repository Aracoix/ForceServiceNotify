package com.aracoix.forceservicenotify;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class GuardService extends Service {

    private String  notificationId = "guardChannelId";
    private String  notificationName = "guardChannelName";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            //创建NotificationChannel
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(
                        notificationId,
                        notificationName,
                        NotificationManager.IMPORTANCE_HIGH
                );
                notificationManager.createNotificationChannel(channel);
            }
            startForeground(1, getNotification());
        }

    }

    private Notification getNotification(){
        Intent intent = new  Intent(this, MainActivity.class);
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("this is a title")
                .setContentText("this is a sub text")
                .setContentIntent(
                        PendingIntent.getActivity(
                                this,
                                -1,
                                intent,
                                PendingIntent.FLAG_CANCEL_CURRENT
                        )
                );
        //设置Notification的ChannelID,否则不能正常显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(notificationId);
        }
        return builder.build();
    }
}
