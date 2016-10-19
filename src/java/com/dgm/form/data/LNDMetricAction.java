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
public class LNDMetricAction extends LNDCommonAction {

    public LNDMetricAction() {
        this.LandingKeyName = "JOYFUNCKEY";
        this.LandingTableName = "LND_METRIC";
    }
    
    @Override
    public void updateSpecific(IEntity Entity) {
        Entity.field("MET_NAME").setValue(getStrArgumentValue("MET_NAME"));
        Entity.field("SCORECARD_KEY").setValue(getStrArgumentValue("SCORECARD_KEY"));
        Entity.field("SCORECARDGRP_KEY").setValue(getStrArgumentValue("SCORECARDGRP_KEY"));
        Entity.field("MET_SCORE").setValue(getStrArgumentValue("MET_SCORE"));
    }
}
