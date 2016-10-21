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
package com.dgm.rest.actions;

import com.dgm.rest.RESTDataCommon;
import com.joy.Joy;
import com.joy.charts.chartjs.ChartWithDataset;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr)
 * P1: type de chart (polar, bar, line, donut, pie)
 * P2: nom de la query
 * P3 ... PX : parametres de la query --> par paire : (NOM Colonne, valeur colonne)
 
 Pour polar (SQL)
  field 1 : libellé (string)
  field 2 : valeur (numérique)
 */
public class RESTChart extends RESTDataCommon {

    @Override
    public String restGet() {
        switch (this.getStrArgumentValue("P1").toUpperCase()) {
            case "SDS": // Single DataSet
                return getSingleDatasetData();
            case "MDS": // Multiple DataSets
                return getMultipleDatasetsData();
            default:
                return "";
        }
    }
    
    /**
     * Return the data for multiple datasets request
     * P2 contains the Query reference (entity)
     * SQL query must return 3 columns :
     *  Col 1 : label
     *  Col 2 : dataset
     *  Col 3 : value (numeric)
     * @return 
     */
    private String getMultipleDatasetsData() {
        try {
            IEntity entity = this.getEntityFromPOST(2);
            if (entity == null) return "";
            ResultSet rs = entity.selectFiltered();

            ChartWithDataset chartbar = new ChartWithDataset(Joy.parameters().getParameter("ChartsColors").getList(), Joy.parameters().getParameter("transparency").getValue().toString());   
            while (rs.next()) {
                chartbar.add(rs.getString(1), 
                             rs.getString(2), 
                             rs.getFloat(3));
            }
            getBOFactory().closeResultSet(rs);
            return chartbar.getChartData();
            
        } catch (SQLException e) {
            Joy.log().error(e);
        }
        return "";
    }
    
    /**
     * Return the data for single dataset request
     * P2 contains the Query reference (entity)
     * SQL query must return 2 columns :
     *  Col 1 : label
     *  Col 2 : Value (numeric)
     * @return 
     */
    private String getSingleDatasetData() {
        if (this.getStrArgumentValue("P2").isEmpty())
            return "";
        
        ChartWithDataset chart = new ChartWithDataset(Joy.parameters().getParameter("ChartsColors").getList(), 
                                                      Joy.parameters().getParameter("transparency").getValue().toString());
        IEntity entity = this.getEntityFromPOST(2);
        if (entity == null) return "";
        ResultSet rs = entity.selectFiltered();
        
        try {
            while (rs.next())
                chart.add(rs.getString(1), "data", rs.getFloat(2));
            this.getBOFactory().getDB().closeResultSet(rs);
            return chart.getChartData();
            
        } catch (Exception ex) {
            Joy.log().error(ex);
            this.getBOFactory().getDB().closeResultSet(rs);
        }
        return "";
    }
    
}
