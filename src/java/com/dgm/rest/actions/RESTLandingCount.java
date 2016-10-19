/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.rest.actions;

import com.dgm.rest.RESTDataCommon;
import com.joy.json.JSONObject;
import java.util.ArrayList;
import java.util.Collection;
import com.joy.bo.IEntity;

/**
 * Return count information about all the landing tables
 * call with ./rest/landingcount
 *  no more arguments
 * @author Administrator
 */
public class RESTLandingCount extends RESTDataCommon {

    @Override
    public String restGet() {
        Collection<JSONObject> landings = new ArrayList<>();
        landings.add(CountLanding("LND_TERM"));
        landings.add(CountLanding("LND_GLOSSARY"));
        landings.add(CountLanding("LND_CATEGORY"));
        landings.add(CountLanding("LND_TERM_RELATIONSHIPS"));
        landings.add(CountLanding("LND_METRIC"));
        landings.add(CountLanding("LND_SCORECARD"));
        landings.add(CountLanding("LND_SCORECARD_GROUP"));
        
        JSONObject retObj = new JSONObject();
        retObj.put("LandingCount", landings);
        return retObj.toString();
    }

    /**
     * Count the record in one landing table
     * @param landing
     * @return json subobject counting records
     */
    private JSONObject CountLanding(String landing) {
        try {
            IEntity lndTable = this.getBOFactory().getEntity(landing);
            JSONObject item = new JSONObject();
            JSONObject detail = new JSONObject();
            detail.put("tablename", landing);
            detail.put("tablecount", lndTable.count());
            item.put("landing", detail);
            return item;
            
        } catch (Exception e) {
            return null;
        }
    }
    
}
