package com.travelsafe.particle.testapp;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        printLog("Service Started");
        /*for (int i = 0 ; i< 1000; i++){
            if (i == 999){
                printLog(String.valueOf(i));
                i = 0;
            }
        }*/
        makeNotification();
        return START_STICKY;
        //return super.onStartCommand(intent, flags, startId);

    }

    public void printLog(String msg){
        Log.d("TEST", msg);
    }
    @Override
    public void onDestroy() {
        printLog("On Destroy (Service) ") ;
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        printLog("Memory Low (TestService)");
        super.onLowMemory();

    }

    private void makeNotification() {

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("Particle Labs")
                .setContentText("Testing Foreground Service")
                .setSmallIcon(R.drawable.ic_insert_emoticon_black_24dp)
                .setContentIntent(pendingIntent)
                .setTicker("Ticket text")
                .build();

        startForeground(11111, notification);

    }

}
