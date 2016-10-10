package com.status.callie.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.status.callie.Model.Status;
import com.status.callie.R;
import com.status.callie.accounts.AccountConstants;

/**
 * Created by jivan.ghadage on 9/6/2016.
 */
public class Home extends Activity {
    Button setStatusButton;
    Button getSetStatusButton;
    EditText setStatusEditText;
    String textStatus;
    Status status;
    private SharedPreferences shared_pref_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        status = new Status(Home.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        shared_pref_login = this.getSharedPreferences(AccountConstants.SHARED_PREF_LOGIN, Context.MODE_PRIVATE);
        setStatusButton = (Button) findViewById(R.id.set_status_button);
        getSetStatusButton = (Button) findViewById(R.id.status_get);
        setStatusEditText = (EditText) findViewById(R.id.set_status_edit_text);
        setStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textStatus = setStatusEditText.getText().toString();
                status.statusStore(textStatus, shared_pref_login.getString(AccountConstants.TOKEN, ""));

            }
        });

        getSetStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status.getStatus(shared_pref_login.getString(AccountConstants.TOKEN, ""));
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

}
