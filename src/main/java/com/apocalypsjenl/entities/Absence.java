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

public class Absence extends WebEntity {

    private AbsenceEntity[] items;

    public Absence(String accessToken) {
        super("/rest/v1/absentiemeldingen", WebRequest.requestTypes.GET);

        this.addHeader("Accept", "application/vnd.topicus.platinum+json");
        this.addHeader("Authorization", "Bearer " + accessToken);
    }

    @Override
    public void handleResponse(String response) {
        JSONObject object = new JSONObject(response);
        JSONArray itemArray = object.getJSONArray("items");
        this.items = new AbsenceEntity[itemArray.length()];
        for(int i = 0; i < itemArray.length(); i++) {
            this.items[i] = new AbsenceEntity().parse(itemArray.getJSONObject(i));
        }
    }

    @Override
    public String toJson() {
        JSONObject object = new JSONObject();
        JSONArray itemArray = new JSONArray();
        for(AbsenceEntity item : this.items) {
            itemArray.put(item.toJson());
        }
        object.put("items", itemArray);
        return object.toString();
    }

    public AbsenceEntity[] getItems() {
        return items;
    }
}
