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
package com.dgm.form.data;

import com.joy.Joy;
import com.joy.mvc.actionTypes.ActionTypeForm;
import java.sql.ResultSet;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr)
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
