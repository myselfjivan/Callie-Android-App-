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

import com.status.callie.Model.Request.PverifyRequest;
import com.status.callie.Model.Response.PverifyResponse;
import com.status.callie.R;
import com.status.callie.System.GetMacAddress;
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
public class VerifyActivity extends Activity {

    private static final String TAG = VerifyActivity.class.getSimpleName();
    private Button btnVerify;
    private EditText inputOtp;
    private String macAddress;
    GetMacAddress getMacAddress;
    private SharedPreferences shared_pref_otp;
    private SharedPreferences shared_pref_oauth2;
    SharedPreferences.Editor editor;
    String status_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pverify);
        inputOtp = (EditText) findViewById(R.id.otp);
        btnVerify = (Button) findViewById(R.id.btn_verify);
        getMacAddress = new GetMacAddress();
        shared_pref_otp = getPreferences(0);
        shared_pref_oauth2 = getPreferences(0);
        shared_pref_otp = this.getSharedPreferences(AccountConstants.SHARED_PREF_OTP, Context.MODE_PRIVATE);
        shared_pref_oauth2 = this.getSharedPreferences(AccountConstants.SHARED_PREF_OAUTH2, Context.MODE_PRIVATE);

        macAddress = getMacAddress.getMacAddr();

        // PregisterRequest Button Click event
        btnVerify.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String otp = inputOtp.getText().toString().trim();
                if (!otp.isEmpty()) {
                    verifyUser(VerifyActivity.this, otp, macAddress);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter otp!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }


    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     */
    private String verifyUser(Context context, String otp, String mac) {
        shared_pref_oauth2 = context.getSharedPreferences(AccountConstants.SHARED_PREF_OAUTH2, Context.MODE_PRIVATE);
        return Pverify(shared_pref_oauth2.getString(AccountConstants.ACCESS_TOKEN, ""), shared_pref_otp.getString(AccountConstants.COUNTRY_CODE, ""), shared_pref_otp.getString(AccountConstants.MOBILE, ""), otp, mac);

    }

    // Gets the data repository in write mode
    public String Pverify(String accessToken, final String country_code, final String mobile, final String otp, final String mac) {
        PverifyRequest pverifyRequest = new PverifyRequest();
        pverifyRequest.setAccess_token(accessToken);
        pverifyRequest.setCountry_code(country_code);
        pverifyRequest.setMobile(mobile);
        pverifyRequest.setOtp(otp);
        pverifyRequest.setMac(mac);

        ApiInterface apiService = null;
        try {
            apiService = ApiClient.getClient().create(ApiInterface.class);
        } catch (IOException e) {
            e.printStackTrace();
        }


        retrofit2.Call<PverifyResponse> call = apiService.authPverify(pverifyRequest);
        call.enqueue(new Callback<PverifyResponse>() {
            @Override
            public void onResponse(retrofit2.Call<PverifyResponse> call, retrofit2.Response<PverifyResponse> response) {
                if (response.isSuccessful()) {
                    PverifyResponse pverifyResponse = response.body();
                    Log.d(TAG, "onResponse: " + pverifyResponse.getMessage());
                    verified(pverifyResponse.getStatus_code(), otp);
                } else {
                    // parse the response body …
                    ApiError error;
                    try {
                        //pregister.sharedPrefSetter(false, country_code, mobile);
                        error = ErrorUtils.parseError(response);
                        Log.d("error message", error.message());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(retrofit2.Call<PverifyResponse> call, Throwable t) {
                Log.e("on failure", t.toString());
            }
        });

        return status_code;
    }

    public String verified(String statusCode, String otp) {
        switch (statusCode) {
            case "1":
                sharedPrefSetter(VerifyActivity.this, otp);
                Intent intent = new Intent(VerifyActivity.this, Home.class);
                startActivity(intent);
                break;
            case "0":
                Toast.makeText(getApplicationContext(),
                        "Wrong otp!", Toast.LENGTH_LONG)
                        .show();
                break;
            default:
                Toast.makeText(getApplicationContext(),
                        "Something Went Wrong!", Toast.LENGTH_LONG)
                        .show();
                break;

        }
        return null;
    }

    public String sharedPrefSetter(Context context, String otp) {
        Log.d(TAG, "sharedPrefSetter: I am not getting called");
        shared_pref_otp = context.getSharedPreferences(AccountConstants.SHARED_PREF_OTP, Context.MODE_PRIVATE);
        editor = shared_pref_otp.edit();
        editor.putString(AccountConstants.IS_VERIFIED, "true");
        editor.putString(AccountConstants.IS_LOGGED_IN, "true");
        editor.putString(AccountConstants.OTP, otp);
        editor.commit();
        return null;
    }

}
