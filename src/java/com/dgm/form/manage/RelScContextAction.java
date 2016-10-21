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
package com.dgm.form.manage;

import com.joy.Joy;
import com.joy.bo.BOEntityReadWrite;
import com.joy.mvc.actionTypes.ActionTypeForm;
import com.joy.mvc.formbean.JoyFormMatrixEntry;
import com.joy.mvc.formbean.JoyFormVectorEntry;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr)
 */
public class RelScContextAction  extends ActionTypeForm {

    @Override
    public String list() {
        JoyFormMatrixEntry matrix = new JoyFormMatrixEntry();
        try {
            IEntity Entity = this.getBOFactory().getEntity("REL_SC_CONTEXT");
            ResultSet rs = Entity.selectAll();
            while (rs.next()) {
                JoyFormVectorEntry columns = new JoyFormVectorEntry();
                columns.addValue("SCX_PK", rs.getString("SCX_PK"));
                columns.addValue("SCO_NAME", rs.getString("SCO_NAME"));
                columns.addValue("CON_DESCRIPTION", rs.getString("CON_DESCRIPTION"));
                columns.addValue("SCX_DESCRIPTION", rs.getString("SCX_DESCRIPTION"));
                matrix.addRow(columns);
            }
            this.getBOFactory().closeResultSet(rs);

        } catch (SQLException e) {
            Joy.log().error( e);
        }
        this.addFormMatrixEntry("LIST", matrix);
        return super.list(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String edit() {
        String uid = getStrArgumentValue("SCX_PK");
        if (!uid.equalsIgnoreCase("")) {
            try {
                IEntity Entity = this.getBOFactory().getEntity("REL_SC_CONTEXT");
                Entity.init();
                Entity.field("SCX_PK").setKeyValue(Integer.parseInt(uid));
                ResultSet rs = Entity.selectFiltered();

                if (rs.next()) {
                    this.addFormSingleEntry("SCX_PK", rs.getString("SCX_PK"));
                    this.addFormSingleEntry("SCO_NAME", rs.getString("SCO_NAME"));
                    this.addFormSingleEntry("CON_DESCRIPTION", rs.getString("CON_DESCRIPTION"));
                    this.addFormSingleEntry("SCX_DESCRIPTION", rs.getString("SCX_DESCRIPTION"));
                }
                this.getBOFactory().closeResultSet(rs);

            } catch (SQLException e) {
                Joy.log().error( e);
            }
        }
        
        loadCBOAvailableContexts(this.getFormSingleEntry("CON_DESCRIPTION").getStrValue());
        loadCBOAvailableScorecards(this.getFormSingleEntry("SCO_NAME").getStrValue());
        return super.edit(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String other() {
        return this.list();
    }

    @Override
    public String add() {
        this.addFormSingleEntry("SCX_PK", "0");
        this.addFormSingleEntry("CON_DESCRIPTION", "");
        this.addFormSingleEntry("SCX_DESCRIPTION", "");
        loadCBOAvailableContexts("");
        loadCBOAvailableScorecards("");
        return super.add(); 
    }

    @Override
    public String delete() {
        BOEntityReadWrite Entity = (BOEntityReadWrite)this.getBOFactory().getEntity("REL_SC_CONTEXT");
        int uid = getIntArgumentValue("SCX_PK");
        if (uid != 0) {
            Entity.init();
            Entity.field("SCX_PK").setKeyValue(uid);
            Entity.delete();
        }
        return this.list();
    }

    @Override
    public String update() {
        int uid = getIntArgumentValue("SCX_PK"); 
        
    	try {
            BOEntityReadWrite Entity = (BOEntityReadWrite)this.getBOFactory().getEntity("REL_SC_CONTEXT");
            Entity.init();
            Entity.field("SCO_NAME").setValue(getStrArgumentValue("SCO_NAME"));
            Entity.field("SCX_DESCRIPTION").setValue(getStrArgumentValue("SCX_DESCRIPTION"));
            Entity.field("CON_DESCRIPTION").setValue(getStrArgumentValue("CON_DESCRIPTION"));
            if (uid == 0) {
                Entity.field("SCX_PK").setNextIDValue();
                Entity.insert();
            } else {
                Entity.field("SCX_PK").setKeyValue(uid);
                Entity.update();
            } 
            
        } catch (Exception e) {
            Joy.log().error( e);
        }
        return this.list();
    }
    
    private void loadCBOAvailableContexts(String _selected) {
        JoyFormVectorEntry columns = new JoyFormVectorEntry();
        try {
            IEntity EntitySrcContext = this.getBOFactory().getEntity("SRC_CONTEXT");
            EntitySrcContext.addSort("CON_DESCRIPTION");
            ResultSet rs = EntitySrcContext.selectAll();
            columns.setSelected(_selected);
            
            while (rs.next()) {
                columns.addValue("CON_DESCRIPTION", rs.getString("CON_DESCRIPTION"));
            }
            this.getBOFactory().closeResultSet(rs);
            
        } catch (SQLException e) {
            Joy.log().error( e);
        }
        this.addFormVectorEntry("CON_DESCRIPTION", columns);
    }
    
    private void loadCBOAvailableScorecards(String CurrentScorecard) {
        JoyFormVectorEntry columns = new JoyFormVectorEntry();
        
        try {
            IEntity EntitySrcContext = this.getBOFactory().getEntity("Scorecard Dimension");
            EntitySrcContext.addSort("SCO_NAME");
            EntitySrcContext.addFilter("SCO_NAME NOT IN (SELECT SCO_NAME FROM REL_SC_CONTEXT)");
            ResultSet rs = EntitySrcContext.selectFiltered();
            columns.setSelected(CurrentScorecard);
            
            while (rs.next()) {
                columns.addValue("SCO_NAME", rs.getString("SCO_NAME"));
            }
            if (!CurrentScorecard.isEmpty())
                columns.addValue("SCO_NAME", CurrentScorecard);
            this.getBOFactory().closeResultSet(rs);
            
        } catch (SQLException e) {
            Joy.log().error( e);
        }
        this.addFormVectorEntry("SCO_NAME", columns);
    }
    
}
