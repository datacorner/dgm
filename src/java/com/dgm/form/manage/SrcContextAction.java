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
public class SrcContextAction extends ActionTypeForm {
    
    @Override
    public String list() {
        try {
            IEntity Entity = this.getBOFactory().getEntity("SRC_CONTEXT");
            ResultSet rs = Entity.select();
            JoyFormMatrixEntry matrix = new JoyFormMatrixEntry();
            while (rs.next()) {
                JoyFormVectorEntry columns = new JoyFormVectorEntry();
                columns.addValue("CON_PK", String.valueOf(rs.getInt("CON_PK")));
                columns.addValue("CON_DESCRIPTION", rs.getString("CON_DESCRIPTION"));
                columns.addValue("CON_FUNCKEY", rs.getString("CON_FUNCKEY"));
                matrix.addRow(columns);
            }
            this.getBOFactory().closeResultSet(rs);
            this.addFormMatrixEntry("CONTEXTLIST", matrix);
            
        } catch (SQLException e) {
            Joy.log().error(  e);
        }
        return super.list(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String edit() {
        String uid = getStrArgumentValue("CON_PK");
        if (!uid.equalsIgnoreCase("")) {
            try {
                IEntity Entity = this.getBOFactory().getEntity("SRC_CONTEXT");
                Entity.reset();
                Entity.field("CON_PK").setKeyValue(Integer.parseInt(uid));
                ResultSet rs = Entity.select();

                if (rs.next()) {
                    this.addFormSingleEntry("CON_PK", String.valueOf(rs.getInt("CON_PK")));
                    this.addFormSingleEntry("CON_DESCRIPTION", rs.getString("CON_DESCRIPTION"));
                    this.addFormSingleEntry("CON_FUNCKEY", rs.getString("CON_FUNCKEY"));
                }
                this.getBOFactory().closeResultSet(rs);

            } catch (SQLException e) {
                Joy.log().error(  e);
            }
        }
        return super.edit();
    }
    
    @Override
    public String other() {
        return this.list();
    }

    @Override
    public String add() {
        this.addFormSingleEntry("CON_PK", "0");
        this.addFormSingleEntry("CON_DESCRIPTION", "");
        this.addFormSingleEntry("CON_FUNCKEY", "");
        return super.add(); 
    }

    @Override
    public String delete() {
        BOEntityReadWrite Entity = (BOEntityReadWrite)this.getBOFactory().getEntity("SRC_CONTEXT");
        int uid = getIntArgumentValue("CON_PK");
        if (uid != 0) {
            Entity.reset();
            Entity.field("CON_PK").setKeyValue(uid);
            Entity.delete();
        }
        return this.list();
    }

    @Override
    public String update() {
        int uid = getIntArgumentValue("CON_PK"); 
        
    	try {
            BOEntityReadWrite Entity = (BOEntityReadWrite)this.getBOFactory().getEntity("SRC_CONTEXT");
            Entity.reset();
            Entity.field("CON_DESCRIPTION").setValue(getStrArgumentValue("CON_DESCRIPTION"));
            Entity.field("CON_FUNCKEY").setValue(getStrArgumentValue("CON_FUNCKEY"));
            if (uid == 0) {
                Entity.field("CON_PK").setNextIDValue();
                Entity.insert();
            } else {
                Entity.field("CON_PK").setKeyValue(uid);
                Entity.update();
            } 
            
        } catch (Exception e) {
            Joy.log().error( e);
        }
        return this.list();
    }

}
