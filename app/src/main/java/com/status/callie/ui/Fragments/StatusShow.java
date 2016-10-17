package com.status.callie.ui.Fragments;

import android.content.Context;
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
import com.status.callie.ui.Home;

/**
 * Created by om on 16/10/16.
 */
public class StatusShow extends Fragment implements View.OnClickListener {

    Button setStatusButton;
    Button getSetStatusButton;
    EditText setStatusEditText;
    String textStatus;
    Status status;
    View view;
    private SharedPreferences shared_pref_login;
    Context context;

    public StatusShow() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        context = getActivity();
        status = new Status(context);
        shared_pref_login = context.getSharedPreferences(AccountConstants.SHARED_PREF_LOGIN, Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.status_show, container, false);
        setStatusButton = (Button) view.findViewById(R.id.set_status_button);
        getSetStatusButton = (Button) view.findViewById(R.id.status_get);
        setStatusEditText = (EditText) view.findViewById(R.id.set_status_edit_text);
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
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
