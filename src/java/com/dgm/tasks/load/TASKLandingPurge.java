/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.tasks.load;

import com.dgm.common.providers.ParamProvider;
import com.joy.Joy;
import com.joy.tasks.JoyTaskStatus;

/**
 *
 * @author Administrator
 */
public class TASKLandingPurge extends TASKCommonRAZ {

    @Override
    protected void init() {
        super.init();
    }
    
    @Override
    public JoyTaskStatus taskExecute() {
        try {
            ParamProvider myParams = new ParamProvider(this.getEntities());
            
            // vide les tables
            landingpurge();
            
            super.taskExecute();
            this.setMessage("Landing tables purged.");
            
        } catch (Exception e) {
            Joy.log().fatal(e);
            this.setMessage(e.toString());
            return JoyTaskStatus.Failed;
        }
        return JoyTaskStatus.Success;
    }
    
}
