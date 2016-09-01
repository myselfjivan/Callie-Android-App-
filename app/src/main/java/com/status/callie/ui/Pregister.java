package com.status.callie.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;
import com.status.callie.Callie;
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
    private SharedPreferences pref;

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
        AccountConstants.country_code = countryCode;
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initViews(View view) {
        pref = getActivity().getPreferences(0);
        btnRegister = (Button) view.findViewById(R.id.btn_register);
        inputMobileEditText = (EditText) view.findViewById(R.id.mobile);
        ccp = (CountryCodePicker) view.findViewById(R.id.ccp);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        pref = this.getActivity().getSharedPreferences("com.status.callie", Context.MODE_PRIVATE);
        super.onAttach(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_register:
                String mobile = inputMobileEditText.getText().toString().trim();
                AccountConstants.mobile = mobile;

                if (!mobile.isEmpty()) {
                    registerUser(mobile);
                    goToOtpVerification();
                } else {
                    Snackbar.make(getView(), "Please enter mobile number!", Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

    public String sharedPrefSetter(Boolean status, String countryCode, String mobile) {
        if (status == true) {
            Log.d(TAG, "sharedPrefSetter: I am not getting called");
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(AccountConstants.IS_LOGGED_IN, status);
            editor.putString("country_code", countryCode);
            editor.putString("mobile", mobile);
            editor.commit();
            goToOtpVerification();
        }
        return null;
    }

    public void goToOtpVerification() {
        Fragment verify = new Pverify();
        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, verify);
        ft.commit();
    }

}
