package com.status.callie.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.status.callie.Model.Register;
import com.status.callie.Model.Request.PregisterRequest;
import com.status.callie.Model.Response.PregisterResponse;
import com.status.callie.R;
import com.status.callie.accounts.AccountConstants;
import com.status.callie.services.ApiClient;
import com.status.callie.services.ApiError;
import com.status.callie.services.ApiInterface;
import com.status.callie.services.ErrorUtils;

import java.io.IOException;

import retrofit2.Callback;

/**
 * Created by jivan.ghadage on 9/1/2016.
 */
public class RegisterActivity extends Activity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnRegister;
    private EditText inputMobileEditText;
    private String countryCode;
    Register register;
    CountryCodePicker ccp;
    private SharedPreferences shared_pref_otp;
    private SharedPreferences shared_pref_oauth2;
    SharedPreferences.Editor editor;
    String status_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregister);
        register = new Register();
        shared_pref_otp = getPreferences(0);
        shared_pref_oauth2 = getPreferences(0);


        btnRegister = (Button) findViewById(R.id.btn_register);
        inputMobileEditText = (EditText) findViewById(R.id.mobile);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        countryCode = ccp.getSelectedCountryCodeWithPlus();

        // PregisterRequest Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String mobile = inputMobileEditText.getText().toString().trim();

                if (!mobile.isEmpty()) {
                    registerUser(RegisterActivity.this, mobile);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter mobile number!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     */
    private String registerUser(Context context, String mobile) {
        shared_pref_oauth2 = context.getSharedPreferences(AccountConstants.SHARED_PREF_OAUTH2, Context.MODE_PRIVATE);
        return Pregister(shared_pref_oauth2.getString(AccountConstants.ACCESS_TOKEN, ""), countryCode, mobile);

    }

    public String sharedPrefSetter(Context context, Boolean status, String countryCode, String mobile) {
        if (status == true) {
            Log.d(TAG, "sharedPrefSetter: I am not getting called");
            shared_pref_otp = context.getSharedPreferences(AccountConstants.SHARED_PREF_OTP, Context.MODE_PRIVATE);
            editor = shared_pref_otp.edit();
            editor.putString(AccountConstants.IS_LOGGED_IN, "true");
            editor.putString(AccountConstants.COUNTRY_CODE, countryCode);
            editor.putString(AccountConstants.MOBILE, mobile);
            editor.commit();
            goToOtpVerification();
        }
        return null;
    }

    public void goToOtpVerification() {
        Intent intent = new Intent(RegisterActivity.this, VerifyActivity.class);
        startActivity(intent);
    }

    public String Pregister(String accessToken, final String country_code, final String mobile) {
        PregisterRequest pregisterRequest = new PregisterRequest();
        pregisterRequest.setAccessToken(accessToken);
        pregisterRequest.setCountry_code(country_code);
        pregisterRequest.setMobile(mobile);

        ApiInterface apiService = null;
        try {
            apiService = ApiClient.getClient().create(ApiInterface.class);
        } catch (IOException e) {
            e.printStackTrace();
        }


        retrofit2.Call<PregisterResponse> call = apiService.authPregister(pregisterRequest);
        call.enqueue(new Callback<PregisterResponse>() {
            @Override
            public void onResponse(retrofit2.Call<PregisterResponse> call, retrofit2.Response<PregisterResponse> response) {
                if (response.isSuccessful()) {
                    PregisterResponse pregisterResponse = response.body();
                    Log.d(TAG, "onResponse: " + pregisterResponse.getMessage());
                    sharedPrefSetter(RegisterActivity.this, true, country_code, mobile);
                } else {
                    // parse the response body …
                    ApiError error;
                    try {
                        error = ErrorUtils.parseError(response);
                        Log.d("error message", error.message());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(retrofit2.Call<PregisterResponse> call, Throwable t) {
                Log.e("on failure", t.toString());
            }
        });

        return status_code;
    }

}
