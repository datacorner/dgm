/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.form.analytics.bg;

import com.dgm.form.analytics.ReportCommonConsolidated;


/**
 *
 * @author benoit
 */
public class ReportByGlossary extends ReportCommonConsolidated {

    public ReportByGlossary() {
        // Needed for the common functions
        this.dimFieldKey = "GLO_PK";
        this.dimFieldName = "GLO_NAME";
        this.dimTableName = "DIM_GLOSSARY";
        this.tagDimensionRequest = "glossary";
    }
    
    @Override
    public String display(int Key) {
        // Global data
        loadGlobalData(Key);
        // Load trends value and radar data 
        loadDQVectorsValAndTrends(Key, "Analytics - Glossary Last Runs", "GLO_FK");
        // Metrics list
        loadMetricTableList(Key, this.dimFieldKey); 
        // Terms list
        loadTermsList(Key, "GLO_FK", "Analytics - Glossary Term List");
        // Category list
        loadCategoryList(Key, "GLO_FK", "Analytics - Glossary Category List");
        
        return super.display(Key); 
    }
    
}
