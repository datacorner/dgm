/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.rest.actions;

import com.dgm.common.Constants;
import com.joy.bo.BOEntityReadOnly;
import com.joy.bo.BOEntityReadWrite;
import com.joy.json.JSONObject;
import com.joy.mvc.actionTypes.ActionTypeREST;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA
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
