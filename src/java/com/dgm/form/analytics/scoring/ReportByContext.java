/*
 * Copyright (C) 2016 Benoit CAYLA <benoit@famillecayla.fr>
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
package com.dgm.form.analytics.scoring;

import com.dgm.form.analytics.ReportCommonConsolidated;

/**
 *
 * @author Benoit CAYLA <benoit@famillecayla.fr> 
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
