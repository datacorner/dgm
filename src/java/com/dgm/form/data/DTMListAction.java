/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.form.data;


import com.joy.bo.BOEntityReadOnly;
import com.joy.mvc.actionTypes.ActionTypeForm;
import java.sql.ResultSet;
import com.joy.bo.IEntity;

/**
 *
 * @author benoit
 */
public class DTMListAction extends ActionTypeForm {

    /**
     * Load the Datamart data : "FRS_PK", "FRS_VALID_ROWS", "FRS_INVALID_ROWS","FRS_TOTALROWS", "FRS_KPI_SCORE", 
     *                          "DQX_NAME", "TRM_NAME", "FRS_WEIGHT", "FRS_COST", "MET_NAME", "TRM_FK", "DQX_CODE",
     *                          "MET_FK", "FRS_DATETIME_LOAD", "SCG_NAME", "SCO_NAME", "TIM_CALENDAR_DATE"
     * @return 
     */
    @Override
    public String list() {
        IEntity entity;
        int i = this.getIntArgumentValue("LIMIT");
        
        // Load the data table result
        if (this.getStrArgumentValue("list").equalsIgnoreCase("LAST")) 
            entity = this.getBOFactory().getEntity("Last Facts Only with details");
        else 
            entity = this.getBOFactory().getEntity("All Facts with details");
        entity.addSort("TIM_CALENDAR_DATE DESC", "MET_NAME");
        if (i != 0)
            entity.setLimitRecords(i);
        
        ResultSet rs = entity.selectAll();
        this.loadMatrix(rs, "LIST");
        this.getBOFactory().closeResultSet(rs);
        this.addFormSingleEntry("LIMIT", i);
        
        return super.list(); 
    }

}
