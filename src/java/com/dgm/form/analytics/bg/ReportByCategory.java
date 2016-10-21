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
package com.dgm.form.analytics.bg;

import com.dgm.form.analytics.ReportCommonConsolidated;
import com.joy.Joy;
import com.joy.mvc.formbean.JoyFormMatrixEntry;
import com.joy.mvc.formbean.JoyFormVectorEntry;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr) 
 */
public class ReportByCategory extends ReportCommonConsolidated {
    
    public ReportByCategory() {
        // Needed for the common functions
        this.dimFieldKey = "CAT_PK";
        this.dimFieldName = "CAT_NAME";
        this.dimTableName = "DIM_CATEGORY";
        this.tagDimensionRequest = "category";
    }
    
    @Override
    public String display(int Key) {
        // Global data
        loadGlobalData(Key);
        // Metrics list
        loadMetricTableList(Key, this.dimFieldKey); 
        // Load trends value and radar data 
        loadDQVectorsValAndTrends(Key, "Analytics - Category Last Runs", "CAT_FK");        
        // Terms list
        loadTermsList(Key, "CAT_FK", "Analytics - Category Term List");
        // Parents Category
        loadParentsCategory(Key);
        // Childs categories
        loadChildsCategory(Key);
        // Glossary
        loadGlossary(Key);
        
        return super.display(Key); 
    }
    
    /**
     * Retrieve the current glossary who own the category
     * @param Key category key
     */
    private void loadGlossary(int Key) {
        try {
            IEntity entity = getBOFactory().getEntity("Analytics - Category Glossary List");
            if (Key != 0)
                entity.field("CAT_PK").setKeyValue(Key);
            ResultSet rs = entity.selectFiltered();

            if (rs.next()) {
                this.addFormSingleEntry("CAT_PK", rs.getString("GLO_NAME"));
                this.addFormSingleEntry("GLO_DESCRIPTION", rs.getString("GLO_DESCRIPTION"));
                this.addFormSingleEntry("GLO_PK", rs.getInt("GLO_PK"));
                this.addFormSingleEntry("GLO_LINK", Joy.href("byglossary", "display", rs.getString("GLO_DESCRIPTION"), "glossary", rs.getString("GLO_PK")));
            }
            getBOFactory().closeResultSet(rs);
            
        } catch (SQLException e) {
            Joy.log().error(e);
        }
    }
    
    /**
     * Load the parents categories
     * @param Key 
     */
    private void loadParentsCategory(int Key) {
        JoyFormMatrixEntry matrix = new JoyFormMatrixEntry();
        try {
            IEntity entity = getBOFactory().getEntity("Analytics - Category Parent List");
            if (Key != 0)
                entity.field("CAT_FILTERING").setKeyValue(Key);
            else {
                entity.field("CAT_FILTERING").doNotUseThisField();
                entity.setDistinct(true);
            }
            ResultSet rs = entity.selectFiltered();

            while (rs.next()) {
                JoyFormVectorEntry columns = new JoyFormVectorEntry();
                columns.addValue("CAT_PK", rs.getInt("CAT_PK")); 
                columns.addValue("CAT_NAME", rs.getString("CAT_NAME"));
                columns.addValue("CAT_ID", rs.getString("CAT_ID"));
                columns.addValue("CAT_PARENT_ID", rs.getString("CAT_PARENT_ID"));
                columns.addValue("CAT_DATETIME_LOAD", rs.getDate("CAT_DATETIME_LOAD"));
                columns.addValue("CAT_DESCRIPTION", rs.getString("CAT_DESCRIPTION"));
                matrix.addRow(columns);
            }
            getBOFactory().closeResultSet(rs);
            
        } catch (SQLException e) {
            Joy.log().error(e);
        }
        this.addFormMatrixEntry("PARENTS", matrix);
    }

    /**
     * Load the childs categories
     * @param Key 
     */
    private void loadChildsCategory(int Key) {
        JoyFormMatrixEntry matrix = new JoyFormMatrixEntry();
        try {
            IEntity entity = getBOFactory().getEntity("Analytics - Category Child List");
            if (Key != 0)
                entity.field("CAT_FILTERING").setKeyValue(Key);
            else {
                entity.field("CAT_FILTERING").doNotUseThisField();
                entity.setDistinct(true);
            }
            ResultSet rs = entity.selectFiltered();

            while (rs.next()) {
                JoyFormVectorEntry columns = new JoyFormVectorEntry();
                columns.addValue("CAT_PK", rs.getInt("CAT_PK")); 
                columns.addValue("CAT_NAME", rs.getString("CAT_NAME"));
                columns.addValue("CAT_ID", rs.getString("CAT_ID"));
                columns.addValue("CAT_PARENT_ID", rs.getString("CAT_PARENT_ID"));
                columns.addValue("CAT_DATETIME_LOAD", rs.getDate("CAT_DATETIME_LOAD"));
                columns.addValue("CAT_DESCRIPTION", rs.getString("CAT_DESCRIPTION"));
                if (rs.getString("ASLINK").equalsIgnoreCase("Y")) 
                    columns.addValue("LINK", Joy.href("bycategory", "display",  rs.getString("CAT_PK"), rs.getString("CAT_NAME")));
                else
                    columns.addValue("LINK", rs.getString("CAT_NAME"));
                matrix.addRow(columns);
            }
            getBOFactory().closeResultSet(rs);

        } catch (SQLException e) {
            Joy.log().error(e);
        }
        this.addFormMatrixEntry("CHILDS", matrix);
    }
}
