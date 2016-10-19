/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.form.load;

import com.joy.mvc.actionTypes.ActionTypeForm;
import com.dgm.common.providers.ParamProvider;

/**
 *
 * @author Benoit CAYLA benoit@famillecayla.fr
 */
public class InfaConfigInterface extends ActionTypeForm {
    @Override
    public String list() {
        ParamProvider params = new ParamProvider(this.getBOFactory());
        this.addFormSingleEntry("CMD", params.getParamValue("infacmd").getStrValue());
        this.addFormSingleEntry("DOMAIN", params.getParamValue("infadomain").getStrValue());
        this.addFormSingleEntry("DIS", params.getParamValue("infadis").getStrValue());
        this.addFormSingleEntry("USER", params.getParamValue("infauser").getStrValue());
        this.addFormSingleEntry("PWD", params.getParamValue("infapwd").getStrValue());
        this.addFormSingleEntry("APP", params.getParamValue("infaapp").getStrValue());
        return super.list();
    }

}
