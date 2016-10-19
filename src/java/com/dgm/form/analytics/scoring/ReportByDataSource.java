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
public class ReportByDataSource extends ReportCommonConsolidated {

    public ReportByDataSource() {
        // Needed for the common functions
        this.dimFieldKey = "DSO_PK";
        this.dimFieldName = "DSO_SOURCENAME";
        this.dimTableName = "DIM_DATASOURCE";
        this.tagDimensionRequest = "ds";
    }

    @Override
    public String display(int Key) {
        // Global data
        loadGlobalData(Key);
        // Metrics list
        loadMetricTableList(Key, "DSO_FK"); 
        // Terms list
        loadTermsList(Key, "DSO_FK", "Analytics - DataSource Term List");
        // Load trends value and radar data 
        loadDQVectorsValAndTrends(Key, "Analytics - DataSource Last Runs", "DSO_FK");  
        return super.display(Key); //To change body of generated methods, choose Tools | Templates.
    }
    
}
