package com.status.callie.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.status.callie.Model.Request.LoginRequest;
import com.status.callie.Model.Response.LoginResponse;
import com.status.callie.System.GetMacAddress;
import com.status.callie.accounts.AccountConstants;
import com.status.callie.services.ApiClient;
import com.status.callie.services.ApiError;
import com.status.callie.services.ApiInterface;
import com.status.callie.services.CallieSharedPreferences;
import com.status.callie.services.ErrorUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by jivan.ghadage on 10/3/2016.
 */
public class Status {

    private Context context;

    public String TAG = "Token Genration";
    String access_token;
    String jwt_token;
    String token_type;
    String expires_in;
    String refresh_token;

    private SharedPreferences shared_pref_otp;
    private SharedPreferences shared_pref_token;
    CallieSharedPreferences callieSharedPreferences = new CallieSharedPreferences();

    public Status() {
        this.context = context;
    }


    public String getStatus() {
        shared_pref_otp = context.getSharedPreferences(AccountConstants.SHARED_PREF_OTP, Context.MODE_PRIVATE);
        shared_pref_token = context.getSharedPreferences(AccountConstants.SHARED_PREF_OAUTH2, Context.MODE_PRIVATE);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setAccessToken(shared_pref_token.getString(AccountConstants.ACCESS_TOKEN, ""));
        String country_code = shared_pref_otp.getString(AccountConstants.COUNTRY_CODE, "");
        loginRequest.setMobile(country_code + shared_pref_otp.getString(AccountConstants.MOBILE, ""));
        String otp = shared_pref_otp.getString(AccountConstants.OTP, "");
        loginRequest.setPassword(otp + GetMacAddress.getMacAddr());

        ApiInterface apiService = null;
        try {
            apiService = ApiClient
                    .getClient()
                    .create(ApiInterface.class);
            //apiService.statusStore(shared_pref_otp.getString("",""));
        } catch (IOException e) {
            e.printStackTrace();
        }


        Call<LoginResponse> call = apiService.authLogin(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    jwt_token = loginResponse.getToken();
                    //sharedPrefSetter(context, access_token);
                    callieSharedPreferences.login(context, jwt_token);
                } else {
                    // parse the response body …
                    ApiError error;
                    try {
                        //getToken();
                        error = ErrorUtils.parseError(response);
                        Log.d("error message", error.message());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("on failure", t.toString());
            }
        });
        return null;
    }
}
