/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.form.data;

import com.joy.Joy;
import com.joy.bo.BOEntityReadOnly;
import com.joy.bo.BOEntityReadWrite;
import com.joy.mvc.actionTypes.ActionTypeForm;
import java.sql.ResultSet;
import com.joy.bo.IEntity;

/**
 *
 * @author benoit
 */
public class LNDCommonAction extends ActionTypeForm {

    protected String LandingTableName;
    protected String LandingKeyName;

    protected void editSpecific (ResultSet rs) {}
    protected void addSpecific () {}
    protected void updateSpecific(IEntity Entity) {}    
    
    /**
     * Load Business term  data
     * @return 
     */
    @Override
    public String list() {
        int i = this.getIntArgumentValue("LIMIT");
        
        // Load the data table result
        IEntity entity = this.getBOFactory().getEntity(LandingTableName);
        entity.addSort(LandingKeyName);
        if (i != 0)
            entity.setLimitRecords(i);
        
        ResultSet rs = entity.selectAll();
        this.loadMatrix(rs, "LIST");
        this.getBOFactory().closeResultSet(rs);
        this.addFormSingleEntry("LIMIT", i);
        
        return super.list(); 
    }

    @Override
    public String edit() {
        String uid = getStrArgumentValue(this.LandingKeyName);
        if (!uid.equalsIgnoreCase("")) {
            try {
                IEntity Entity = this.getBOFactory().getEntity(this.LandingTableName);
                Entity.init();
                Entity.field(this.LandingKeyName).setKeyValue(uid);
                ResultSet rs = Entity.selectFiltered();

                this.loadSingle(rs);
                editSpecific(rs);
                this.getBOFactory().closeResultSet(rs);

            } catch (Exception e) {
                Joy.log().error(  e);
            }
        }
        return super.edit(); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String add() {
        this.addFormSingleEntry(LandingKeyName, "");
        this.addFormSingleEntry("NEW", "yes");
        addSpecific();
        return super.add();
    }
    
    @Override
    public String delete() {
        String uid = getStrArgumentValue(LandingKeyName);
        if (!uid.isEmpty()) {
            IEntity Entity = this.getBOFactory().getEntity(LandingTableName);
            Entity.field(LandingKeyName).setKeyValue(uid);
            Entity.delete();
        }
        return this.list();
    }

    @Override
    public String update() {
        boolean isNew = (getStrArgumentValue("NEW").equalsIgnoreCase("yes")); 
        
    	try {
            IEntity Entity = this.getBOFactory().getEntity(this.LandingTableName);
            updateSpecific(Entity);

            if (isNew) {
                Entity.field(LandingKeyName).setValue(getStrArgumentValue(LandingKeyName));
                Entity.insert();
            } else {
                Entity.field(LandingKeyName).setKeyValue(getStrArgumentValue(LandingKeyName));
                Entity.update();
            } 
            
        } catch (Exception e) {
            Joy.log().error( e);
        }
        return this.list();
    }    

}
