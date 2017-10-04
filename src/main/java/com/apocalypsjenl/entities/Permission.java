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

import org.json.JSONArray;
import org.json.JSONObject;

public class Permission {

    private String full;
    private String type;
    private String[] operations;
    private String[] instances;

    public Permission parse(JSONObject json) {
        this.handleResponse(json);
        return this;
    }

    public void handleResponse(JSONObject response) {
        this.full = response.getString("full");
        this.type = response.getString("type");
        JSONArray operationsArray = response.getJSONArray("operations");
        this.operations = new String[operationsArray.length()];
        for(int i = 0; i < operationsArray.length(); i++) {
            this.operations[i] = operationsArray.getString(i);
        }
        JSONArray instancesArray = response.getJSONArray("instances");
        this.instances = new String[instancesArray.length()];
        for(int i = 0; i < instancesArray.length(); i++) {
            this.instances[i] = instancesArray.getString(i);
        }
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        object.put("full", this.full);
        object.put("type", this.type);
        JSONArray operationsArray = new JSONArray();
        for(String operation : this.operations) {
            operationsArray.put(operation);
        }
        object.put("operations", operationsArray);
        JSONArray instancesArray = new JSONArray();
        for(String instance : this.instances) {
            instancesArray.put(instance);
        }
        object.put("instances", instancesArray);
        return object;
    }

    public String getFull() {
        return full;
    }

    public String getType() {
        return type;
    }

    public String[] getOperations() {
        return operations;
    }

    public String[] getInstances() {
        return instances;
    }
}
