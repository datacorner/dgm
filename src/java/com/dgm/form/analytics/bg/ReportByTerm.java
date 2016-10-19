/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.form.analytics.bg;

import com.dgm.common.Constants;
import com.dgm.common.Utils;
import com.dgm.common.providers.ParamProvider;
import com.dgm.form.analytics.ReportCommonAction;
import com.joy.Joy;
import com.joy.mvc.formbean.JoyFormVectorEntry;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA
 */
public class ReportByTerm extends ReportCommonAction {

    private int getTermID() {
        int iCurrentTerm = 0;
        try {
            iCurrentTerm = Integer.valueOf(getStrArgumentValue("term"));
        } catch (Exception e) {}
        return iCurrentTerm;
    }
    
    /**
     * Display the by term report
     * @return 
     */
    @Override
    public String display() {
        
        int iCurrentTerm = getTermID();
        
        if (iCurrentTerm != 0) {
            // Collect term informations
            loadGlobalData(iCurrentTerm); 
            // Load trends value and radar data 
            loadDQVectorsValAndTrends(iCurrentTerm, "Analytics - Terms Last Runs" ,  "TRM_FK"); //"RPT_TERM_LASTRUNS", "TRM_FK");
            // Metrics list
            loadMetricTableList(iCurrentTerm, "TRM_FK"); 
            // Contexts list
            loadLinkedContextList(iCurrentTerm);
            // Data sources
            loadLinkedDataSourceList(iCurrentTerm);
            // available Terms Combo
            loadTerms(false, iCurrentTerm);
            // Global score
            calculateGlobalScore(iCurrentTerm);
        }
        return super.display(); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Calculate Term score
     */
    public void calculateGlobalScore(int currentTerm) {
        IEntity entity = getBOFactory().getEntity("Analytics - Terms Global Score Calculation");
        entity.field("TRM_FK").setKeyValue(currentTerm);
        ResultSet rs = entity.selectFiltered();
        
        try {
            if (rs.next())
                this.addFormSingleEntry("GLOBALSCORE", rs.getString("GLOBALSCORE"));
            else
                this.addFormSingleEntry("GLOBALSCORE", "--");
        } catch (SQLException ex) {
            this.addFormSingleEntry("GLOBALSCORE", "--");
        }
        getBOFactory().closeResultSet(rs);
    }
    
    /**
     * Display the term search page
     * @return search page tag
     */
    @Override
    public String search() {
        int iCurrentTerm = getTermID();
        
        boolean onlyTermsDefined = this.getStrArgumentValue("termsdefined").equalsIgnoreCase("on");
        this.addFormSingleEntry("termsdefined", onlyTermsDefined);
        // load Term list
        loadTerms(onlyTermsDefined, iCurrentTerm);
        // Load Term type list
        this.addFormSingleEntry("termtypecriteria", true);
        loadTermTypes();
        
        return super.search(); 
    }

    /**
     * Fill the Context list
     * @param currentTerm 
     */
    private void loadLinkedContextList(int currentTerm) {
        IEntity entity = getBOFactory().getEntity("Analytics - Terms Context List");
        entity.field("TRM_FK").setKeyValue(currentTerm);
        ResultSet rs = entity.selectFiltered();
        
        this.loadMatrix(rs, "CONTEXT_LIST");
        getBOFactory().closeResultSet(rs);
    }
    
    /**
     * Fill the data source list
     * @param currentTerm 
     */
    private void loadLinkedDataSourceList(int currentTerm) {
        IEntity entity = getBOFactory().getEntity("Analytics - Terms DS List");
        entity.field("TRM_FK").setKeyValue(currentTerm);
        ResultSet rs = entity.selectFiltered();

        this.loadMatrix(rs, "DATASOURCE_LIST");
        getBOFactory().closeResultSet(rs);
    }
    
    /**
     * Load the Term Type combo
     */
    private void loadTermTypes() {
        try {
            ResultSet rs;
            IEntity entity = getBOFactory().getEntity("Analytics - Terms Type List");
            rs = entity.selectAll();
            this.loadVector(rs, "TRT_PK", "TRT_NAME", "termtypes", "TRT_PK");
            this.getBOFactory().closeResultSet(rs);

        } catch (Exception e) {
            Joy.log().error(e);
        }
    }
    
    /**
     * Build the term combobox list for a search
     * @param entities
     * @param definedonly
     * @param currentTerm 
     */
    private void loadTerms(boolean definedonly,
                              int currentTerm) {
        JoyFormVectorEntry columns = new JoyFormVectorEntry();
        columns.setSelected(String.valueOf(currentTerm));
        try {
            ResultSet rs;
            if (! definedonly) {
                IEntity entity = getBOFactory().getEntity("Analytics - Terms List");
                rs = entity.selectAll();
            } else {
                IEntity entity = getBOFactory().getEntity("DIM_TERM");
                rs = entity.selectAll();
            }
            
            while (rs.next()) {
                columns.addValue("TRM_PK", rs.getString("TRM_PK"), rs.getString("TRM_NAME"));
            }
            this.getBOFactory().closeResultSet(rs);

        } catch (SQLException e) {
            Joy.log().error(e);
        }
        this.addFormVectorEntry("term", columns);
    }
    
    /***
     * Load into the result form the global term informations
     * @param currentTerm current TRM_PK
     */
    private void loadGlobalData(int currentTerm) {
        try {
            IEntity entity = getBOFactory().getEntity("Analytics - Terms Global Informations");
            entity.field("TRM_PK").setKeyValue(currentTerm);
            ResultSet rs = entity.selectFiltered();
            ParamProvider myParam = new ParamProvider(getBOFactory());
            boolean displayInfa = myParam.getParamValue("INFORMATICA").getStrValue().equalsIgnoreCase(Constants.PARAM_YES);
            
            if (rs.next()) {
                this.addFormSingleEntry("TRM_NAME", rs.getString("TRM_NAME"));
                this.addFormSingleEntry("TRT_NAME", rs.getString("TRT_NAME"));
                this.addFormSingleEntry("TRM_DESCRIPTION", Joy.T(rs.getString("TRM_DESCRIPTION")));
                this.addFormSingleEntry("TRM_EXAMPLE", Joy.T(rs.getString("TRM_EXAMPLE")));
                this.addFormSingleEntry("TRM_OWNER", Joy.T(rs.getString("TRM_OWNER")));
                this.addFormSingleEntry("TRM_PHASE", Joy.T(rs.getString("TRM_PHASE")));
                
                this.addFormSingleEntry("TRM_USAGE", Joy.T(rs.getString("TRM_USAGE")));
                this.addFormSingleEntry("TRM_OWNER_EMAIL", Joy.T(rs.getString("TRM_OWNER_EMAIL"), "Not Defined"));
                this.addFormSingleEntry("TRM_STEWARD_EMAIL", Joy.T(rs.getString("TRM_STEWARD_EMAIL"), "Not Defined"));
                this.addFormSingleEntry("GLO_NAME", rs.getString("GLO_NAME"));
                this.addFormSingleEntry("GLO_PK", rs.getString("GLO_PK"));
                this.addFormSingleEntry("TRM_PK", rs.getString("TRM_PK"));
                this.addFormSingleEntry("TRM_CLUSTER_ID", rs.getString("TRM_CLUSTER_ID"));
                // Term icon
                String icon = Utils.getTermTypeIcon(this.getBOFactory(), rs.getString("TRT_NAME"));
                this.addFormSingleEntry("ICON", icon);
                this.addFormSingleEntry("IMGICO", (rs.getString("TRT_NAME").isEmpty() ? Constants.DEFAULT_TERMTYPE_ICON : icon));
                // Glossary link
                this.addFormSingleEntry("GLOSSARY_LINK", Joy.href("byglossary", "display", rs.getString("GLO_NAME"), "glossary", String.valueOf(rs.getString("GLO_PK"))));
                // Category link
                this.addFormSingleEntry("CATEGORY_LINK", Joy.href("bycategory", "display", rs.getString("CAT_NAME"), "category", String.valueOf(rs.getString("CAT_PK"))));
                
                // Informatica specifics
                this.addFormSingleEntry("INFORMATICA", displayInfa);
                if (displayInfa) {
                    String mmURL = myParam.getParamValue("infammurl").getStrValue();
                    this.addFormSingleEntry("INFA_MM_LINK",  getMetaManagerURLLink(false, mmURL, rs.getString("GLO_NAME"), rs.getString("TRM_NAME")));
                    this.addFormSingleEntry("INFA_DIRECT_LINK",  getBGTermURLLink(rs.getString("OBJECT_ID")));
                }
                
                if (rs.getString("TRM_CLUSTER_ID") != null)
                    this.addFormSingleEntry("CONFIG_TERM_LINK",  Joy.url("reltermmetric", "EDIT", "TRM_CLUSTER_ID", rs.getString("TRM_CLUSTER_ID")));
                else 
                    this.addFormSingleEntry("CONFIG_TERM_LINK", Joy.url("reltermmetric", "ADD", "TERM_NAME", rs.getString("TRM_NAME")));
                this.addFormSingleEntry("REL_MAP_LINK",  Joy.basicURL("report", "display") + "&report=relationshipmap&term=" + rs.getString("TRM_PK") + "&hops=3");
                
            }
            this.getBOFactory().closeResultSet(rs);

        } catch (SQLException e) {
            Joy.log().error(e);
        }
    }
    
   /**
    * Build the Informatica Matedata Manager url like this
  http://win2k8:10250/mm/lineage?objectPath=MM/Data Governance Glossary/Account&mode=lineage
    * @param _detail
    * @param URL
    * @param Glossary
    * @param Term
    * @return 
    */
   private String getMetaManagerURLLink(boolean _detail, String URL, String Glossary, String Term) {
       String url = "";

       //url += ParamProvider.getParamValue("infammurl").getStrValue();  
       url += URL; 
       url += "lineage?objectPath=MM/"  ;
       url += Glossary + "/" ;
       url += Term;
       url += "&mode=" + (_detail ? "detail" : "lineage");

       return url;
   }
   
   /**
    * replace the url by "http://infaw2k12:8085/analyst/?wstate=(%27$obj%27:%27" + ObjectID + "@com.informatica.bg.core.models.BGTermInfo%27,%27$ws%27:bgRequirementsWS)";  
    * @param ObjectID
    * @return 
    */
   private String getBGTermURLLink(String ObjectID) {
       ParamProvider myParams = new ParamProvider(this.getBOFactory());
       String mask = myParams.getParamValue("bgtermmask").getStrValue();
       String url = "";

       if (mask == null)
           return "#";
       if (mask.isEmpty())
           return "#";
       url = mask.replace("{0}", ObjectID);

       return url;
   }
   
}
