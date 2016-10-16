package com.status.callie.ui.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.status.callie.Model.Status;
import com.status.callie.R;
import com.status.callie.accounts.AccountConstants;

/**
 * Created by om on 16/10/16.
 */
public class StatusShow extends Fragment {

    Button setStatusButton;
    Button getSetStatusButton;
    EditText setStatusEditText;
    String textStatus;
    Status status;
    private SharedPreferences shared_pref_login;

    public StatusShow() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.status_show, container, false);
    }
}
