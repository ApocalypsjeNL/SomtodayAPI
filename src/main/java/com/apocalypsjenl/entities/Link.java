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

import org.json.JSONObject;

public class Link {

    private Integer id;
    private String rel;
    private String type;
    private String href;

    public Link parse(JSONObject json) {
        this.handleResponse(json);
        return this;
    }

    public void handleResponse(JSONObject response) {
        this.id = (Integer) response.get("id");
        this.rel = response.getString("rel");
        this.type = response.getString("type");
        this.href = response.getString("href");
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        object.put("id", this.id);
        object.put("rel", this.rel);
        object.put("type", this.type);
        object.put("href", this.href);
        return object;
    }

    public Integer getId() {
        return id;
    }

    public String getRel() {
        return rel;
    }

    public String getType() {
        return type;
    }

    public String getHref() {
        return href;
    }
}
