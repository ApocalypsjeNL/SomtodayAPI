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

package com.apocalypsjenl.entities.account;

import org.json.JSONArray;
import org.json.JSONObject;

public class Person {

    private String type;
    private Link[] links;
    private Permission[] permissions;
    //TODO Research what this is
    private JSONObject additionalObjects;
    private Integer pupilId;
    private String firstName;
    private String lastName;

    public Person parse(JSONObject json) {
        this.handleResponse(json);
        return this;
    }

    public void handleResponse(JSONObject response) {
        this.type = response.getString("$type");
        JSONArray linkArray = response.getJSONArray("links");
        this.links = new Link[linkArray.length()];
        for(int i = 0; i < linkArray.length(); i++) {
            this.links[i] = new Link().parse(linkArray.getJSONObject(i));
        }
        JSONArray permissionArray = response.getJSONArray("permissions");
        this.permissions = new Permission[permissionArray.length()];
        for(int i = 0; i < permissionArray.length(); i++) {
            this.permissions[i] = new Permission().parse(permissionArray.getJSONObject(i));
        }
        this.additionalObjects = (JSONObject) response.get("additionalObjects");
        this.pupilId = (Integer) response.get("leerlingnummer");
        this.firstName = response.getString("roepnaam");
        this.lastName = response.getString("achternaam");
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        object.put("$type", this.type);
        JSONArray linkArray = new JSONArray();
        for(Link link : this.links) {
            linkArray.put(link.toJson());
        }
        object.put("links", linkArray);
        JSONArray permissionArray = new JSONArray();
        for(Permission permission : this.permissions) {
            permissionArray.put(permission.toJson());
        }
        object.put("permissions", permissionArray);
        object.put("additionalObjects", this.additionalObjects);
        object.put("leerlingnummer", this.pupilId);
        object.put("roepnaam", this.firstName);
        object.put("achternaam", this.lastName);
        return object;
    }

    public String getType() {
        return type;
    }

    public Link[] getLinks() {
        return links;
    }

    public Permission[] getPermissions() {
        return permissions;
    }

    public JSONObject getAdditionalObjects() {
        return additionalObjects;
    }

    public Integer getPupilId() {
        return pupilId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
