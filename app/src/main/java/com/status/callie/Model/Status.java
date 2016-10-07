package com.status.callie.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.status.callie.Model.Request.LoginRequest;
import com.status.callie.Model.Request.StatusRequest;
import com.status.callie.Model.Response.LoginResponse;
import com.status.callie.Model.Response.StatusResponse;
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


    public String TAG = "Token Genration";
    private Context context;

    private SharedPreferences shared_pref_login;

    public Status(Context context) {
        this.context = context;
    }


    public String statusStore(String status) {
        shared_pref_login = context.getSharedPreferences(AccountConstants.SHARED_PREF_LOGIN, Context.MODE_PRIVATE);
        StatusRequest statusRequest = new StatusRequest();
        statusRequest.setStatus(status);

        ApiInterface apiService = null;
        try {
            apiService = ApiClient
                    .getClient()
                    .create(ApiInterface.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "statusStore: jwtToken" + shared_pref_login.getString(AccountConstants.JWT_ACCESS_TOKEN, ""));
        Call<StatusResponse> call = apiService.statusStore(statusRequest, shared_pref_login.getString(AccountConstants.JWT_ACCESS_TOKEN, ""));
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, retrofit2.Response<StatusResponse> response) {
                if (response.isSuccessful()) {
                    StatusResponse statusResponse = response.body();
                    Log.d(TAG, "onResponse: status" + statusResponse.getStatus_code());
                } else {
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
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Log.e("on failure", t.toString());
            }
        });
        return null;
    }
}
