package com.example.owner.takeandgouser.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        if (intent.hasExtra("STSTUS_CHANGED"))
            Toast.makeText(context,"new car is available now..",Toast.LENGTH_LONG).show();
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
