/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.form.analytics;

import com.joy.Joy;
import com.joy.bo.BOEntityReadOnly;
import com.joy.bo.BOEntityReadWrite;
import com.joy.mvc.formbean.JoyFormVectorEntry;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.joy.bo.IEntity;

/**
 *
 * @author benoit
 */
public class ReportCommonConsolidated extends ReportCommonAction {
    //private static String TAG_DIMENSION_REQUEST = "glossary";
    //private static String DIMENSION_TABLE = "DIM_GLOSSARY";
    //private static String DIMENSION_FIELD_NAME = "GLO_NAME";
    //private static String DIMENSION_FIELD_KEY = "GLO_PK";
    
    protected String tagDimensionRequest;
    protected String dimTableName;
    protected String dimFieldName;
    protected String dimFieldKey;

    @Override
    public String display() {
        int iCurrent = 0;
        try {
            iCurrent = Integer.valueOf(getStrArgumentValue(tagDimensionRequest));
        } catch (Exception e) {}
        
        //if (iCurrent != 0) 
            return display(iCurrent);
        // else 
        //    return super.display(); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String display(int Key) {
        loadCBO(Key);
        return super.display();
    } 
    
    @Override
    public String search() {
        int iCurrent = 0;
        try {
            iCurrent = Integer.valueOf(getStrArgumentValue(tagDimensionRequest));
        } catch (Exception e) {}
        
        loadCBO(iCurrent);
        return super.search(); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Build the data combobox selection list for a search 
     * @param current
     */
    protected void loadCBO(int current) {
        IEntity entity;
            
        JoyFormVectorEntry columns = new JoyFormVectorEntry();
        columns.setSelected(String.valueOf(current));
        try {
            entity = this.getBOFactory().getEntity(dimTableName);
            entity.setDistinct(true);
            entity.useNoFields();
            entity.useTheseFields(dimFieldKey, dimFieldName); 
            entity.addFilter(dimFieldKey + " IS NOT NULL");
            entity.addSort(dimFieldName); 
            ResultSet rs = entity.selectFiltered();
            while (rs.next()) {
                columns.addValue(dimFieldKey, 
                                 rs.getString(dimFieldKey), 
                                 rs.getString(dimFieldName));
            }
            this.getBOFactory().closeResultSet(rs);

        } catch (SQLException e) {
            Joy.log().error( e);
        }
        this.addFormVectorEntry(tagDimensionRequest, columns);
    }
    
    /***
     * Load into the result form the global term informations
     * @param Key current context
     */
    protected void loadGlobalData(int Key) {
        IEntity entity = this.getBOFactory().getEntity(this.dimTableName);
        entity.field(this.dimFieldKey).setKeyValue(Key);
        ResultSet rs = entity.selectFiltered();
        this.loadSingle(rs);
        this.getBOFactory().closeResultSet(rs);
    }
}
