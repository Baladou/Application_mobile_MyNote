package com.example.application_note;


import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import androidx.core.app.NotificationCompat;


import com.example.application_note.Activities.MainActivity;
import com.example.application_note.Activities.notifActivity2;
import com.example.application_note.Helper.DBHelper;

import java.util.Random;

public class AlertReceiver extends BroadcastReceiver {
    Random r = new Random();
    private DBHelper mydb ;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();

        mydb = new DBHelper(context);

        int id_n = intent.getExtras().getInt("ID2");

        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("ID3", id_n);
        Log.v("notifactiv", String.valueOf(id_n));

// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);

        Intent i = new Intent (context, notifActivity2.class);
        i.putExtras(extras);
        stackBuilder.addNextIntent(i);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(r.nextInt(1000),PendingIntent.FLAG_UPDATE_CURRENT);
        nb.setContentTitle(mydb.getNote(id_n).getName());
        nb.setContentText(mydb.getNote(id_n).getText());
        nb.setContentIntent(resultPendingIntent);

        notificationHelper.getManager().notify(NotificationID.getID(), nb.build());
        Log.v("getID", String.valueOf(NotificationID.getID()));

        Vibrator v= (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        v.vibrate(1000);
    }

}
