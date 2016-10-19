/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.beans.termreltree;

import com.joy.Joy;
import com.joy.bo.BOFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA
 */
public class TermTree {
    BOFactory entities;

    public TermTree(BOFactory entities) {
        this.entities = entities;
    }
    
    /*
    * récupère les données d'un terme
    */
    private TermBean getTermInfo(int PK, int level) {
        try {
            IEntity entity = entities.getEntity("Analytics - Rel Term Info");
            TermBean trm;
            
            entity.field("TRM_PK").setKeyValue(PK);
            ResultSet rs = entity.selectFiltered();
            
            if (rs.next()) {
                trm = new TermBean(rs.getString("GLO_NAME"), 
                               rs.getString("TRM_NAME"), 
                               rs.getInt("TRM_PK"),
                               level);
            } else {
                trm = new TermBean(entities);
            }
            
            entities.closeResultSet(rs);
            return trm;
            
        } catch (SQLException ex) {
            Joy.log().info( ex);
            return new TermBean(entities);
        }
        
    }
    
    /**
     * build the terms & relationship tree recusrsively
     * @param myTerm        Origin's term PK
     * @param currentLevel  Current level (recusrsivity)
     * @param levelMax      Max Depth
     * @return 
     */
    public TermBean build(int myTerm, 
                          int currentLevel, 
                          int levelMax) {
        TermBean ars = new TermBean(entities);
        boolean hadData = false;
        
        ars.setKey(myTerm);
        try {
            TermBean currentTerm = getTermInfo(myTerm, currentLevel);
            if (currentLevel == levelMax) { // fin de la récusrivité !
                Joy.log().debug( "End of Term recursivity at level " + String.valueOf(currentLevel));
                return currentTerm;
            }
            
            // Récupère les relations
            Joy.log().debug("Get relationships for  " + String.valueOf(myTerm));
            IEntity entity = entities.getEntity("Analytics - Rel Term Relationships");
            entity.field("TERM_PK_SOURCE").setKeyValue(myTerm);
            entity.addSort("REL_NAME");
            ResultSet rs = entity.selectFiltered();
            
            String rupture = "";
            RelationshipBean currentFolder = null;

            while (rs.next()) {
                hadData = true;
                ars.setName(rs.getString("TERM_SOURCE"));
                ars.setTermType(rs.getString("GLO_NAME_SOURCE"));
                if (!rupture.equalsIgnoreCase(rs.getString("REL_NAME")) || currentFolder == null) {
                    // création d'un folder + term
                    rupture = rs.getString("REL_NAME");
                    Joy.log().debug( "Add relationship for  " + rupture);
                    currentFolder = new RelationshipBean(rs.getString("REL_NAME"), 
                                                     rs.getInt("REL_FK"),
                                                     myTerm);
                    currentFolder.addRelatedTerm(build(rs.getInt("TERM_PK_TARGET"), currentLevel+1, levelMax));
                    ars.addRelationShip(currentFolder);
                    
                } else {
                    // création d'un terme seul (dans le folder courant)
                    Joy.log().debug( "Add Term for  " + rs.getInt("TERM_PK_TARGET"));
                    currentFolder.addRelatedTerm(build(rs.getInt("TERM_PK_TARGET"), currentLevel+1, levelMax));
                }
            }
            entities.closeResultSet(rs);
            if (!hadData) {
                // dernier noeud
                ars = currentTerm;
            }
            
            return ars;
            
        } catch (SQLException e) {
            Joy.log().error( e);
        }
        return ars;
    }
    
}
