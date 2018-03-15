package com.example.owner.takeandgouser.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast t = null;
        //if new car is available show toast message
        if (intent.hasExtra("STATUS_CHANGED")) {
            t = Toast.makeText(context, "new car is available now..", Toast.LENGTH_LONG);
            t.show();
        }
        //if not show nothing
        else
            t.cancel();
    }
}

