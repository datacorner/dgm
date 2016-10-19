/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.tasks.load;

import com.joy.tasks.JoyTaskStatus;


/**
 *
 * @author Administrator
 */
public class TASKInfaWorkflow extends TASKCommonLoad {

    @Override
    public JoyTaskStatus taskExecute() {
        JoyTaskStatus infaStatus = this.loadInformatica();
        if (infaStatus == JoyTaskStatus.Success) {
            this.addTrace("Load Informatica Data into the Datamart");
            return this.loadInternalLanding();
        } else {
            this.addTrace("Error while getting information from Informatica Platform");
            return infaStatus;
        }
    }
    
}
