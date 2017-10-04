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

package com.apocalypsjenl.entities;

import com.apocalypsjenl.exception.InvalidRequestTypeException;
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

    public WebEntity parse(String json) {
        this.handleResponse(json);
        return this;
    }

    public abstract String toJson();

    public void makeRequest() {
        try {
            WebRequest.makeRequest(this);
        } catch (InvalidRequestTypeException e) {
            e.printStackTrace();
        }
    }

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
