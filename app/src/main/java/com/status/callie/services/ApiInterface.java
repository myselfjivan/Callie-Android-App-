package com.status.callie.services;

/**
 * Created by jivan.ghadage on 8/23/2016.
 */

import com.status.callie.Model.AccessToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @Headers("Content-Type: application/json")
    @FormUrlEncoded
    @POST("oauth/request/")
    Call<AccessToken> getOauthRequest(@Field("grant_type") String grant_type,
                                      @Field("client_id") String client_id,
                                      @Field("client_secret") String client_secret,
                                      @Field("username") String username,
                                      @Field("password") String password
    );

}
