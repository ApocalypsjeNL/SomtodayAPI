package com.apocalypsjenl.entities;

import com.apocalypsjenl.util.WebRequest;

import java.util.HashMap;

public abstract class WebEntity {

    private String endpoint;
    private WebRequest.requestTypes method;

    private HashMap<String, String> headers = new HashMap<>();
    private HashMap<String, String> parameters = new HashMap<>();

    public WebEntity(String endpoint, WebRequest.requestTypes method) {
        this.endpoint = endpoint;
        this.method = method;
    }

    public abstract void handleResponse(String response);

    public abstract WebEntity parse(String json);

    public abstract String toJson();

    public HashMap<String, String> headers() {
        return headers;
    }

    public HashMap<String, String> parameters() {
        return parameters;
    }

    public void addParameter(String key, String value) {
        parameters.put(key, value);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public WebRequest.requestTypes getMethod() {
        return method;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
