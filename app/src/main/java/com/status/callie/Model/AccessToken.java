package com.status.callie.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.status.callie.Model.Request.TokenRequest;
import com.status.callie.Model.Response.TokenResponse;
import com.status.callie.accounts.AccountConstants;
import com.status.callie.services.ApiClient;
import com.status.callie.services.ApiInterface;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by jivan.ghadage on 8/26/2016.
 */
public class AccessToken {
    public String TAG = "Token Genration";
    String access_token;
    String token_type;
    String expires_in;
    String refresh_token;
    String token_status;

    private SharedPreferences pref;
    SharedPreferences.Editor editor;

    private Context context;

    public AccessToken(Context context) {
        this.context = context;
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
                TokenResponse tokenResponse = response.body();
                access_token = tokenResponse.getAccessToken();
                expires_in = tokenResponse.getExpiresIn();
                refresh_token = tokenResponse.getRefreshToken();
                token_type = tokenResponse.getTokenType();
                sharedPrefSetter(context, access_token, token_type, expires_in, refresh_token);
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e("on failure", t.toString());
            }
        });
        return access_token;
    }

    public String checkToken() {
        return token_status;
    }

    public String getRefreshToken(String old_refresh_token) {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setGrant_type(AccountConstants.GRANT_TYPE_REFRESH_TOKEN);
        tokenRequest.setClient_id(AccountConstants.CLIENT_ID);
        tokenRequest.setClient_secret(AccountConstants.CLIENT_SECRET);
        tokenRequest.setRefresh_token(old_refresh_token);

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
                TokenResponse tokenResponse = response.body();
                access_token = tokenResponse.getAccessToken();
                AccountConstants.aquiredAccessToken = access_token;
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e("on failure", t.toString());
            }
        });
        return refresh_token;
    }


    public String sharedPrefSetter(Context context, String access_token, String token_type, String expires_in, String refresh_token) {
        Log.d(TAG, "sharedPrefSetter: I am not getting called");
        pref = context.getSharedPreferences(AccountConstants.SHARED_PREF_OAUTH2, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString(AccountConstants.ACCESS_TOKEN, access_token);
        editor.putString(AccountConstants.TOKEN_TYPE, token_type);
        editor.putString(AccountConstants.EXPIRES_IN, expires_in);
        editor.putString(AccountConstants.REFRESH_TOKEN, refresh_token);
        editor.commit();
        return null;
    }

}
