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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;
import com.status.callie.Model.Register;
import com.status.callie.Model.Verify;
import com.status.callie.R;
import com.status.callie.System.GetMacAddress;
import com.status.callie.accounts.AccountConstants;

/**
 * Created by jivan.ghadage on 8/11/2016.
 */
public class Pverify extends Fragment implements View.OnClickListener {
    private static final String TAG = Pregister.class.getSimpleName();
    private Button btnVerify;
    private EditText inputOtp;
    private String macAddress;
    Verify pverify = new Verify();
    GetMacAddress getMacAddress = new GetMacAddress();


    public SharedPreferences pref;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        macAddress = getMacAddress.getMacAddr();
    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     */
    private String verifyUser(String otp, String mac) {
        return pverify.Pverify(AccountConstants.aquiredAccessToken, AccountConstants.country_code, AccountConstants.mobile, otp, mac);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pverify, container, false);
        initViews(view);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initViews(View view) {
        btnVerify = (Button) view.findViewById(R.id.btn_verify);
        inputOtp = (EditText) view.findViewById(R.id.otp);
        btnVerify.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        pref = getActivity().getPreferences(0);
        super.onAttach(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_verify:
                String otp = inputOtp.getText().toString().trim();
                if (!otp.isEmpty()) {
                    verifyUser(otp, macAddress);
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

        Fragment profile = new Pverify();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,profile);
        ft.commit();
    }
*/
}
