package com.example.owner.takeandgouser.controller;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.Nullable;

import com.example.owner.takeandgouser.R;
import com.example.owner.takeandgouser.model.backEnd.DBManagerFactory;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Notification.Builder nBuilder = new Notification.Builder(getBaseContext());
       // nBuilder.setSmallIcon(R.drawable.ic_menu_share); //TODO to change to onther picture
        nBuilder.setSmallIcon(R.mipmap.ic_sport_car);
        nBuilder.setContentTitle("service");
        nBuilder.setContentText("service is running...");
        Notification notification = nBuilder.build();
        startForeground(1234, notification);
    }


    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) { //TODO check it
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

         while (true) {
             try {
                 Thread.sleep(1000);

                 Intent myIntent = new Intent();
                 myIntent.setAction("CHANGE_CAR_STATUS");
                if (DBManagerFactory.getManager().checkOrder())
                 {
                     myIntent.putExtra("STATUS_CHANGED","status_changed");
                     sendBroadcast(myIntent);
                 }
             } catch (Exception e) {
                 Thread.currentThread().interrupt();
             }
         }
    }

}
