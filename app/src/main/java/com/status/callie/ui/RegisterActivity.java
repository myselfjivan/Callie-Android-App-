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
import com.status.callie.Model.AccessToken;
import com.status.callie.Model.Register;
import com.status.callie.Model.Request.PregisterRequest;
import com.status.callie.Model.Response.PregisterResponse;
import com.status.callie.R;
import com.status.callie.accounts.AccountConstants;
import com.status.callie.services.ApiClient;
import com.status.callie.services.ApiError;
import com.status.callie.services.ApiInterface;
import com.status.callie.services.CallieSharedPreferences;
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
    private SharedPreferences shared_pref_oauth2;
    CallieSharedPreferences callieSharedPreferences;
    String status_code;
    AccessToken accessTokenObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregister);
        register = new Register();
        shared_pref_oauth2 = getPreferences(0);
        callieSharedPreferences = new CallieSharedPreferences();
        accessTokenObj = new AccessToken(RegisterActivity.this);


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

    public String verification(Context context, String status_code, String countryCode, String mobile) {
        switch (status_code) {
            case "1":
                callieSharedPreferences.otp(context, "false", "false", mobile, countryCode, "");
                Intent intent = new Intent(RegisterActivity.this, VerifyActivity.class);
                startActivity(intent);
                break;
            case "0":
                Toast.makeText(getApplicationContext(),
                        "Mobile number is incorrect!", Toast.LENGTH_LONG)
                        .show();
            default:
                Toast.makeText(getApplicationContext(),
                        "Something went wrong, try again?", Toast.LENGTH_LONG)
                        .show();
                accessTokenObj.tokenCheck();
                break;

        }
        return null;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public String Pregister(final String accessToken, final String country_code, final String mobile) {
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
                    status_code = pregisterResponse.getStatus_code();
                    verification(RegisterActivity.this, status_code, country_code, mobile);
                } else {
                    // parse the response body …
                    ApiError error;
                    try {
                        error = ErrorUtils.parseError(response);
                        verification(RegisterActivity.this, String.valueOf(error.status()), country_code, mobile);
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
