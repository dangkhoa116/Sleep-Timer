package com.example.sleeptimer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.practiceui2.R;


public class sleepTimerService extends Service {
    public static  final String CHANNEL_ID = "ForegroundServiceChannel";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public int onStartCommand(Intent intent,int flags,int startId)
    {
        /*Intent extendIntent = new Intent(this,MainActivity.class);
        extendIntent.putExtra("Extend","extendButton");
        Intent stopIntent = new Intent(this,MainActivity.class);
        stopIntent.putExtra("Stop","endButton");*/

        String minLeft = intent.getStringExtra("minLeft");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,notificationIntent,0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Sleep Timer")
                .setContentText(minLeft)
                .setSmallIcon(R.raw.ic_launcherweb)
                .setContentIntent(pendingIntent)
                .setOnlyAlertOnce(true)
                .addAction(R.raw.extendicon,"Extend",pendingIntent)
                .addAction(R.raw.clearicon,"Stop",pendingIntent)
                .build();
        startForeground(1, notification);

        return START_NOT_STICKY;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChanel = new NotificationChannel(
                    CHANNEL_ID,
                    "Sleep Timer",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChanel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Service Stopped",Toast.LENGTH_SHORT).show();
    }
}
