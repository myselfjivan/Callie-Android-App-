package com.status.callie.utils;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by jivan.ghadage on 10/21/2016.
 */
public class LPreviewUtils {
    private LPreviewUtils() {
    }

    public static LPreviewUtilsBase getInstance(AppCompatActivity activity) {
        return new LPreviewUtilsBase(activity);
    }
}
