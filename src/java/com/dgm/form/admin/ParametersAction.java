/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.form.admin;

import com.joy.mvc.actionTypes.ActionTypeForm;
import com.joy.bo.BOEntityReadWrite;
import java.sql.ResultSet;
import com.joy.bo.IEntity;


/**
 *
 * @author Benoit CAYLA benoit@famillecayla.fr
 */
public class ParametersAction extends ActionTypeForm {

    /**
     * Display parameter list
     * @return 
     */
    @Override
    public String list() {
        IEntity entity = this.getBOFactory().getEntity("APP_PARAMS");
        entity.init();
        entity.field("PARAM_DISPLAY").setKeyValue("Y");
        ResultSet rs = entity.selectFiltered();
        this.loadMatrix(rs, "LIST");
        this.getBOFactory().closeResultSet(rs);
        return super.list(); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * update a parameter into the DB
     * @return 
     */
    @Override
    public String update() {
        String param = this.getStrArgumentValue("PARAM_NAME");
        if (!param.isEmpty()) {
            String sVal = this.getStrArgumentValue("PARAM_STRVALUE");
            int iVal = this.getIntArgumentValue("PARAM_INTVALUE");
            
            // DB update
            IEntity entity = this.getBOFactory().getEntity("APP_PARAMS");
            entity.init();
            entity.field("PARAM_NAME").setKeyValue(param);
            entity.field("PARAM_LABEL").setValue(this.getIntArgumentValue("PARAM_LABEL"));
            entity.field("PARAM_DISPLAY").setValue(this.getStrArgumentValue("PARAM_DISPLAY"));
            entity.field("PARAM_STRVALUE").setValue(sVal);
            entity.field("PARAM_INTVALUE").setValue(iVal);
            entity.update();
        }
        return this.list();
    }

    @Override
    public String add() {
        this.addFormSingleEntry("PARAM_NAME", "");
        this.addFormSingleEntry("PARAM_LABEL", "");
        this.addFormSingleEntry("PARAM_INTVALUE", "");
        this.addFormSingleEntry("PARAM_STRVALUE", "");
        this.addFormSingleEntry("PARAM_DISPLAY", "Y");

        return super.add(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String edit() {
        String parameter = this.getStrArgumentValue("parameter");
        if (!parameter.isEmpty()) {
            IEntity entity = this.getBOFactory().getEntity("APP_PARAMS");
            entity.init();
            entity.field("PARAM_NAME").setKeyValue(parameter);
            ResultSet rs = entity.selectFiltered();
            this.loadSingle(rs);
            this.getBOFactory().closeResultSet(rs);
            
            return super.edit(); 
        } else {
            return this.edit();
        }
    }
    
}
