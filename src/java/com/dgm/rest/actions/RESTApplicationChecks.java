/*
 * Copyright (C) 2016 Benoit CAYLA <benoit@famillecayla.fr>
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

import com.dgm.common.Constants;
import com.joy.json.JSONObject;
import com.joy.mvc.actionTypes.ActionTypeREST;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA <benoit@famillecayla.fr>
 * Retourne un flux JSON avec toutes les vérifications de l'application
 * http://localhost:18180/GovManagementTool/rest/checks/
 */

public class RESTApplicationChecks extends ActionTypeREST {

    /*
    * Vérifie si la ligne UNKNOWN (id = 0) existe bien dans toutes les tables
    */
    private JSONObject CheckUnknownInTable(String Table, String Key) {
        IEntity entity;
        JSONObject retObj = new JSONObject();
        ResultSet rs;
        boolean empty = true;
        JSONObject detail = new JSONObject();
        
        try {
            entity = this.getBOFactory().getEntity(Table);
            entity.field(Key).setKeyValue(Constants.UNKNOWN_VALUE);
            rs = entity.selectFiltered();
            empty = (!rs.next());
            
        } catch (Exception ex) {
            detail.put("Status", "KO");
            detail.put("Entity", Table);
            detail.put("Message", "The entity/Table may not exist, " + ex.toString());
            retObj.put(Table, detail);
            return retObj;
        }
        
        detail.put("Status", (empty ? "KO" : "OK"));
        detail.put("Entity", Table);
        detail.put("Message", (empty ? "No record found for Unknown (ID=0) this record is mandatory." : "Table configuration OK."));
        retObj.put("Check", detail);
        this.getBOFactory().closeResultSet(rs);
        
        return retObj;
    }
    
    private Collection<JSONObject> CheckTables() {
        Collection<JSONObject> checks = new ArrayList<>();
        
        checks.add(CheckUnknownInTable("DIM_CONTEXT", "CON_PK"));
        checks.add(CheckUnknownInTable("DIM_JOB", "JOB_PK"));
        checks.add(CheckUnknownInTable("Scorecard Dimension", "Scorecard Dimension PKey"));
        checks.add(CheckUnknownInTable("DIM_SCORECARD_GROUP", "SCG_PK"));
        checks.add(CheckUnknownInTable("DIM_METRIC", "MET_PK"));
        checks.add(CheckUnknownInTable("DIM_TERM", "TRM_PK"));
        checks.add(CheckUnknownInTable("DIM_DATASOURCE", "DSO_PK"));
        checks.add(CheckUnknownInTable("DIM_DQAXIS", "DQX_PK"));
        checks.add(CheckUnknownInTable("DIM_GLOSSARY", "GLO_PK"));
        checks.add(CheckUnknownInTable("DIM_CATEGORY", "CAT_PK"));
        checks.add(CheckUnknownInTable("DIM_TERM_RELATIONSHIP", "REL_PK"));
        checks.add(CheckUnknownInTable("DIM_TERM_RELLINKS", "TRL_PK"));
        checks.add(CheckUnknownInTable("DIM_ORIGINE", "ORI_PK"));
        
        return checks;
    }
    
    private Collection<JSONObject> CheckEntities() {
        Collection<JSONObject> checkEntities = new ArrayList<>();

        for (IEntity entity : this.getBOFactory().getAll()) {
            entity.init();
            JSONObject retEntity = new JSONObject();
            JSONObject detail = new JSONObject();
            detail.put("Status", (entity.isInitialized() ? "OK" : "KO")); 
            detail.put("Message", (entity.isInitialized() ? "Initialized" : "Not Initialized properly"));
            if (entity.isInitialized())
                detail.put("Count", (entity.count()));
            else
                detail.put("Count", "No"); 
            detail.put("Entity", entity.getName());
            
            retEntity.put("Check", detail);  
            checkEntities.add(retEntity);
        }

        return checkEntities;
    }
    
    
    @Override
    public String restGet() {
        JSONObject retObj = new JSONObject();

        retObj.put("TableCheck", CheckTables());
        retObj.put("EntityCheck", CheckEntities());
        
        return retObj.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
