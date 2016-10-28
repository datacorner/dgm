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
import java.sql.ResultSet;
import java.sql.SQLException;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr)
 */
public class LNDTermAction extends LNDCommonAction {

    public LNDTermAction() {
        this.LandingKeyName = "JOYFUNCKEY";
        this.LandingTableName = "LND_TERM";
    }
    
    @Override
    public void editSpecific (ResultSet rs) { 
        try {
            loadCBOGlossaries(rs.getString("GLOSSARY_KEY"));
            loadCBOCategories(rs.getString("CATEGORY_KEY"));
        } catch (SQLException ex) {
            Joy.log().error(ex);
        }
    }
    
    @Override
    public void addSpecific () { 
        loadCBOGlossaries("");
        loadCBOCategories("");
    }
    
    @Override
    public void updateSpecific(IEntity Entity) {
        Entity.field("TERM_NAME").setValue(getStrArgumentValue("TERM_NAME"));
        Entity.field("TERM_STATUS").setValue(getStrArgumentValue("TERM_STATUS"));
        Entity.field("TERM_FUNCKEY").setValue(getStrArgumentValue("TERM_FUNCKEY"));
        Entity.field("TERM_DESCRIPTION").setValue(getStrArgumentValue("TERM_DESCRIPTION"));
        Entity.field("TERM_USAGE").setValue(getStrArgumentValue("TERM_USAGE"));
        Entity.field("TERM_EXAMPLE").setValue(getStrArgumentValue("TERM_EXAMPLE"));
        Entity.field("TERM_PHASE").setValue(getStrArgumentValue("TERM_PHASE"));
        Entity.field("TERM_OWNER").setValue(getStrArgumentValue("TERM_OWNER"));
        Entity.field("TERM_OWNER_EMAIL").setValue(getStrArgumentValue("TERM_OWNER_EMAIL"));
        Entity.field("TERM_STEWARD").setValue(getStrArgumentValue("TERM_STEWARD"));
        Entity.field("TERM_STEWARD_EMAIL").setValue(getStrArgumentValue("TERM_STEWARD_EMAIL"));
        Entity.field("GLOSSARY_KEY").setValue(getStrArgumentValue("GLOSSARY_KEY"));
        Entity.field("CATEGORY_KEY").setValue(getStrArgumentValue("CATEGORY_KEY"));
    }
    
    private void loadCBOGlossaries(String PKSelected) {
        try {
            // into the DIM table
            IEntity entity = this.getBOFactory().getEntity("DIM_GLOSSARY");
            entity.addSort("GLO_NAME");
            ResultSet rs = entity.select();
            this.loadVector(rs, "GLO_ID",  "GLO_NAME", "GLOSSARY_CBO", PKSelected);
            this.getBOFactory().closeResultSet(rs);

        } catch (Exception e) {
            Joy.log().error( e);
        }
    }
    
    private void loadCBOCategories(String PKSelected) {
        try {
            // into the DIM table
            IEntity entity = this.getBOFactory().getEntity("DIM_CATEGORY");
            entity.addSort("CAT_NAME");
            ResultSet rs = entity.select();
            this.loadVector(rs, "CAT_ID",  "CAT_NAME", "CATEGORY_CBO", PKSelected);
            this.getBOFactory().closeResultSet(rs);

        } catch (Exception e) {
            Joy.log().error( e);
        }
    }
}
