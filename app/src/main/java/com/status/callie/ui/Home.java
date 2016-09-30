package com.status.callie.ui;

import android.app.Activity;
import android.os.Bundle;

import com.status.callie.Model.AccessToken;
import com.status.callie.R;

/**
 * Created by jivan.ghadage on 9/6/2016.
 */
public class Home extends Activity {
    AccessToken accessToken = new AccessToken(Home.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        accessToken.jwtToken();
    }

    @Override
    public void onBackPressed() {

        // your code.
    }
}
