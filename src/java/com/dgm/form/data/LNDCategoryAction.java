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
public class LNDCategoryAction extends LNDCommonAction {

    public LNDCategoryAction() {
        this.LandingKeyName = "JOYFUNCKEY";
        this.LandingTableName = "LND_CATEGORY";
    }
    
    @Override
    public void updateSpecific(IEntity Entity) {
        Entity.field("CATEGORY_NAME").setValue(getStrArgumentValue("CATEGORY_NAME"));
        Entity.field("CATEGORY_DESCRIPTION").setValue(getStrArgumentValue("CATEGORY_DESCRIPTION"));
        Entity.field("CATEGORY_FULLPATH").setValue(getStrArgumentValue("CATEGORY_FULLPATH"));
        Entity.field("CATEGORY_PARENT_KEY").setValue(getStrArgumentValue("CATEGORY_PARENT_KEY"));
    }
    
    @Override
    public void editSpecific (ResultSet rs) { 
        try {
            loadCBOCategories(rs.getString("CATEGORY_PARENT_KEY"));
        } catch (SQLException ex) {
            Joy.log().error(ex);
        }
    }
    
    @Override
    public void addSpecific () { 
        loadCBOCategories("");
    }
    
    private void loadCBOCategories(String PKSelected) {
        try {
            // into the DIM table
            IEntity entity = this.getBOFactory().getEntity("DIM_CATEGORY");
            entity.addSort("CAT_NAME");
            ResultSet rs = entity.select();
            this.loadVector(rs, "CAT_ID",  "CAT_NAME", "CATEGORY_PARENT_KEY_CBO", PKSelected);
            this.getBOFactory().closeResultSet(rs);

        } catch (Exception e) {
            Joy.log().error( e);
        }
    }
}
