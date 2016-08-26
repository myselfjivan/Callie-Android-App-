package com.status.callie.Model;

import android.util.Log;

import com.status.callie.Model.Request.PregisterRequest;
import com.status.callie.Model.Response.PregisterResponse;
import com.status.callie.services.ApiClient;
import com.status.callie.services.ApiInterface;

import java.io.IOException;

import retrofit2.Callback;

/**
 * Created by jivan.ghadage on 8/26/2016.
 */
public class Register {
    public String TAG = "Register Request";
    String message, status_code;

    public String Pregister(String accessToken, String country_code, String mobile) {
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
                PregisterResponse pregisterResponse = response.body();
                message = pregisterResponse.getMessage();
                status_code = pregisterResponse.getMessage();
            }

            @Override
            public void onFailure(retrofit2.Call<PregisterResponse> call, Throwable t) {
                Log.e("on failure", t.toString());
            }
        });

        return status_code;
    }
}
