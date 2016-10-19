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
public class ReportByContext extends ReportCommonConsolidated {

    public ReportByContext() {
        // Needed for the common functions
        this.dimFieldKey = "CON_PK";
        this.dimFieldName = "CON_DESCRIPTION";
        this.dimTableName = "DIM_CONTEXT";
        this.tagDimensionRequest = "context";
    }

    @Override
    public String display(int Key) {
        // Global data
        loadGlobalData(Key);
        // Metrics list
        loadMetricTableList(Key, "CON_FK"); 
        // Load trends value and radar data 
        loadDQVectorsValAndTrends(Key, "Analytics - Context Last Runs", "CON_FK");  
        // Terms list
        loadTermsList(Key, "CON_FK", "Analytics - Context Term List");
        
        return super.display(Key); //To change body of generated methods, choose Tools | Templates.
    }
    
}
