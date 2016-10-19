/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.beans.termreltree;

import com.joy.json.JSONObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Benoit CAYLA
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
        Collection<JSONObject> items = new ArrayList<JSONObject>();
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
