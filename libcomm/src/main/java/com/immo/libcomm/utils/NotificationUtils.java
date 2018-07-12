package com.immo.libcomm.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

/**
 * @author Administrator
 * @content
 * @date 2018/4/12
 */
public class NotificationUtils extends ContextWrapper {
    private NotificationManager manager;
    public static final String id = "channel_1";
    public static final String name = "channel_name_1";
    public static final String CHANNEL_ID = "my_channel_01";


    public NotificationUtils(Context context){
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel(Context context){
        String channelId = "subscribe";
        String channelName = "订阅消息";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }
    private NotificationManager getManager(){
        if (manager == null){
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification(String title, String content,PendingIntent mainPendingIntent){
        return new Notification.Builder(getApplicationContext(), id)
                .setChannelId(CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setContentIntent(mainPendingIntent)
                .setAutoCancel(true);
    }
    public NotificationCompat.Builder getNotification_25(String title, String content,PendingIntent mainPendingIntent){
        return new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setContentIntent(mainPendingIntent)
                .setAutoCancel(true);
    }
    public void sendNotification(Context context,String title, String content,PendingIntent mainPendingIntent){
            Notification notification = getNotification_25(title, content,mainPendingIntent).build();
            getManager().notify(1,notification);
}
}
