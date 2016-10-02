package com.status.callie.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.status.callie.Model.Request.LoginRequest;
import com.status.callie.Model.Request.TokenRequest;
import com.status.callie.Model.Response.LoginResponse;
import com.status.callie.Model.Response.TokenResponse;
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
 * Created by jivan.ghadage on 8/26/2016.
 */
public class AccessToken {
    public String TAG = "Token Genration";
    String access_token;
    String jwt_token;
    String token_type;
    String expires_in;
    String refresh_token;

    private SharedPreferences shared_pref_otp;
    private SharedPreferences shared_pref_token;
    CallieSharedPreferences callieSharedPreferences = new CallieSharedPreferences();

    private Context context;

    public AccessToken(Context context) {
        this.context = context;
    }

    public String tokenCheck() {
        shared_pref_otp = context.getSharedPreferences(AccountConstants.SHARED_PREF_OAUTH2, Context.MODE_PRIVATE);
        if (shared_pref_otp.getString(AccountConstants.ACCESS_TOKEN, "") != null) {
            //getRefreshToken();
        } else {
            getToken();
        }
        return null;
    }

    public String getToken() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setGrant_type(AccountConstants.GRANT_TYPE_PASSWORD);
        tokenRequest.setClient_id(AccountConstants.CLIENT_ID);
        tokenRequest.setClient_secret(AccountConstants.CLIENT_SECRET);
        tokenRequest.setUsername(AccountConstants.USERNAME);
        tokenRequest.setPassword(AccountConstants.PASSWORD);

        ApiInterface apiService = null;
        try {
            apiService = ApiClient.getClient().create(ApiInterface.class);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Call<TokenResponse> call = apiService.oauthRequest(tokenRequest);
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, retrofit2.Response<TokenResponse> response) {
                if (response.isSuccessful()) {
                    TokenResponse tokenResponse = response.body();
                    access_token = tokenResponse.getAccessToken();
                    expires_in = tokenResponse.getExpiresIn();
                    refresh_token = tokenResponse.getRefreshToken();
                    token_type = tokenResponse.getTokenType();
                    callieSharedPreferences.oauth2(context, access_token, token_type, expires_in, refresh_token);
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
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e("on failure", t.toString());
            }
        });
        return access_token;
    }


    public String getRefreshToken() {
        shared_pref_otp = context.getSharedPreferences(AccountConstants.SHARED_PREF_OAUTH2, Context.MODE_PRIVATE);
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setGrant_type(AccountConstants.GRANT_TYPE_REFRESH_TOKEN);
        tokenRequest.setClient_id(AccountConstants.CLIENT_ID);
        tokenRequest.setClient_secret(AccountConstants.CLIENT_SECRET);
        tokenRequest.setRefresh_token(shared_pref_otp.getString(AccountConstants.REFRESH_TOKEN, ""));

        ApiInterface apiService = null;
        try {
            apiService = ApiClient.getClient().create(ApiInterface.class);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Call<TokenResponse> call = apiService.oauthReRequest(tokenRequest);
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, retrofit2.Response<TokenResponse> response) {
                if (response.isSuccessful()) {
                    TokenResponse tokenResponse = response.body();
                    access_token = tokenResponse.getAccessToken();
                    expires_in = tokenResponse.getExpiresIn();
                    refresh_token = tokenResponse.getRefreshToken();
                    token_type = tokenResponse.getTokenType();
                    callieSharedPreferences.oauth2(context, access_token, token_type, expires_in, refresh_token);
                } else {
                    // parse the response body …
                    ApiError error;
                    try {
                        getToken();
                        error = ErrorUtils.parseError(response);
                        Log.d("error message", error.message());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e("on failure", t.toString());
            }
        });
        return refresh_token;
    }

    public String jwtToken() {
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
            apiService = ApiClient.getClient().create(ApiInterface.class);
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
