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
import java.sql.ResultSet;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA <benoit@famillecayla.fr> CAYLA <benoit@famillecayla.fr>
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
