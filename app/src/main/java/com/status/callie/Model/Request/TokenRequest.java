package com.status.callie.Model.Request;

/**
 * Created by jivan.ghadage on 8/24/2016.
 */
public class TokenRequest {

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    private String grant_type;
    private String client_id;
    private String client_secret;
    private String username;
    private String password;
    private String refresh_token;
}
