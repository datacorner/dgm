/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.tasks.load;

import com.dgm.common.providers.ParamProvider;
import com.joy.Joy;
import com.joy.mvc.actionTypes.ActionTypeTASK;
import com.joy.tasks.JoyTaskStatus;

/**
 *
 * @author Administrator
 */
public class TASKInfaSCRefresh extends ActionTypeTASK {

    @Override
    public JoyTaskStatus taskExecute() {
        try {
            ParamProvider myParams = new ParamProvider(this.getEntities());
            String scName = this.getTaskName();
            String result = "";
            Joy.log().info("Informatica Scorecard Refresh : " + scName);

            String cmd = "";
            cmd += "infacmd.bat ps Execute";
            cmd += " -dn " + myParams.getParamValue("infadomain").getStrValue();      
            cmd += " -dsn " + myParams.getParamValue("infadis").getStrValue();
            cmd += " -msn " + myParams.getParamValue("infamrs").getStrValue();
            cmd += " -ot scorecard";
            cmd += " -un " + myParams.getParamValue("infauser").getStrValue();
            cmd += " -pd " + myParams.getParamValue("infapwd").getStrValue();   
            cmd += " -opn " + scName;
            cmd += " -w true";
            
            result = Joy.executeCommandLine(cmd);
            this.setMessage(result);
            
        } catch (Exception e) {
            Joy.log().fatal(e);
            this.setMessage(e.toString());
            return JoyTaskStatus.Failed;
        }
        return JoyTaskStatus.Success;
    }
    
}
