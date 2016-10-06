package com.status.callie.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.status.callie.Model.AccessToken;
import com.status.callie.Model.Status;
import com.status.callie.R;

/**
 * Created by jivan.ghadage on 9/6/2016.
 */
public class Home extends Activity {
    AccessToken accessToken = new AccessToken(Home.this);
    Button setStatusButton;
    EditText setStatusEditText;
    String textStatus;
    Status status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        status = new Status();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setStatusButton = (Button) findViewById(R.id.set_status_button);
        setStatusEditText = (EditText) findViewById(R.id.set_status_edit_text);
        //accessToken.jwtToken();
        setStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textStatus = setStatusEditText.getText().toString();
                status.setStatus(textStatus);
            }
        });
    }

    private Boolean exit = false;

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }

    public String setStatus() {
        return null;
    }
}
