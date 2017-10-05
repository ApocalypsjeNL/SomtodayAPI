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

import com.apocalypsjenl.util.WebRequest;
import org.json.JSONArray;
import org.json.JSONObject;

public class AbsenceEntity {

    private String type;
    private Link[] links;
    private Permission[] permissions;
    //TODO Research what this is
    private JSONObject additionalObjects;
    private Person pupil;
    private AbsenceReason reason;
    private String timeRegistered;
    private String timeStart;
    private String timeEnd;
    private Integer startLessonHour;
    private Integer endLessonHour;
    private Boolean solved;
    private Owner owner;

    public AbsenceEntity parse(JSONObject json) {
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
            this.permissions[i] = new Permission().parse((permissionArray.getJSONObject(i)));
        }
        this.additionalObjects = response.getJSONObject("additionalObjects");
        this.pupil = new Person().parse(response.getJSONObject("leerling"));
        this.reason = new AbsenceReason().parse(response.getJSONObject("absentieReden"));
        this.timeRegistered = response.getString("datumTijdInvoer");
        this.timeStart = response.getString("beginDatumTijd");
        this.timeEnd = response.getString("eindDatumTijd");
        this.startLessonHour = (Integer) response.get("beginLesuur");
        this.endLessonHour = (Integer) response.get("eindLesuur");
        this.solved = response.getBoolean("afgehandeld");
        this.owner = new Owner().parse(response.getJSONObject("eigenaar"));
    }

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
        object.put("leerling", this.pupil.toJson());
        object.put("absentieReden", this.reason.toJson());
        object.put("datumTijdInvoer", this.timeRegistered);
        object.put("beginDatumTijd", this.timeStart);
        object.put("eindDatumTijd", this.timeEnd);
        object.put("beginLesuur", this.startLessonHour);
        object.put("eindLesuur", this.endLessonHour);
        object.put("adgehandeld", this.solved);
        object.put("eigenaar", this.owner.toJson());
        return object.toString();
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

    public Person getPupil() {
        return pupil;
    }

    public AbsenceReason getReason() {
        return reason;
    }

    public String getTimeRegistered() {
        return timeRegistered;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public Integer getStartLessonHour() {
        return startLessonHour;
    }

    public Integer getEndLessonHour() {
        return endLessonHour;
    }

    public Boolean isSolved() {
        return solved;
    }

    public Owner getOwner() {
        return owner;
    }
}
