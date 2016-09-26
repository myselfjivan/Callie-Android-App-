package com.status.callie.services;

import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by jivan.ghadage on 9/26/2016.
 */
public class IntentService extends android.app.IntentService {
    public String TAG = "Intent Service";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public IntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        WakefulBroadcastReceiver.completeWakefulIntent(intent);
        Log.d(TAG, "onHandleIntent: service started on boot");
    }
}
