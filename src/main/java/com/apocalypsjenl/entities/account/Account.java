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

import com.apocalypsjenl.entities.WebEntity;
import com.apocalypsjenl.util.WebRequest;
import org.json.JSONArray;
import org.json.JSONObject;

public class Account extends WebEntity {

    private Link[] links;
    private Permission[] permissions;
    //TODO Research what this is
    private JSONObject additionalObjects;
    private String username;
    //TODO Research what this is
    private JSONArray accountPermissions;
    private Person person;

    public Account(String accessToken) {
        super("/rest/v1/account/me", WebRequest.requestTypes.GET);

        this.addHeader("Accept", "application/json");
        this.addHeader("Authorization", "Bearer " + accessToken);
    }

    @Override
    public WebEntity parse(String json) {
        this.handleResponse(json);
        return this;
    }

    @Override
    public void handleResponse(String response) {
        JSONObject object = new JSONObject(response);
        JSONArray linkArray = object.getJSONArray("links");
        this.links = new Link[linkArray.length()];
        for(int i = 0; i < linkArray.length(); i++) {
            this.links[i] = new Link().parse(linkArray.getJSONObject(i));
        }
        JSONArray permissionArray = object.getJSONArray("permissions");
        this.permissions = new Permission[permissionArray.length()];
        for(int i = 0; i < permissionArray.length(); i++) {
            this.permissions[i] = new Permission().parse((permissionArray.getJSONObject(i)));
        }
        this.additionalObjects = (JSONObject) object.get("additionalObjects");
        this.username = object.getString("gebruikersnaam");
        this.accountPermissions = (JSONArray) object.get("accountPermissions");
        this.person = new Person().parse(object.getJSONObject("persoon"));
    }

    @Override
    public String toJson() {
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
        object.put("gebruikersnaam", this.username);
        object.put("accountPermissions", this.accountPermissions);
        object.put("persoon", this.person.toJson());
        return object.toString();
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

    public String getUsername() {
        return username;
    }

    public JSONArray getAccountPermissions() {
        return accountPermissions;
    }

    public Person getPerson() {
        return person;
    }
}