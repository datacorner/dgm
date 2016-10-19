/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.form.data;

import com.joy.bo.IEntity;

/**
 *
 * @author benoit
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
    
}
