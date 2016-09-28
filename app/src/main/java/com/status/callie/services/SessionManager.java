package com.status.callie.services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.status.callie.accounts.AccountConstants;
import com.status.callie.ui.RegisterActivity;

/**
 * Created by jivan.ghadage on 9/28/2016.
 */
public class SessionManager {

    // Context
    Context _context;
    private SharedPreferences shared_pref_otp;
    SharedPreferences.Editor editor;

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        shared_pref_otp = _context.getSharedPreferences(AccountConstants.SHARED_PREF_OTP, context.MODE_PRIVATE);
        editor = shared_pref_otp.edit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (this.isLoggedIn() != "true") {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, RegisterActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public String isLoggedIn() {
        return shared_pref_otp.getString(AccountConstants.IS_LOGGED_IN, "");
    }
}
