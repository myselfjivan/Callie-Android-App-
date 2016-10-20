package com.status.callie.services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.status.callie.model.AccessToken;
import com.status.callie.accounts.AccountConstants;
import com.status.callie.ui.Home;
import com.status.callie.ui.RegisterActivity;

/**
 * Created by jivan.ghadage on 9/28/2016.
 */
public class SessionManager {

    // Context
    Context _context;
    private SharedPreferences shared_pref_otp;
    AccessToken accessToken = new AccessToken(_context);
    String TAG = "Session Manager";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        shared_pref_otp = _context.getSharedPreferences(AccountConstants.SHARED_PREF_OTP, context.MODE_PRIVATE);
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        switch (this.isLoggedIn()) {
            case "true":
                Intent intent = new Intent(_context, Home.class);
                _context.startActivity(intent);
                break;
            default:
                Intent goBackToRegister = new Intent(_context, RegisterActivity.class);
                // Closing all the Activities
                goBackToRegister.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Add new Flag to start new Activity
                goBackToRegister.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // Staring Login Activity
                _context.startActivity(goBackToRegister);
                break;

        }

    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public String isLoggedIn() {
        if (shared_pref_otp.getString(AccountConstants.ACCESS_TOKEN, "") != null) {
            return shared_pref_otp.getString(AccountConstants.IS_LOGGED_IN, "");
        }
        return "false";
    }
}
