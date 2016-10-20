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
package com.dgm.form.manage;

import com.joy.Joy;
import com.joy.bo.BOEntityReadWrite;
import com.joy.common.JoyParameter;
import com.joy.mvc.actionTypes.ActionTypeForm;
import com.joy.mvc.formbean.JoyFormMatrixEntry;
import com.joy.mvc.formbean.JoyFormVectorEntry;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA <benoit@famillecayla.fr>
 */
public class SrcTermTypeAction extends ActionTypeForm {
    
    @Override
    public String list() {
        try {
            JoyFormMatrixEntry matrix = new JoyFormMatrixEntry();
            IEntity Entity = this.getBOFactory().getEntity("SRC_TERMTYPE");
            ResultSet rs = Entity.selectAll();
            while (rs.next()) {
                JoyFormVectorEntry columns = new JoyFormVectorEntry();
                columns.addValue("GIO_PK", rs.getInt("GIO_PK"));
                columns.addValue("GIO_TERMTYPE_NAME", rs.getString("GIO_TERMTYPE_NAME"));
                columns.addValue("GIO_ICON_PATHNAME", rs.getString("GIO_ICON_PATHNAME"));
                matrix.addRow(columns);
            }
            this.getBOFactory().closeResultSet(rs);
            this.addFormMatrixEntry("LIST", matrix);
            
        } catch (SQLException e) {
            Joy.log().error( e);
        }
        return super.list(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String edit() {
        String uid = getStrArgumentValue("GIO_PK");
        if (!uid.equalsIgnoreCase("")) {
            try {
                IEntity Entity = this.getBOFactory().getEntity("SRC_TERMTYPE");
                Entity.init();
                Entity.field("GIO_PK").setKeyValue(uid);
                ResultSet rs = Entity.selectFiltered();

                if (rs.next()) {
                    this.addFormSingleEntry("GIO_PK", rs.getInt("GIO_PK"));
                    this.addFormSingleEntry("GIO_TERMTYPE_NAME", rs.getString("GIO_TERMTYPE_NAME"));
                    this.addFormSingleEntry("GIO_ICON_PATHNAME", rs.getString("GIO_ICON_PATHNAME"));
                }
                this.getBOFactory().closeResultSet(rs);

            } catch (SQLException e) {
                Joy.log().error( e);
            }
        }
        
        loadCBOGlossaries(this.getFormSingleEntry("GIO_TERMTYPE_NAME").getStrValue());
        loadCBOAvailableIcons(this.getFormSingleEntry("GIO_ICON_PATHNAME").getStrValue());
        
        return super.edit(); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String delete() {
        
        int uid = getIntArgumentValue("GIO_PK");
        if (uid != 0) {
            BOEntityReadWrite Entity = (BOEntityReadWrite)this.getBOFactory().getEntity("SRC_TERMTYPE");
            Entity.init();
            Entity.field("GIO_PK").setKeyValue(uid);
            Entity.delete();
        }
        return this.list();
    }
    
    @Override
    public String add() {
        this.addFormSingleEntry("GIO_PK", "0");
        this.addFormSingleEntry("GIO_TERMTYPE_NAME", "");
        this.addFormSingleEntry("GIO_ICON_PATHNAME", "");
        loadCBOGlossaries("");
        loadCBOAvailableIcons("");
        return super.add(); 
    }
    
    @Override
    public String update() {
        int uid = getIntArgumentValue("GIO_PK"); 
        
    	try {
            BOEntityReadWrite Entity = (BOEntityReadWrite)this.getBOFactory().getEntity("SRC_TERMTYPE");
            Entity.init();
            Entity.field("GIO_TERMTYPE_NAME").setValue(getStrArgumentValue("GIO_TERMTYPE_NAME"));
            Entity.field("GIO_ICON_PATHNAME").setValue(getStrArgumentValue("GIO_ICON_PATHNAME"));
            if (uid == 0) {
                Entity.field("GIO_PK").setNextIDValue();
                Entity.insert();
            } else {
                Entity.field("GIO_PK").setKeyValue(uid);
                Entity.update();
            } 
            
        } catch (Exception e) {
            Joy.log().error( e);
        }
        return this.list();
    }
    
    private void loadCBOAvailableIcons(String PKSelected) {
        List<JoyParameter> icons = Joy.parameters().getParameter("GlossaryAvailableIcons").getList();
        JoyFormVectorEntry columns = new JoyFormVectorEntry();
        
        for (JoyParameter param : icons) {
            columns.addValue("VALUE", param.getValue().toString());
            if (param.getValue().toString().equalsIgnoreCase(PKSelected))
                columns.setSelected(PKSelected);
        }
        this.addFormVectorEntry("GIO_ICON_PATHNAME", columns);
    }
    
    private void loadCBOGlossaries(String PKSelected) {
        JoyFormVectorEntry columns = new JoyFormVectorEntry();
        try {
            IEntity entity = this.getBOFactory().getEntity("DIM_GLOSSARY");
            entity.addSort("GLO_NAME");
            ResultSet rs = entity.selectAll();
            
            while (rs.next()) {
                columns.addValue("GLO_NAME", rs.getString("GLO_NAME"));
                if (rs.getString("GLO_NAME").equalsIgnoreCase(PKSelected))
                    columns.setSelected(PKSelected);
            }
            this.getBOFactory().closeResultSet(rs);

        } catch (SQLException e) {
            Joy.log().error( e);
        }
        this.addFormVectorEntry("GIO_TERMTYPE_NAME", columns);
    }
}
