package com.example.divyank.dad;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Divyank on 06-08-2016.
 */
public class AlarmReceiver1 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        // For our recurring task, we'll just display a message

        Intent repeating_intent = new Intent(context,MainActivity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,315,repeating_intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(context);
        b.setContentIntent(pendingIntent);
        b.setContentTitle("DAD");
        b.setContentText("Today's events are pending");
        b.setSmallIcon(R.drawable.icon);
        b.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon));
        b.setAutoCancel(true);
        b.setDefaults(Notification.DEFAULT_SOUND);
        b.setDefaults(Notification.DEFAULT_VIBRATE);
        Notification n = b.build();
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(315,n);

    }


}

