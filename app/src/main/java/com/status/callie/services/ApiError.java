package com.status.callie.services;

/**
 * Created by jivan.ghadage on 8/29/2016.
 */
public class ApiError {
    private int statusCode;
    private String message;

    public ApiError() {
    }

    public int status() {
        return statusCode;
    }

    public String message() {
        return message;
    }
}
