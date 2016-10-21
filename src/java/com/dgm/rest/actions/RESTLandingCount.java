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
 * @author Benoit CAYLA (benoit@famillecayla.fr)
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
