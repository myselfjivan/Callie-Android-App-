package com.status.callie.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.status.callie.Model.Request.PverifyRequest;
import com.status.callie.Model.Response.PverifyResponse;
import com.status.callie.Model.Verify;
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

    private static final String TAG = Pregister.class.getSimpleName();
    private Button btnVerify;
    private EditText inputOtp;
    private String macAddress;
    GetMacAddress getMacAddress;
    private SharedPreferences pref;
    SharedPreferences.Editor editor;
    String status_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pverify);
        inputOtp = (EditText) findViewById(R.id.otp);
        btnVerify = (Button) findViewById(R.id.btn_verify);
        getMacAddress = new GetMacAddress();
        pref = getPreferences(0);
        pref = this.getSharedPreferences("com.callie.status", Context.MODE_PRIVATE);

        macAddress = getMacAddress.getMacAddr();

        // PregisterRequest Button Click event
        btnVerify.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String otp = inputOtp.getText().toString().trim();
                if (!otp.isEmpty()) {
                    verifyUser(otp, macAddress);
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
    private String verifyUser(String otp, String mac) {
        return Pverify(AccountConstants.aquiredAccessToken, pref.getString("country_code", ""), pref.getString("mobile", ""), otp, mac);

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
                    pverifyResponse.setMessage(pverifyResponse.getMessage());
                    pverifyResponse.setStatus_code(pverifyResponse.getStatus_code());
                    //pregister.sharedPrefSetter(true);
                    sharedPrefSetter(VerifyActivity.this);
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

    public String sharedPrefSetter(Context context) {
        Log.d(TAG, "sharedPrefSetter: I am not getting called");
        pref = context.getSharedPreferences("com.callie.status", Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putBoolean(AccountConstants.IS_VERIFIED, true);
        editor.commit();
        return null;
    }

}
