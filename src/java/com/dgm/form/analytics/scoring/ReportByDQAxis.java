/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.form.analytics.scoring;

import com.dgm.form.analytics.ReportCommonConsolidated;

/**
 *
 * @author benoit
 */
public class ReportByDQAxis extends ReportCommonConsolidated {
    
    public ReportByDQAxis() {
        // Needed for the common functions
        this.dimFieldKey = "DQX_PK";
        this.dimFieldName = "DQX_NAME";
        this.dimTableName = "DIM_DQAXIS";
        this.tagDimensionRequest = "dqaxis";
    }

    @Override
    public String display(int Key) {
        // Global data
        loadGlobalData(Key);
        // Metrics list
        loadMetricTableList(Key, "DQX_FK"); 
        
        return super.display(Key); //To change body of generated methods, choose Tools | Templates.
    }
}
