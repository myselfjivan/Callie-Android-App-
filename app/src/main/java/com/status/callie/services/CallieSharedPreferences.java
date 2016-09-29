package com.status.callie.services;

import android.content.Context;
import android.util.Log;

import com.status.callie.accounts.AccountConstants;

/**
 * Created by jivan.ghadage on 9/29/2016.
 */
public class CallieSharedPreferences {
    String TAG = "Shared Preference Manager";
    private android.content.SharedPreferences shared_pref_otp;
    android.content.SharedPreferences.Editor editor;

    public String otp(Context context, String otp) {
        Log.d(TAG, "sharedPrefSetter: I am not getting called");
        shared_pref_otp = context.getSharedPreferences(AccountConstants.SHARED_PREF_OTP, Context.MODE_PRIVATE);
        editor = shared_pref_otp.edit();
        editor.putString(AccountConstants.IS_VERIFIED, "true");
        editor.putString(AccountConstants.IS_LOGGED_IN, "true");
        editor.putString(AccountConstants.OTP, otp);
        editor.commit();
        return null;
    }

    public String oauth2(Context context, String access_token, String token_type, String expires_in, String refresh_token) {
        Log.d(TAG, "sharedPrefSetter: I am not getting called");
        shared_pref_otp = context.getSharedPreferences(AccountConstants.SHARED_PREF_OAUTH2, Context.MODE_PRIVATE);
        editor = shared_pref_otp.edit();
        editor.putString(AccountConstants.ACCESS_TOKEN, access_token);
        editor.putString(AccountConstants.TOKEN_TYPE, token_type);
        editor.putString(AccountConstants.EXPIRES_IN, expires_in);
        editor.putString(AccountConstants.REFRESH_TOKEN, refresh_token);
        editor.commit();
        return null;
    }

    public String login(Context context, String token) {
        shared_pref_otp = context.getSharedPreferences(AccountConstants.SHARED_PREF_LOGIN, Context.MODE_PRIVATE);
        editor = shared_pref_otp.edit();
        editor.putString(AccountConstants.TOKEN, token);
        editor.commit();
        return null;
    }

}
