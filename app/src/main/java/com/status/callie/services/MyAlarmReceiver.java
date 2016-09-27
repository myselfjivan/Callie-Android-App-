package com.status.callie.services;

import android.app.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.status.callie.Callie;
import com.status.callie.Model.AccessToken;

/**
 * Created by jivan.ghadage on 9/27/2016.
 */
public class MyAlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.status.Callie.alarm";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, android.app.IntentService.class);
        i.putExtra("foo", "bar");
        context.startService(i);
    }
}
