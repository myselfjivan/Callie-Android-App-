package com.status.callie.services;

/**
 * Created by jivan.ghadage on 8/23/2016.
 */

import com.status.callie.Model.Request.PregisterRequest;
import com.status.callie.Model.Request.PverifyRequest;
import com.status.callie.Model.Request.TokenRequest;
import com.status.callie.Model.Response.PregisterResponse;
import com.status.callie.Model.Response.PverifyResponse;
import com.status.callie.Model.Response.TokenResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("oauth/request")
    Call<TokenResponse> oauthRequest(@Body TokenRequest tokenRequest);

    @POST("oauth/rerequest")
    Call<TokenResponse> oauthReRequest(@Body TokenRequest tokenRequest);

    @POST("auth/pregister")
    Call<PregisterResponse> authPregister(@Body PregisterRequest pregisterRequest);

    @POST("auth/pverify")
    Call<PverifyResponse> authPverify(@Body PverifyRequest pverifyRequest);
}
