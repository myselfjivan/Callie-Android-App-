package com.status.callie.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jivan.ghadage on 8/31/2016.
 */
public class PverifyResponse {
    public String getMessage() {
        return message;
    }

    public String getStatus_code() {
        return status_code;
    }

    @SerializedName("message")
    @Expose
    private String message;


    @SerializedName("status_code")
    @Expose
    private String status_code;
}
