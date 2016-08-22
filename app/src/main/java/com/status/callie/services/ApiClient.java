package com.status.callie.services;

/**
 * Created by jivan.ghadage on 8/23/2016.
 */

import android.util.Log;

import com.status.callie.accounts.AccountConstants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    public static final String BASE_URL = AccountConstants.APP_NOTE_URL;
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
