/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.form.analytics.global;

import com.joy.Joy;
import com.joy.bo.BOEntityReadOnly;
import com.joy.charts.chartjs.ChartWithDataset;
import com.joy.mvc.actionTypes.ActionTypeForm;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.joy.bo.IEntity;

/**
 *
 * @author benoit
 */
public class ReportGlobalInvalidsAndCosts extends ActionTypeForm {
    protected String fieldValue;
    
    @Override
    public String display() {
        // Build the different sub-reports
        this.addFormSingleEntry("BARS1", (Object)getBarGlobalCostByDQAxis());
        this.addFormSingleEntry("BARS2", (Object)getBarGlobalCostByDataSource());
        this.addFormSingleEntry("BARS3", (Object)getBarGlobalCostByContext());
        this.addFormSingleEntry("BARS4", (Object)getBarGlobalCostByTerm());
        
        return super.display(); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String getBarGlobalCostByDQAxis()
    {
        try {
            IEntity entity = this.getBOFactory().getEntity("Analytics - Global by DQ Axis"); //"LASTONLY_COST_IR_BYAXIS");
            ResultSet rs = entity.selectAll();

            ChartWithDataset chartbar = new ChartWithDataset(Joy.parameters().getParameter("ChartsColors").getList(), Joy.parameters().getParameter("transparency").getValue().toString());   
            while (rs.next()) {
                chartbar.add(rs.getString("DQX_NAME"), "data", 
                             rs.getFloat(fieldValue));
            }
            this.getBOFactory().closeResultSet(rs);
            return chartbar.getChartData();

        } catch (SQLException e) {
            Joy.log().error(e);
            return null;
        }
    }
    
    private String getBarGlobalCostByDataSource()
    {
        try {
            IEntity entity = this.getBOFactory().getEntity("Analytics - Global by Datasource"); //"LASTONLY_COST_IR_BYSOURCE");
            ResultSet rs = entity.selectAll();

            ChartWithDataset chartbar = new ChartWithDataset(Joy.parameters().getParameter("ChartsColors").getList(), Joy.parameters().getParameter("transparency").getValue().toString());   
            while (rs.next()) {
                chartbar.add((rs.getString("DSO_SOURCENAME") == null ? "Unknown" : rs.getString("DSO_SOURCENAME")), 
                             "data", 
                             rs.getFloat(fieldValue));
            }
            this.getBOFactory().closeResultSet(rs);
            return chartbar.getChartData();

        } catch (SQLException e) {
            Joy.log().error(e);
            return null;
        }
    }

    private String getBarGlobalCostByContext()
    {
        try {
            IEntity entity = this.getBOFactory().getEntity("Analytics - Global by Context"); //"LASTONLY_COST_IR_BYCONTEXT");
            ResultSet rs = entity.selectAll();

            ChartWithDataset chartbar = new ChartWithDataset(Joy.parameters().getParameter("ChartsColors").getList(), Joy.parameters().getParameter("transparency").getValue().toString());   
            while (rs.next()) {
                chartbar.add(rs.getString("CON_DESCRIPTION"), "data", 
                             rs.getFloat(fieldValue));
            }
            this.getBOFactory().closeResultSet(rs);
            return chartbar.getChartData();

        } catch (SQLException e) {
            Joy.log().error(e);
            return "";
        }
    }
    
    private String getBarGlobalCostByTerm()
    {
        try {
            IEntity entity = this.getBOFactory().getEntity("Analytics - Global by Term"); //"LASTONLY_COST_IR_BYTERM");
            ResultSet rs = entity.selectAll();

            ChartWithDataset chartbar = new ChartWithDataset(Joy.parameters().getParameter("ChartsColors").getList(), Joy.parameters().getParameter("transparency").getValue().toString());   
            while (rs.next()) {
                String s = rs.getString("TRM_NAME");
                String res = s;
                if (s.indexOf("'") >0 ) {
                    res =  s.substring(0, s.indexOf("'")) + "\\";
                    res += s.substring(s.indexOf("'"), s.length());
                } 
                chartbar.add(res, "data",  rs.getFloat(fieldValue));
            }
            this.getBOFactory().closeResultSet(rs);
            return chartbar.getChartData();

        } catch (SQLException e) {
            Joy.log().error(e);
            return "";
        }
    }
}
