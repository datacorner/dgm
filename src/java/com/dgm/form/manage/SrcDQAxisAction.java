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
import com.joy.mvc.actionTypes.ActionTypeForm;
import com.joy.bo.BOEntityReadWrite;
import com.joy.mvc.formbean.JoyFormMatrixEntry;
import com.joy.mvc.formbean.JoyFormVectorEntry;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr) CAYLA
 */
public class SrcDQAxisAction extends ActionTypeForm {

    @Override
    public String list() {
        JoyFormMatrixEntry matrix = new JoyFormMatrixEntry();
        try {
            IEntity Entity = this.getBOFactory().getEntity("SRC_DQAXIS");
            ResultSet rs = Entity.select();
            while (rs.next()) {
                JoyFormVectorEntry columns = new JoyFormVectorEntry();
                columns.addValue("DQX_PK", rs.getInt("DQX_PK"));
                columns.addValue("DQX_DESCRIPTION", rs.getString("DQX_DESCRIPTION"));
                columns.addValue("DQX_LABEL", rs.getString("DQX_LABEL"));
                columns.addValue("DQX_CODE", rs.getString("DQX_CODE"));
                columns.addValue("DQX_WEIGHT", rs.getString("DQX_WEIGHT"));
                matrix.addRow(columns);
            }
            this.addFormMatrixEntry("LIST", matrix);
            this.getBOFactory().closeResultSet(rs);
        } catch (SQLException e) {
            Joy.log().error(  e);
        }
        return super.list(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String edit() {
        String uid = getStrArgumentValue("DQX_PK");
        if (!uid.equalsIgnoreCase("")) {
            try {
                IEntity Entity = this.getBOFactory().getEntity("SRC_DQAXIS");
                Entity.reset();
                Entity.field("DQX_PK").setKeyValue(Integer.valueOf(uid));
                ResultSet rs = Entity.select();

                if (rs.next()) {
                    this.addFormSingleEntry("DQX_PK", rs.getString("DQX_PK"));
                    this.addFormSingleEntry("DQX_DESCRIPTION", rs.getString("DQX_DESCRIPTION"));
                    this.addFormSingleEntry("DQX_LABEL", rs.getString("DQX_LABEL"));
                    this.addFormSingleEntry("DQX_CODE", rs.getString("DQX_CODE"));
                    this.addFormSingleEntry("DQX_WEIGHT", rs.getString("DQX_WEIGHT"));
                }
                this.getBOFactory().closeResultSet(rs);

            } catch (SQLException e) {
                Joy.log().error(  e);
            }
        }
        return super.edit(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String other() {
        return this.list();
    }

    @Override
    public String add() {
        this.addFormSingleEntry("DQX_PK", "0");
        this.addFormSingleEntry("DQX_DESCRIPTION", "");
        this.addFormSingleEntry("DQX_LABEL", "");
        this.addFormSingleEntry("DQX_CODE", "");
        this.addFormSingleEntry("DQX_WEIGHT", "1");
        return super.add();
    }

    /**
     * Retourne le label DQ Axis à partir de sa PK
     * @param DQX_PK
     * @return DQX_LABEL
     */
    private String getLabelFromID(int ID) {
        String ret = "";
        try {
            IEntity Entity = this.getBOFactory().getEntity("SRC_DQAXIS");
            Entity.reset();
            Entity.field("DQX_PK").setKeyValue(ID);
            ResultSet rs = Entity.select();
            if (rs.next()) {
                ret = rs.getString("DQX_LABEL");
            }
            this.getBOFactory().closeResultSet(rs);
            
        } catch (SQLException ex) {
            Joy.log().error( ex);
        }
        return ret;
    }
    
    /**
     * retire dans REL_TERM_METRIC les référence à cette dimension
     * @param PK DQX_PK
     */
    private void removeDQAxisReferences(int PK) {
        String label = getLabelFromID(PK);
        
        if (!label.isEmpty()) {
            BOEntityReadWrite entRel = (BOEntityReadWrite)this.getBOFactory().getEntity("REL_TERM_METRIC");
            entRel.field("DQX_NAME").setKeyValue(label);
            entRel.delete();
        }
    }
    
    /**
     * Ajoute dans REL_TERM_METRIC les référence à cette dimension pour chaque TRM_CLUSTER_ID
     * @param Label DQX_NAME
     */
    private void addDQAxisReferences(String label) {
        if (!label.isEmpty()) {
            try {
                IEntity entListCluster = this.getBOFactory().getEntity("REL_TERM_METRIC");
                entListCluster.useNoFields();
                entListCluster.field("TRM_CLUSTER_ID").useThisField();
                entListCluster.field("TRM_NAME").useThisField();
                entListCluster.setDistinct(true);
                ResultSet rs = entListCluster.select();
                while (rs.next()) { // parcours les cluster id pour rajouter le nouvel axe
                    BOEntityReadWrite entNew = (BOEntityReadWrite)this.getBOFactory().getEntity("REL_TERM_METRIC");
                    entNew.reset();
                    entNew.field("TMD_PK").setNextIDValue();
                    entNew.field("DQX_NAME").setValue(label);
                    entNew.field("TRM_CLUSTER_ID").setValue(rs.getInt("TRM_CLUSTER_ID"));
                    entNew.field("TRM_NAME").setValue(rs.getString("TRM_NAME"));
                    entNew.insert();
                }
                this.getBOFactory().closeResultSet(rs);
            } catch (SQLException ex) {
                Joy.log().error( ex);
            }
        }
    }
    
    /**
     * Check if removing a DQAxis is possible (not used in DIM_DQAXIS)
     * @return 
     */
    private boolean canDelete(int id) {
        try {
            if (id <= 0) return false;
            IEntity Entity = this.getBOFactory().getEntity("DQ Axis In DTM Scope");
            Entity.field("DQX_PK").setKeyValue(id);
            ResultSet rs = Entity.select();
            if (rs.next()) {
                this.addDisplayMessageError("03_DELETE_DQAXIS_KO");
                return false;
            }
            this.getBOFactory().closeResultSet(rs);
            return true;
            
        } catch (SQLException ex) {
            Joy.log().error(ex);
            return false;
        }
    }
    
    @Override
    public String delete() {
        int uid = getIntArgumentValue("DQX_PK");
        if (uid != 0 && canDelete(uid)) {
            BOEntityReadWrite Entity = (BOEntityReadWrite)this.getBOFactory().getEntity("SRC_DQAXIS");
            Entity.reset();
            Entity.field("DQX_PK").setKeyValue(uid);
            removeDQAxisReferences(uid);
            Entity.delete();
            this.addDisplayMessageError("03_DELETE_DQAXIS_OK");
        }
        return this.list();
    }

    /**
     * update the SRC_DQAXIS table, updates also DIM_DQAXIS but only for the WEIGHT and name field
     * @return 
     */
    @Override
    public String update() {
        int uid = getIntArgumentValue("DQX_PK"); 
        
    	try {
            // update SRC_DQAXIS table
            BOEntityReadWrite Entity = (BOEntityReadWrite)this.getBOFactory().getEntity("SRC_DQAXIS");
            Entity.field("DQX_STATUS").doNotUseThisField();
            Entity.field("DQX_DESCRIPTION").setValue(getStrArgumentValue("DQX_DESCRIPTION"));
            Entity.field("DQX_LABEL").setValue(getStrArgumentValue("DQX_LABEL"));
            Entity.field("DQX_CODE").setValue(getStrArgumentValue("DQX_CODE"));
            Entity.field("DQX_WEIGHT").setValue(getStrArgumentValue("DQX_WEIGHT"));
            if (uid == 0) {
                Entity.field("DQX_PK").setNextIDValue();
                Entity.insert();
                addDQAxisReferences(getStrArgumentValue("DQX_LABEL"));
            } else {
                Entity.field("DQX_PK").setKeyValue(uid);
                Entity.update();
            } 
            
            // update DIM_DQAXIS
            Entity = (BOEntityReadWrite)this.getBOFactory().getEntity("DIM_DQAXIS");
            Entity.doNotUseTheseFields("DQX_PK", "DQX_STATUS");
            Entity.field("DQX_CODE").setKeyValue(getStrArgumentValue("DQX_CODE"));
            Entity.field("DQX_WEIGHT").setValue(getStrArgumentValue("DQX_WEIGHT"));
            Entity.field("DQX_NAME").setValue(getStrArgumentValue("DQX_LABEL"));
            Entity.field("DQX_DATETIME_LOAD").setValue(new Date());
            Entity.update();
            
        } catch (Exception e) {
            Joy.log().error( e);
        }
        return this.list();
    }

}
