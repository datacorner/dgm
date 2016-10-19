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
public class LNDScorecardAction extends LNDCommonAction {

    public LNDScorecardAction() {
        this.LandingKeyName = "JOYFUNCKEY";
        this.LandingTableName = "LND_SCORECARD";
    }
    
    @Override
    public void updateSpecific(IEntity Entity) {
        Entity.field("SCO_NAME").setValue(getStrArgumentValue("SCO_NAME"));
        Entity.field("SCO_DESCRIPTION").setValue(getStrArgumentValue("SCO_DESCRIPTION"));
    }
}
