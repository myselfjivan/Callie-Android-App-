package com.status.callie.Model;

import android.content.SharedPreferences;
import android.util.Log;

import com.status.callie.Model.Request.PregisterRequest;
import com.status.callie.Model.Request.PverifyRequest;
import com.status.callie.Model.Response.PregisterResponse;
import com.status.callie.Model.Response.PverifyResponse;
import com.status.callie.services.ApiClient;
import com.status.callie.services.ApiError;
import com.status.callie.services.ApiInterface;
import com.status.callie.services.ErrorUtils;
import com.status.callie.ui.Pregister;
import com.status.callie.ui.Pverify;

import java.io.IOException;

import retrofit2.Callback;

/**
 * Created by jivan.ghadage on 8/31/2016.
 */
public class Verify {
    public String TAG = "Verify Request";
    String status_code;
    Pverify pverify;
    private SharedPreferences pref;

    // Gets the data repository in write mode
    public String Pverify(String accessToken, final String country_code, final String mobile, final String otp, final String mac) {
        pverify = new Pverify();
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
}
