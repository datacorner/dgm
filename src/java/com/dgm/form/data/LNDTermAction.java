/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.form.data;

import com.joy.Joy;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.joy.bo.IEntity;
import com.joy.bo.IEntity;

/**
 *
 * @author benoit
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
        Entity.field("GLOSSARY_KEY").setValue(getStrArgumentValue("GLOSSARY"));
        Entity.field("CATEGORY_KEY").setValue(getStrArgumentValue("CATEGORY"));
    }
    
    private void loadCBOGlossaries(String PKSelected) {
        try {
            // into the DIM table
            IEntity entity = this.getBOFactory().getEntity("Landing - List of available Glossary");
            entity.addSort("GLO_NAME");
            ResultSet rs = entity.selectAll();
            this.loadVector(rs, "GLO_ID",  "GLO_NAME", "GLOSSARY", PKSelected);
            this.getBOFactory().closeResultSet(rs);

        } catch (Exception e) {
            Joy.log().error( e);
        }
    }
    
    private void loadCBOCategories(String PKSelected) {
        try {
            // into the DIM table
            IEntity entity = this.getBOFactory().getEntity("Landing - List of available Category");
            entity.addSort("CAT_NAME");
            ResultSet rs = entity.selectAll();
            this.loadVector(rs, "CAT_ID",  "CAT_NAME", "CATEGORY", PKSelected);
            this.getBOFactory().closeResultSet(rs);

        } catch (Exception e) {
            Joy.log().error( e);
        }
    }
}
