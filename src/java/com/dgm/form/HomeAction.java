/*
 * Home page behavior
 */
package com.dgm.form;

import com.dgm.ui.UITaglibSpotData;
import com.joy.mvc.actionTypes.ActionTypeForm;
import java.sql.ResultSet;
import com.joy.bo.IEntity;

/**
 *
 * @author Administrator
 */
public class HomeAction extends ActionTypeForm {

    private void display_Spots() {
        // Spot business Terms
        UITaglibSpotData spot = new UITaglibSpotData();
        IEntity query = this.getBOFactory().getEntity("List of terms used");
        IEntity table = this.getBOFactory().getEntity("DIM_TERM");
        spot.setLittlelongtext("Business Terms Used");
        spot.setBigshorttext(String.valueOf(query.count()) + " / " + String.valueOf(table.count()));
        spot.setLinkURL("byterm", "search");
        spot.setLinktext("View Business Terms KPIs");
        this.addFormSingleEntry("TERMS", spot);

        // Metrics
        spot = new UITaglibSpotData();
        query = this.getBOFactory().getEntity("List of metrics used");
        table = this.getBOFactory().getEntity("DIM_METRIC");
        spot.setLittlelongtext("Metrics Used");
        spot.setBigshorttext(String.valueOf(query.count()) + " / " + String.valueOf(table.count()));
        spot.setLinkURL("bymetric", "search");
        spot.setLinktext("View Metrics");
        this.addFormSingleEntry("METRICS", spot);
        
        // Glossaries
        spot = new UITaglibSpotData();
        table = this.getBOFactory().getEntity("DIM_GLOSSARY");
        spot.setLittlelongtext("Glossaries Used");
        spot.setBigshorttext(String.valueOf(table.count()));
        spot.setLinkURL("byglossary", "search");
        spot.setLinktext("View Glossaries KPIs");
        this.addFormSingleEntry("GLOSSARIES", spot);
        
        // DQ Dimensions
        spot = new UITaglibSpotData();
        table = this.getBOFactory().getEntity("DIM_DQAXIS");
        spot.setLittlelongtext("Data Quality Dimensions in scope");
        table.addFilter("DQX_PK>0");
        spot.setBigshorttext(String.valueOf(table.count(true)));
        spot.setLinkURL("bydqaxis", "search");
        spot.setLinktext("View Data Quality Dimensions");
        this.addFormSingleEntry("DQAXIS", spot);
    }
    
    private void  display_Metrics() {
        // display the DQ Axis global scores
        ResultSet rs = this.getBOFactory().getEntity("AXIS_SCORE_HOME_00").selectAll();
        this.loadMatrix(rs, "SCORES");
        this.getBOFactory().getDB().closeResultSet(rs);
        
        // Display the best Terms / Home - Best terms
        IEntity entity = this.getBOFactory().getEntity("Home - Best terms");
        entity.setLimitRecords(5);
        rs = entity.selectFiltered();
        this.loadMatrix(rs, "BEST_TERMS");
        this.getBOFactory().getDB().closeResultSet(rs);
    }
    
    @Override
    public String display() {
        display_Spots();
        display_Metrics();
        
        return super.display(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
