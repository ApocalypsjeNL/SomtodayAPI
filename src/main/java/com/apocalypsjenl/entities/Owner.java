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

public class Owner {

    private Link[] links;
    private Permission[] permissions;
    //TODO Research what this is
    private JSONObject additionalObjects;
    private Integer number;
    private String shortName;
    private String lastName;
    private String gender;
    private String firstLetters;

    public Owner parse(JSONObject json) {
        this.handleResponse(json);
        return this;
    }

    public void handleResponse(JSONObject response) {
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
        this.number = (Integer) response.get("nummer");
        this.shortName = response.getString("afkorting");
        this.lastName = response.getString("achternaam");
        this.gender = response.getString("geslacht");
        this.firstLetters = response.getString("voorletters");
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
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
        object.put("nummer", this.number);
        object.put("afkorting", this.shortName);
        object.put("achternaam", this.lastName);
        object.put("geslacht", this.gender);
        object.put("voorletters", this.firstLetters);
        return object;
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

    public Integer getNumber() {
        return number;
    }

    public String getShortName() {
        return shortName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFirstLetters() {
        return firstLetters;
    }
}
