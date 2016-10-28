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
package com.dgm.form.analytics.maps;

import com.dgm.beans.termreltree.TermBean;
import com.dgm.beans.termreltree.TermTree;
import com.joy.Joy;
import com.joy.mvc.actionTypes.ActionTypeForm;
import com.joy.mvc.formbean.JoyFormVectorEntry;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr) 
 */
public class MapForRelatedTerms extends ActionTypeForm {
    private static final int NB_HOP_MAX = 5;
    private static final int DEFAULT_NB_HOP = 3;
    
    @Override
    public String search() {
        this.addFormSingleEntry("target", this.getStrArgumentValue("target"));
        loadCBO(0, DEFAULT_NB_HOP);
        loadTermTypes();
        return super.search(); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Load the Term Type combo
     */
    private void loadTermTypes() {
        try {
            ResultSet rs;
            IEntity entity = getBOFactory().getEntity("Analytics - Terms Type List");
            rs = entity.select();
            this.loadVector(rs, "TRT_PK", "TRT_NAME", "termtypes", "TRT_PK");
            this.getBOFactory().closeResultSet(rs);

        } catch (Exception e) {
            Joy.log().error(e);
        }
    }
    
    @Override
    public String display() {
        int Term, nbHops;
        String Nodes = "", Relationships = "";
        
        try { Term = this.getIntArgumentValue("term"); } catch (Exception e) { Term = 0;}
        try { nbHops = this.getIntArgumentValue("nbhops"); } catch (Exception e) { nbHops = DEFAULT_NB_HOP;}
        
        // récupère l'arbre à partir du terme
        //TermBean treefromRequestedTerm = getRelationShipTree(Term, 1, nbHops);
        TermTree mytree = new TermTree(this.getBOFactory());
        TermBean firstTerm = mytree.build(Term, 1, nbHops);
        if (firstTerm != null) {
            Nodes = firstTerm.getAllFlatTerms();
            Relationships = firstTerm.getAllFlatRelationships();
        }
        
        this.addFormSingleEntry("NODES", Nodes);
        this.addFormSingleEntry("RELATIONSHIPS", Relationships);
        
        loadCBO(Term, nbHops);
        loadTermTypeCBO();
        
        return super.display(); 
    }
    
    private void loadTermTypeCBO() {
        try {
            ResultSet rs;
            IEntity entity = getBOFactory().getEntity("Analytics - Terms Type List");
            rs = entity.select();
            this.loadVector(rs, "TRT_NAME", "TRT_NAME", "termtypes", "TRT_NAME");
            this.getBOFactory().closeResultSet(rs);

        } catch (Exception e) {
            Joy.log().error(e);
        }
    }
    
    /**
     * Load the Combobox selections
     * @param Key  current term key
     * @param NbHops Nb hops selected
     */
    private void loadCBO(int Key, int NbHops) {
       
        // Load the terms Combobox
        try {
            IEntity entity = getBOFactory().getEntity("DIM_TERM");
            ResultSet rs = entity.select();
            entity.addSort("TRM_NAME");
            entity.setDistinct(true);
            entity.useOnlyTheseFields("TRM_PK", "TRM_NAME");
            rs = entity.select();
            this.loadVector(rs, "TRM_PK", "TRM_NAME", "term", String.valueOf(Key));
            getBOFactory().closeResultSet(rs);
            
        } catch (Exception e) {
            Joy.log().error(e);
        }
        
        // load the Nb hops Combobox
        JoyFormVectorEntry columns = new JoyFormVectorEntry();
        columns.setSelected(String.valueOf(NbHops));
        for (int i = 1; i < NB_HOP_MAX; i++) {
            columns.addValue(String.valueOf(i), String.valueOf(i), String.valueOf(i));
        }
        this.addFormVectorEntry("NBHOPS", columns);
    }

   /**
    * Get all the term informations
    * @param PK TRM_PK
    * @param level
    * @return 
    */
    private TermBean getTermInfo (int PK, int level) {
        try {
            IEntity entity = getBOFactory().getEntity("Analytics - Rel Term Info");
            TermBean trm;
            
            entity.field("TRM_PK").setKeyValue(PK);
            ResultSet rs = entity.select();
            
            if (rs.next()) {
                trm = new TermBean(this.getBOFactory(), 
                                    rs.getString("GLO_NAME"), 
                                    rs.getString("TRM_NAME"), 
                                    rs.getInt("TRM_PK"),
                                    level);
            } else {
                trm = new TermBean(this.getBOFactory());
            }
            
            getBOFactory().closeResultSet(rs);
            return trm;
            
        } catch (SQLException ex) {
            Joy.log().error(ex);
            return new TermBean(getBOFactory());
        }
        
    }

}
