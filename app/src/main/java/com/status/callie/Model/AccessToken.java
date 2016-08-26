package com.status.callie.Model;

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
    String accessTokne;

    public String getToken() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setGrant_type(AccountConstants.GRANT_TYPE);
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
                accessTokne = tokenResponse.getAccessToken();
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e("on failure", t.toString());
            }
        });
        return accessTokne;
    }

}
