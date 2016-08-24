package com.status.callie.services;

/**
 * Created by jivan.ghadage on 8/23/2016.
 */

import com.status.callie.Model.TokenRequest;
import com.status.callie.Model.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("oauth/request/")
    Call<TokenResponse> getTokenAccess(@Body TokenRequest tokenRequest);
}
