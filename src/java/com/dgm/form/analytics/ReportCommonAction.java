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
package com.dgm.form.analytics;

import com.dgm.common.Utils;
import com.joy.Joy;
import com.joy.charts.chartjs.ChartWithDataset;
import com.joy.charts.gaugejs.ChartCounterData;
import com.joy.mvc.actionTypes.ActionTypeForm;
import com.joy.mvc.formbean.JoyFormMatrixEntry;
import com.joy.mvc.formbean.JoyFormVectorEntry;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr)
 */
public class ReportCommonAction extends ActionTypeForm {
    private static final String TAG_MATRIX_VALUE = "COUNTER_LIST";
    private static final String TAG_COUNTER_NAME = "COUNTER_NAME";
    private static final String TAG_COUNTER_OBJECT = "COUNTER_OBJECT";
    private static final String TAG_TRENDS_LIST = "TENDANCE_LIST";
    private static final String TAG_LASTRUNS = "LASTRUNS";
    private static final String TAG_MULTIPLE_RADAR = "MULTIPLE_RADAR";
    private static final String TAG_TREND_NAME = "TREND";
    /**
     * Build the metric list 
     * @param KeyValue
     * @param KeyName
     */
    protected void loadMetricTableList(int KeyValue, String KeyName) {
        JoyFormMatrixEntry matrix = new JoyFormMatrixEntry();
        
        try {
            IEntity entity = getBOFactory().getEntity("Last Facts Only with details");
            entity.field(KeyName).setKeyValue(KeyValue);
            entity.addFilter("TRM_FK <> 0");
            ResultSet rs = entity.select();

            while (rs.next()) {
                JoyFormVectorEntry columns = new JoyFormVectorEntry();
                columns.addValue("FRS_PK", rs.getString("FRS_PK"));
                columns.addValue("FRS_VALID_ROWS", rs.getString("FRS_VALID_ROWS"));
                columns.addValue("FRS_INVALID_ROWS", rs.getString("FRS_INVALID_ROWS"));
                columns.addValue("FRS_TOTALROWS", rs.getString("FRS_TOTALROWS"));
                columns.addValue("FRS_KPI_SCORE", Utils.scoreDisplay(rs.getFloat("FRS_KPI_SCORE")));
                columns.addValue("DQX_NAME", rs.getString("DQX_NAME"));
                columns.addValue("TRM_NAME", rs.getString("TRM_NAME"));
                columns.addValue("FRS_WEIGHT", rs.getString("FRS_WEIGHT"));
                columns.addValue("FRS_COST", rs.getString("FRS_COST"));
                columns.addValue("MET_NAME", rs.getString("MET_NAME"));
                columns.addValue("TRM_FK", rs.getString("TRM_FK"));
                columns.addValue("DQX_CODE", rs.getString("DQX_CODE"));
                columns.addValue("MET_FK", rs.getString("MET_FK"));
                columns.addValue("FRS_DATETIME_LOAD", rs.getString("FRS_DATETIME_LOAD"));
                columns.addValue("SCG_NAME", rs.getString("SCG_NAME"));
                columns.addValue("SCO_NAME", rs.getString("SCO_NAME"));
                
                columns.addValue("METRIC_LINK", Joy.href("bymetric", "display", rs.getString("MET_NAME"), "metric",  rs.getString("MET_FK")));
                columns.addValue("AXIS_LINK", Joy.href("bydqaxis", "display", rs.getString("DQX_NAME"), "dqaxis",  rs.getString("DQX_FK")));
                columns.addValue("TERM_LINK", Joy.url("byterm", "display", "term",  rs.getString("TRM_FK")));
                columns.addValue("SCORECARD_REF", (rs.getString("SCO_NAME")==null ? "N.A." : rs.getString("SCO_NAME") + "/" + rs.getString("SCG_NAME")));

                matrix.addRow(columns);
            }
            getBOFactory().closeResultSet(rs);

        } catch (SQLException e) {
            Joy.log().error( e);
        }
        this.addFormMatrixEntry("METRIC_LIST", matrix);
    }
    
    /**
     * Load the last run list for a line chart
     * @param KeyValue
     * @param KeyName
     * @param ViewName
     */
    protected void loadBarLastRunsOnly(int KeyValue, 
                                        String KeyName,
                                        String ViewName) {
        try {
            IEntity entity = getBOFactory().getEntity(ViewName);
            entity.field(KeyName).setKeyValue(KeyValue);
            ResultSet rs = entity.select();

            ChartWithDataset chartbar = new ChartWithDataset(Joy.parameters().getParameter("ChartsColors").getList(), Joy.parameters().getParameter("transparency").getValue().toString());    
            while (rs.next()) {
                chartbar.add("Job run (" + rs.getDate("RUNDATE").toString() + ")", 
                             rs.getString("DQX_NAME"), 
                             rs.getFloat("SCORE"));
            }
            getBOFactory().closeResultSet(rs);
            
            this.addFormSingleEntry(TAG_LASTRUNS, chartbar.getChartData());

        } catch (SQLException e) {
            Joy.log().error( e);
        }
    }
    
    /**
     * load the related term list
     * @param KeyValue
     * @param KeyName
     * @param ViewName 
     */
    protected void loadTermsList(int KeyValue, 
                                 String KeyName,
                                 String ViewName) {
        JoyFormMatrixEntry matrix = new JoyFormMatrixEntry();
        
        try {
            IEntity entity = getBOFactory().getEntity(ViewName);
            if (KeyValue != 0)
                entity.field(KeyName).setKeyValue(KeyValue);
            ResultSet rs = entity.select();

            while (rs.next()) {
                JoyFormVectorEntry columns = new JoyFormVectorEntry();
                columns.addValue("SCORE", Utils.scoreDisplay(rs.getFloat("SCORE")));
                columns.addValue("COST", rs.getFloat("COST"));
                columns.addValue("DQX_NAME", rs.getString("DQX_NAME"));
                columns.addValue("TRM_NAME", rs.getString("TRM_NAME"));
                columns.addValue("TRM_FK", rs.getString("TRM_FK"));
                columns.addValue("DQX_CODE", rs.getString("DQX_CODE"));
                matrix.addRow(columns);
            }
            this.addFormMatrixEntry("TERM_LIST", matrix);
            getBOFactory().closeResultSet(rs);
            
        } catch (SQLException e) {
            Joy.log().error(  e);
        }
    }
    
    /**
     * Load the related category list
     * @param KeyValue
     * @param KeyName
     * @param ViewName 
     */
    protected void loadCategoryList(int KeyValue, 
                                    String KeyName,
                                    String ViewName)
    {
        JoyFormMatrixEntry matrix = new JoyFormMatrixEntry();
        
        try {
            IEntity entity = getBOFactory().getEntity(ViewName);
            if (KeyValue != 0)
                entity.field(KeyName).setKeyValue(KeyValue);
            ResultSet rs = entity.select();

            while (rs.next()) {
                JoyFormVectorEntry columns = new JoyFormVectorEntry();
                columns.addValue("CAT_PK", rs.getInt("CAT_PK"));
                columns.addValue("CAT_NAME", rs.getString("CAT_NAME"));
                columns.addValue("CAT_ID", rs.getString("CAT_ID"));
                columns.addValue("CAT_PARENT_ID", rs.getString("CAT_PARENT_ID"));
                columns.addValue("CAT_DATETIME_LOAD", rs.getString("CAT_DATETIME_LOAD"));
                columns.addValue("CAT_DESCRIPTION", rs.getString("CAT_DESCRIPTION"));
                matrix.addRow(columns);
            }
            getBOFactory().closeResultSet(rs);
            this.addFormMatrixEntry("CATEGORY_LIST", matrix);
            
        } catch (SQLException e) {
            Joy.log().error( e);
        }
    }

    /**
     * Load the tendance data. This function builds
     *  1) all the last runs scores in a table
     *  2) The trends data (with only the two last values)
     *  3) The gauges by dq Axis (last score only)
     *  4) The multiple radar chart (with only the two last values)
     * @param key 
     * @param ViewName
     * @param KeyName 
     */
    protected void loadDQVectorsValAndTrends(int key, 
                                             String ViewName, 
                                             String KeyName) {
        boolean takethisrow;
        List<String> Axis = new ArrayList();
        List<Float> Values = new ArrayList();
        JoyFormMatrixEntry matrixTrends = new JoyFormMatrixEntry();
        ChartWithDataset radar = new ChartWithDataset(Joy.parameters().getParameter("ChartsColors").getList(), Joy.parameters().getParameter("transparency").getValue().toString());
        JoyFormMatrixEntry matrixLastValues = new JoyFormMatrixEntry();
        ChartWithDataset chartbar = new ChartWithDataset(Joy.parameters().getParameter("ChartsColors").getList(), Joy.parameters().getParameter("transparency").getValue().toString()); 
        
        try {
            IEntity entity = getBOFactory().getEntity(ViewName);
            if (entity == null)
                entity = getBOFactory().getEntity(ViewName);
            entity.field(KeyName).setKeyValue(key);
            entity.addSort("DQX_NAME", "RUNDATE DESC");
            ResultSet rs = entity.select();
            
            String lastDqxName = "";
            int nbScoreByDqx = 1;
            while (rs.next()) {
                // Add the data (all) into the lastruns chart
                chartbar.add("Job run (" + rs.getDate("RUNDATE").toString() + ")", 
                             rs.getString("DQX_NAME"), 
                             rs.getFloat("SCORE"));
                
                // Build the trends data with only the last two values by each dq axis
                takethisrow = true;
                if (lastDqxName.equalsIgnoreCase(rs.getString("DQX_NAME")) && (nbScoreByDqx > 2)) {
                    // Rupture don't take anymore data for this DQ vector/axis
                    takethisrow = false;
                } else if (!lastDqxName.equalsIgnoreCase(rs.getString("DQX_NAME"))) {
                    // Axis changed
                    if (nbScoreByDqx == 2) {
                        // Missing a score (does not exist), add a dummy/blank score
                        Axis.add(lastDqxName);
                        Values.add(null);
                    }
                    nbScoreByDqx = 1;
                    lastDqxName = rs.getString("DQX_NAME");
                    takethisrow = true;
                } 
                if (takethisrow) {  // add this data (score)
                    //Axis.add(rs.getString("DQX_NAME") + " (" +rs.getString("DQX_WEIGHT")+ ")");
                    Axis.add(rs.getString("DQX_NAME"));
                    Values.add(rs.getFloat("SCORE"));
                }
                nbScoreByDqx++;
            }
            this.addFormSingleEntry(TAG_LASTRUNS, chartbar.getChartData());
            
            if (nbScoreByDqx == 2) {
                // Missing a score (does not exist) for the last value only, add a dummy/blank score
                Axis.add(lastDqxName);
                Values.add(null);
            }
            this.getBOFactory().closeResultSet(rs);
            
            // Now calcultate the trend and load the data for display
            int i=0;
            while (i < Axis.size()) {
                String axis = Axis.get(i);
                Float prev = (Values.get(i+1)==null ? new Float(0) : Values.get(i+1)); 
                Float last = (Values.get(i)==null ? new Float(0) : Values.get(i));
                Float trend = last - prev;
                
                // Multiple Radar build
                radar.add(Axis.get(i), "Previous score", prev);
                radar.add(Axis.get(i+1),"Last score",  last);

                // Trends display
                JoyFormVectorEntry trendVector = new JoyFormVectorEntry();
                trendVector.addValue("AXIS", axis);
                trendVector.addValue("TREND_SCORE", String.format("%.1f", trend));
                trendVector.addValue("PREV", Utils.scoreDisplay(prev));
                trendVector.addValue("LAST", Utils.scoreDisplay(last));
                
                // Last value display (single counter)
                JoyFormVectorEntry lastValVector = new JoyFormVectorEntry();
                ChartCounterData myChart = new ChartCounterData(last,  Axis.get(i),  Axis.get(i));
                myChart = setCounterOptions(myChart);

                lastValVector.addValue(TAG_COUNTER_NAME, Axis.get(i));
                lastValVector.addValue(TAG_COUNTER_OBJECT, myChart); 
                matrixLastValues.addRow(lastValVector);

                // Trends values calculation
                if (Values.get(i+1) != null) {
                    if (trend == 0)
                        trendVector.addValue(TAG_TREND_NAME, "EQUAL");
                    else if (trend > 0)
                        trendVector.addValue(TAG_TREND_NAME, "UP");
                    else if (trend < 0)
                        trendVector.addValue(TAG_TREND_NAME, "DOWN");
                } else 
                    trendVector.addValue(TAG_TREND_NAME, "NO");
                matrixTrends.addRow(trendVector);
                
                // Next couple of data (Couple = previous val and last val)
                i=i+2;
            }
            
        } catch (SQLException ex) {
            Joy.log().error( ex);
        }
        
        // Add data into the result form
        this.addFormMatrixEntry(TAG_MATRIX_VALUE, matrixLastValues);
        this.addFormMatrixEntry(TAG_TRENDS_LIST, matrixTrends);
        this.addFormSingleEntry(TAG_MULTIPLE_RADAR,  radar.getChartData());
    }
    
    private ChartCounterData setCounterOptions(ChartCounterData myChart) {
        try {
            myChart.setThresolds(Integer.valueOf(Joy.parameters().getParameter("ThresoldBadToWarn").getValue().toString()), 
                                 Integer.valueOf(Joy.parameters().getParameter("ThresoldwarningToGood").getValue().toString()));
            myChart.setColors(Joy.rgba(Joy.parameters().getParameter("ColorBad").getValue().toString(), "1"), 
                              Joy.rgba(Joy.parameters().getParameter("ColorWarning").getValue().toString(), "1"), 
                              Joy.rgba(Joy.parameters().getParameter("ColorGood").getValue().toString(), "1"));
        } catch (Exception e) {
            Joy.log().warn(e);
        }
        return myChart;
    }
}
