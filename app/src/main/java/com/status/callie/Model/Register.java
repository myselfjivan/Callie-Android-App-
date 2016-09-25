package com.status.callie.Model;

import android.util.Log;

import com.status.callie.Model.Request.PregisterRequest;
import com.status.callie.Model.Response.PregisterResponse;
import com.status.callie.services.ApiClient;
import com.status.callie.services.ApiError;
import com.status.callie.services.ApiInterface;
import com.status.callie.services.ErrorUtils;
import com.status.callie.ui.RegisterActivity;

import java.io.IOException;

import retrofit2.Callback;

/**
 * Created by jivan.ghadage on 8/26/2016.
 */
public class Register {
    public String TAG = "Register Request";
    String status_code;
    RegisterActivity registerActivity = new RegisterActivity();

    // Gets the data repository in write mode
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
                    //registerActivity.sharedPrefSetter(Callie.this, true, country_code, mobile);
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
