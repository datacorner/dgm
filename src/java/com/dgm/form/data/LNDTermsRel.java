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
import com.joy.bo.IEntity;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr)
 */
public class LNDTermsRel extends LNDCommonAction {

    public LNDTermsRel() {
        this.LandingKeyName = "JOYFUNCKEY";
        this.LandingTableName = "LND_TERM_RELATIONSHIPS";
    }
    
    @Override
    public void updateSpecific(IEntity Entity) {
        Entity.field("REL_KEY_TERM_SOURCE").setValue(getStrArgumentValue("REL_KEY_TERM_SOURCE"));
        Entity.field("REL_KEY_TERM_TARGET").setValue(getStrArgumentValue("REL_KEY_TERM_TARGET"));
        Entity.field("REL_NAME").setValue(getStrArgumentValue("REL_NAME"));
        Entity.field("REL_DESCRIPTION").setValue(getStrArgumentValue("REL_DESCRIPTION"));
    }
    
    @Override
    public void editSpecific (ResultSet rs) { 
        try {
            loadCBOTerm(rs.getString("rel_key_term_source"), "TERM_CBO_SOURCE");
            loadCBOTerm(rs.getString("rel_key_term_target"), "TERM_CBO_TARGET");
        } catch (SQLException ex) {
            Joy.log().error(ex);
        }
    }
    
    @Override
    public void addSpecific () { 
        loadCBOTerm("", "TERM_CBO_SOURCE");
        loadCBOTerm("", "TERM_CBO_TARGET");
    }
    
    private void loadCBOTerm(String PKSelected, String cboName) {
        try {
            // into the DIM table
            IEntity entity = this.getBOFactory().getEntity("DIM_TERM");
            entity.addSort("TRM_NAME");
            ResultSet rs = entity.select();
            this.loadVector(rs, "TRM_FUNCKEY",  "TRM_NAME", cboName, PKSelected);
            this.getBOFactory().closeResultSet(rs);

        } catch (Exception e) {
            Joy.log().error( e);
        }
    }
    
}
