/*
 * Copyright (c) 2017 Niek Vincent
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.apocalypsjenl.entities.oauth;

import com.apocalypsjenl.entities.WebEntity;
import com.apocalypsjenl.exception.InvalidRequestTypeException;
import com.apocalypsjenl.util.WebRequest;
import org.json.JSONObject;

import java.util.Base64;

public class User extends WebEntity {

    private String accessToken;
    private String refreshToken;
    private String apiUrl;
    private String scope;
    private String tenant;
    private String idToken;
    private String tokenType;
    private Integer expiresIn;

    public User() {
        super("/oauth2/token", WebRequest.requestTypes.POST);

        this.addHeader("Accept", "application/json");
        this.addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString((WebRequest.clientId + ":" + WebRequest.secretId).getBytes()));
    }

    @Override
    public User parse(String json) {
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
