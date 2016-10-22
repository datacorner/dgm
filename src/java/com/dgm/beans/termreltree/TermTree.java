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
package com.dgm.beans.termreltree;

import com.joy.Joy;
import com.joy.bo.BOFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr) 
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
            ResultSet rs = entity.select();
            
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
            ResultSet rs = entity.select();
            
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
