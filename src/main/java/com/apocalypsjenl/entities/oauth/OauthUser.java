package com.apocalypsjenl.entities.oauth;

import com.apocalypsjenl.entities.WebEntity;
import com.apocalypsjenl.exception.InvalidRequestTypeException;
import com.apocalypsjenl.util.WebRequest;
import org.json.JSONObject;

import java.util.Base64;

public class OauthUser extends WebEntity {

    private String accessToken;
    private String refreshToken;
    private String apiUrl;
    private String scope;
    private String tenant;
    private String idToken;
    private String tokenType;
    private Integer expiresIn;

    public OauthUser() {
        super("/oauth2/token", WebRequest.requestTypes.POST);
    }

    @Override
    public OauthUser parse(String json) {
        this.handleResponse(json);
        return this;
    }

    @Override
    public void handleResponse(String response) {
        JSONObject object = new JSONObject(response);
        this.accessToken = object.getString("access_token");
        this.refreshToken = object.getString("refresh_token");
        this.apiUrl = object.getString("somtoday_api_url");
        this.scope = object.getString("scope");
        this.tenant = object.getString("somtoday_tenant");
        this.idToken = object.getString("id_token");
        this.tokenType = object.getString("token_type");
        this.expiresIn = (Integer) object.get("expires_in");
    }

    @Override
    public String toJson() {
        JSONObject object = new JSONObject();
        object.put("access_token", this.getAccessToken());
        object.put("refresh_token", this.getRefreshToken());
        object.put("somtoday_api_url", this.getApiUrl());
        object.put("scope", this.getScope());
        object.put("somtoday_tenant", this.getTenant());
        object.put("id_token", this.getIdToken());
        object.put("token_type", this.getTokenType());
        object.put("expires_in", this.getExpiresIn());
        return object.toString();
    }

    public void login(String schoolUuid, String username, String password) {
        this.addHeader("Accept", "application/json");
        this.addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString((WebRequest.clientId + ":" + WebRequest.secretId).getBytes()));

        this.addParameter("username", schoolUuid + "\\" + username);
        this.addParameter("password", password);
        this.addParameter("scope", "openid");
        this.addParameter("grant_type", "password");

        try {
            WebRequest.makeRequest(this);
        } catch (InvalidRequestTypeException e) {
            e.printStackTrace();
        }
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getScope() {
        return scope;
    }

    public String getTenant() {
        return tenant;
    }

    public String getIdToken() {
        return idToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }
}
