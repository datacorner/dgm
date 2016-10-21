/*
 * Copyright (C) 2016 Benoit CAYLA (benoit@famillecayla.fr)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.dgm.form.admin;

import com.joy.mvc.actionTypes.ActionTypeForm;
import java.sql.ResultSet;
import com.joy.bo.IEntity;


/**
 * This class manages the parameter screen
 * @author Benoit CAYLA (benoit@famillecayla.fr)
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
