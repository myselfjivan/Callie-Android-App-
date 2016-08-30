package com.status.callie.ui;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;
import com.status.callie.Model.Register;
import com.status.callie.R;
import com.status.callie.accounts.AccountConstants;

/**
 * Created by jivan.ghadage on 8/11/2016.
 */
public class Pregister extends Fragment implements View.OnClickListener {
    private static final String TAG = Pregister.class.getSimpleName();
    private Button btnRegister;
    private EditText inputMobileEditText;
    private String countryCode;
    Register register = new Register();
    CountryCodePicker ccp;

    public SharedPreferences pref;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     */
    private String registerUser(String mobile) {
        return register.Pregister(AccountConstants.aquiredAccessToken, countryCode, mobile);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pregister, container, false);
        initViews(view);
        countryCode = ccp.getSelectedCountryCodeWithPlus();
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initViews(View view) {
        btnRegister = (Button) view.findViewById(R.id.btn_register);
        inputMobileEditText = (EditText) view.findViewById(R.id.mobile);
        ccp = (CountryCodePicker) view.findViewById(R.id.ccp);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        pref = getActivity().getPreferences(0);
        super.onAttach(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_register:
                String mobile = inputMobileEditText.getText().toString().trim();

                if (!mobile.isEmpty()) {
                    registerUser(mobile);
                } else {
                    Snackbar.make(getView(), "Please enter mobile number!", Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public Boolean sharedPrefSetter(Boolean status) {
        if (status == true) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(AccountConstants.IS_LOGGED_IN, status);
            editor.apply();
            editor.commit();
        }
        return null;
    }
/*
    private void goToProfile(){

        Fragment profile = new ProfileFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,profile);
        ft.commit();
    }
*/
}
