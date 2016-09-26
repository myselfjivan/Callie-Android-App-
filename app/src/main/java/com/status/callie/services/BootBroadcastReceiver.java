package com.status.callie.services;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by jivan.ghadage on 9/26/2016.
 */
public class BootBroadcastReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, android.app.IntentService.class);
        startWakefulService(context, startServiceIntent);
    }
}
