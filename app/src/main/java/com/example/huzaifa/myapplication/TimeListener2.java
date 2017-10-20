package com.example.huzaifa.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by huzaifa on 03-Mar-17.
 */

public class TimeListener2 extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {

            Log.d("Time listener 2","HERE");
            Intent i = new Intent(context, homepage.class);
            PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), i, 0);

            // Build notification
            // Actions are just fake
            Notification noti = new Notification.Builder(context)
                    .setContentTitle("Reminder to drink water")
                    .setContentText("Dont forget to add the amount").setSmallIcon(R.drawable.glass)
                    .setContentIntent(pIntent)
                    .build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            // hide the notification after its selected
            noti.flags |= Notification.FLAG_AUTO_CANCEL;

            notificationManager.notify(0, noti);

        }

    }


