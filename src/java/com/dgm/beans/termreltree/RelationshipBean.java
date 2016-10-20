/*
 * Copyright (C) 2016 Benoit CAYLA (benoit@famillecayla.fr)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.dgm.beans.termreltree;

import com.joy.json.JSONObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class manages relationships between the business terms
 * @author Benoit CAYLA (benoit@famillecayla.fr)
 */
public class RelationshipBean {
    private String name;
    private int key;
    private int termKey;
    private List<TermBean> relTerms;

    public List<TermBean> terms() {
        return relTerms;
    }

    public RelationshipBean(String Name, int Key, int TermKey) {
        relTerms = new ArrayList();
        this.name = Name;
        this.key = Key;
        this.termKey = TermKey;
    }

    public void addRelatedTerm(TermBean term) {
        relTerms.add(term);
    }

    public String getName() {
        return name;
    }

    public int getKey() {
        return key;
    }

    public JSONObject getJSONBootstrapTreeStream(String URI) {
        Collection<JSONObject> items = new ArrayList<>();
        JSONObject itemChildren = new JSONObject();

        for (TermBean term : relTerms) {
            items.add(term.getJSONObject(URI));
        }
        itemChildren.put("text", name);
        itemChildren.put("nodes", items);
        //itemChildren.put("icon", URI + "link.png");
        //itemChildren.put("url", Utils.reportURL2params("basicsbyrelationship", "term",  String.valueOf(termKey), "rel", String.valueOf(key)));
        itemChildren.put("href", "#");
        return itemChildren;
    }

}
