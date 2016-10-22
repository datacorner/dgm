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
package com.dgm.form.data;

import com.joy.mvc.actionTypes.ActionTypeForm;
import java.sql.ResultSet;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr)
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
        
        entity.resetFilters();
        ResultSet rs = entity.select();
        this.loadMatrix(rs, "LIST");
        this.getBOFactory().closeResultSet(rs);
        this.addFormSingleEntry("LIMIT", i);
        
        return super.list(); 
    }

}
