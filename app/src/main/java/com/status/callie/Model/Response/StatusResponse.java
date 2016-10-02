package com.status.callie.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by om on 2/10/16.
 */
public class StatusResponse {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status_code")
    @Expose
    private String status_code;
}