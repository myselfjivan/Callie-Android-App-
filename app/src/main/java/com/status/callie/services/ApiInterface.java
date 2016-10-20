package com.status.callie.services;

/**
 * Created by jivan.ghadage on 8/23/2016.
 */

import com.status.callie.model.Request.LoginRequest;
import com.status.callie.model.Request.PregisterRequest;
import com.status.callie.model.Request.PverifyRequest;
import com.status.callie.model.Request.StatusRequest;
import com.status.callie.model.Request.TokenRequest;
import com.status.callie.model.Response.GetLast10Status;
import com.status.callie.model.Response.LoginResponse;
import com.status.callie.model.Response.PregisterResponse;
import com.status.callie.model.Response.PverifyResponse;
import com.status.callie.model.Response.StatusResponse;
import com.status.callie.model.Response.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("oauth/request")
    Call<TokenResponse> oauthRequest(@Body TokenRequest tokenRequest);

    @POST("oauth/rerequest")
    Call<TokenResponse> oauthReRequest(@Body TokenRequest tokenRequest);

    @POST("auth/pregister")
    Call<PregisterResponse> authPregister(@Body PregisterRequest pregisterRequest);

    @POST("auth/pverify")
    Call<PverifyResponse> authPverify(@Body PverifyRequest pverifyRequest);

    @POST("auth/login")
    Call<LoginResponse> authLogin(@Body LoginRequest loginRequest);

    @POST("status/store")
    Call<StatusResponse> storeStatus(@Body StatusRequest statusRequest, @Query("token") String token);

    @GET("status")
    Call<GetLast10Status> getStatus(@Query("token") String token);

    @GET("status/last10")
    Call<GetLast10Status> getLast10Status(@Query("token") String token);
}
