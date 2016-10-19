/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.form.analytics.scoring;

import com.dgm.form.analytics.ReportCommonConsolidated;
import java.sql.ResultSet;
import com.joy.bo.IEntity;

/**
 *
 * @author benoit
 */
public class ReportByMetric extends ReportCommonConsolidated {
    
    public ReportByMetric() {
        // Needed for the common functions
        this.dimFieldKey = "MET_PK";
        this.dimFieldName = "MET_NAME";
        this.dimTableName = "DIM_METRIC";
        this.tagDimensionRequest = "metric";
    }

    @Override
    public String display(int Key) {
        // Global data
        loadGlobalData(Key);
        // Current Value 
        loadCurrentValue(Key);
        // Last runs
        loadBarLastRunsOnly(Key, "MET_FK", "Analytics - Metric Last Runs");
        
        return super.display(Key); 
    }

    @Override
    /**
     * load these fields :"MET_PK", "MET_NAME", "MET_DESCRIPTION", "MET_WEIGHT", "MTY_NAME", "SCG_NAME", "SCO_NAME", 
     *                    "SCO_DESCRIPTION", "SCO_LASTRUN", "SCO_PROJECT", "SCO_FULLPATH", "TRM_NAME", "TRM_PK"
     */
    protected void loadGlobalData(int Key) {
        IEntity entity = getBOFactory().getEntity("Analytics - Metric Global");
        if (Key != 0)
            entity.field("MET_PK").setKeyValue(Key);
        ResultSet rs = entity.selectFiltered();
        this.loadSingle(rs);
        getBOFactory().closeResultSet(rs);
    }

    /**
     * Load the last matric value
     * @param Key 
     */
    private void loadCurrentValue(int Key) {
        IEntity entity = getBOFactory().getEntity("Last Facts Only"); //LASTONLY_ALL_SCORECARDS");
        if (Key != 0)
            entity.field("MET_FK").setKeyValue(Key);
        entity.useOnlyTheseFields("FRS_VALID_ROWS", "FRS_INVALID_ROWS","FRS_TOTALROWS","FRS_KPI_SCORE", "FRS_WEIGHT", "FRS_COST");
        ResultSet rs = entity.selectFiltered();
        this.loadSingle(rs);
        getBOFactory().closeResultSet(rs);
    }
    
}
