package com.status.callie.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by om on 9/10/16.
 */
public class GetLast10Status {

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }


    public String getTimezone_type() {
        return timezone_type;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getDate() {
        return date;
    }

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("updated_at")
    @Expose
    private String date;

    @SerializedName("date")
    @Expose
    private String updated_at;

    @SerializedName("timezone_type")
    @Expose
    private String timezone_type;

    @SerializedName("timezone")
    @Expose
    private String timezone;

}
