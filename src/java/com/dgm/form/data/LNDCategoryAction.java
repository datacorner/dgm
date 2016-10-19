/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.form.data;

import com.joy.Joy;
import com.joy.bo.BOEntityReadOnly;
import com.joy.bo.BOEntityReadWrite;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.joy.bo.IEntity;

/**
 *
 * @author benoit
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
            IEntity entity = this.getBOFactory().getEntity("Landing - List of available Category");
            entity.addSort("CAT_NAME");
            ResultSet rs = entity.selectAll();
            this.loadVector(rs, "CAT_ID",  "CAT_NAME", "CATEGORY_PARENT_KEY", PKSelected);
            this.getBOFactory().closeResultSet(rs);

        } catch (Exception e) {
            Joy.log().error( e);
        }
    }
}
