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
package com.dgm.form.manage;

import com.joy.Joy;
import com.joy.bo.BOEntityReadWrite;
import com.joy.mvc.actionTypes.ActionTypeForm;
import com.joy.mvc.formbean.JoyFormMatrixEntry;
import com.joy.mvc.formbean.JoyFormVectorEntry;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.joy.bo.IEntity;


/**
 * Gestion des relations Term Metric & DQ Axis
 * Table : REL_TERM_METRIC
 * Definition: un cluster (TRM_CLUSTER_ID) est un groupe (DQ Axis & Metric) pour un Term
 * @author Benoit CAYLA (benoit@famillecayla.fr) 
 */
public class RelTermMetricAction extends ActionTypeForm {
    
    @Override
    public String display() {
        return super.edit(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String edit() {
        String uid = getStrArgumentValue("TRM_CLUSTER_ID");
        String termlink = "";
        JoyFormMatrixEntry matrix = new JoyFormMatrixEntry();
        if (uid != null) {
            int id = Integer.parseInt(uid);
            try {
                IEntity entity = this.getBOFactory().getEntity("REL_TERM_METRIC");
                entity.field("TRM_CLUSTER_ID").setKeyValue(id);
                entity.addSort("DQX_NAME");
                ResultSet rs = entity.selectFiltered();
                int TRM_PK = this.getTrmPKFromClusterID(uid);
                
                while (rs.next()) {
                    JoyFormVectorEntry columns = new JoyFormVectorEntry();
                    columns.addValue("TMD_DESCRIPTION", rs.getString("TMD_DESCRIPTION"));
                    columns.addValue("TRM_CLUSTER_ID", rs.getInt("TRM_CLUSTER_ID"));
                    columns.addValue("MET_NAME", (rs.getString("MET_NAME") == null ? "" : rs.getString("MET_NAME")));  
                    columns.addValue("TRM_NAME", rs.getString("TRM_NAME"));
                    columns.addValue("TRM_PK", TRM_PK);
                    columns.addValue("DQX_NAME", rs.getString("DQX_NAME"));
                    columns.addValue("TMD_PK", rs.getInt("TMD_PK"));
                    if (termlink.isEmpty()) {
                        termlink = getTermLink(String.valueOf(TRM_PK), rs.getString("TRM_NAME"));
                        columns.addValue("TERMLINK", termlink);
                    }
                    matrix.addRow(columns);
                }
                this.getBOFactory().closeResultSet(rs);

                this.addFormSingleEntry("TRM_CLUSTER_ID", uid);
                this.addFormSingleEntry("TERM_LINK", termlink);

            } catch (SQLException e) {
                Joy.log().error( e);
            }
        }
        this.addFormMatrixEntry("LIST", matrix);
        loadListOfAvailableMetrics();
        return super.edit(); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String getTermLink(String TRM_PK, String TRM_NAME) {
        return Joy.href("byterm", "display", TRM_NAME, "term", String.valueOf(TRM_PK));
    } 
    
    /**
     * Retourne la liste des relations
     * @return tag de redirection LIST
     */
    @Override
    public String other() {
        return this.list();
    }

    @Override
    public String list() {
        JoyFormMatrixEntry matrix = new JoyFormMatrixEntry();
         try {
            IEntity entity = this.getBOFactory().getEntity("Rel Term Metric List");
            ResultSet rs = entity.selectAll();
            
            while (rs.next()) {
                JoyFormVectorEntry columns = new JoyFormVectorEntry();
                columns.addValue("TRM_CLUSTER_ID", rs.getString("TRM_CLUSTER_ID"));
                columns.addValue("TRM_NAME", rs.getString("TRM_NAME"));
                columns.addValue("TRM_PK", rs.getString("TRM_PK"));
                columns.addValue("TERMLINK", getTermLink(rs.getString("TRM_PK"), rs.getString("TRM_NAME")));
                
                matrix.addRow(columns);
            }
            this.getBOFactory().closeResultSet(rs);
        } catch (SQLException e) {
            Joy.log().error(e);
        }
        this.addFormMatrixEntry("LIST", matrix);
        
        loadListOfAvailableTerms();
        return super.list(); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Créé une nouvelle relation
     * @return tag de redirection EDIT
     */
    @Override
    public String add() {
        // Ajout d'un nouveau terme
        if (!getStrArgumentValue("TERM_NAME").equals("")) {
            int iCluster = daoAddTerm(getStrArgumentValue("TERM_NAME"));
            if (iCluster > 0) {
                this.addDisplayMessageInfo("00_ADDED_RELTERMMETRIC_OK");
                AutoLink();
            }
            this.setNewArgument("TRM_CLUSTER_ID", iCluster);
        }
        return this.edit(); 
    }

    /**
     * Supprime un cluster de relation( term, metric, axis)
     * @param _TRM_CLUSTER_ID
     * @return faux si erreur
     */
    private boolean daoDelete(String _TRM_CLUSTER_ID) {
        try {
            BOEntityReadWrite entity = (BOEntityReadWrite)this.getBOFactory().getEntity("REL_TERM_METRIC");
            entity.field("TRM_CLUSTER_ID").setKeyValue(Integer.parseInt(_TRM_CLUSTER_ID));
            entity.delete();
            return true;
            
        } catch (Exception ex) {
            Joy.log().error( ex);
            return false;
        }
    }
    
    /**
     * Demande de suppression d'un cluster
     * @return tag de redirection (liste)
     */
    @Override
    public String delete() {
        if (!getStrArgumentValue("TRM_CLUSTER_ID").equals("")) {
            if (!daoDelete(getStrArgumentValue("TRM_CLUSTER_ID"))) {
                this.addDisplayMessageError("00_DELETE_RELTERMMETRIC_KO");
            } else
                this.addDisplayMessageInfo("00_DELETE_RELTERMMETRIC_OK");
        }
        return this.list();
    }
    
    /**
     * Effectue l'update d'une metrique (seule)
     * @return faux si erreur
     * @obsolete
     */
    private boolean daoUpdate() {
        try {
            String tmdpk = getStrArgumentValue("Final_TMD_PK");
            //String oldval = getStrArgumentValue("Final_Old_Metric");
            String newval = getStrArgumentValue("Final_New_Metric");
            
            BOEntityReadWrite entity = (BOEntityReadWrite)this.getBOFactory().getEntity("REL_TERM_METRIC");
            entity.useNoFields();
            entity.field("TMD_PK").useThisField();
            entity.field("MET_NAME").useThisField();
            entity.field("MET_NAME").setValue(newval);
            entity.field("TMD_PK").setKeyValue(Integer.valueOf(tmdpk));
            entity.update();
            
        } catch (Exception  e) {
            Joy.log().error(e);
            return false;
        }
        return true;
    }

    /**
     * Effectue l'update d'un cluster dans la BD
     * @return faux si erreur
     * @obsolete
     */
    private boolean daoUpdateAllCluster() {
        String saveMET;
        try {
            int nbItems = Integer.parseInt(getStrArgumentValue("nbItems"));
            for (int i=1; i <= nbItems; i++) {
                String prefix = i + "_";
                saveMET = getStrArgumentValue(prefix + "MET_NAME");

                String sDesc = getStrArgumentValue(prefix + "TMD_DESCRIPTION");
                BOEntityReadWrite entity = (BOEntityReadWrite)this.getBOFactory().getEntity("REL_TERM_METRIC");
                entity.field("TRM_CLUSTER_ID").doNotUseThisField();
                entity.field("TMD_PK").doNotUseThisField();
                entity.field("MET_NAME").setValue(saveMET);
                entity.field("TMD_DESCRIPTION").setValue((sDesc.equals("null") || sDesc.equals("") ? "" : sDesc));
                entity.field("TRM_NAME").setKeyValue(getStrArgumentValue(prefix + "TRM_NAME"));
                entity.field("DQX_NAME").setKeyValue(getStrArgumentValue(prefix + "DQX_NAME"));
                entity.update();
            }
            
        } catch (NumberFormatException e) {
            Joy.log().error(e);
            return false;
        }
        return true;
    }
    
    /**
     * Demande de mise à jour (edit/add) d'un cluster
     * @return tag de redirection LIST
     */
    @Override
    public String update() {
        // Mise a jour d'un item
        if (daoUpdate()) 
            this.addDisplayMessageInfo("00_UPDATED_RELTERMMETRIC_OK");
        else
            this.addDisplayMessageError("00_UPDATED_RELTERMMETRIC_KO");

        // Rafraichit les liens dans les tables de fait
        RefreshAllLinkage();
        
        return this.edit(); 
    }

    /**
     * Liste des termes non référencés encore
     * @return chaine avec séparation virgule
     */
    private void loadListOfAvailableTerms() {
        IEntity entity = this.getBOFactory().getEntity("Available Terms");
        ResultSet rs = entity.selectAll();
        this.loadVector(rs, "TERM_NAME", "TERM_NAME", "TERM_NAME", null);
        this.getBOFactory().closeResultSet(rs);
    }
    
    /**
     * Liste des Metrics disponibles (non encore affectées)
     * @return liste des metrics séparées par des virgules
     */
    private void loadListOfAvailableMetrics() {
        String line = "";
        
        try {
            IEntity entity = this.getBOFactory().getEntity("Available Metrics");
            ResultSet rs = entity.selectAll();
            while (rs.next()) {
                line += "{id: '" + rs.getString("MET_NAME") + "', text:'" + rs.getString("MET_NAME") + "'},";
            }
            line = "[" + line.substring(0, line.length()-1) +  "]";
            this.getBOFactory().closeResultSet(rs);

        } catch (SQLException e) {
            Joy.log().error(e);
        }

        this.addFormSingleEntry("AVAILABLE_METRICS", line);
    }
    
    /**
     * Ajoute un nouveau terme/cluster
     * @param _TRM_NAME Nom du Terme
     * @return ID du cluster
     */
    private int daoAddTerm(String _TRM_NAME) {
        int iCluster = 0;
        
        try {
            IEntity entityAxis = this.getBOFactory().getEntity("SRC_DQAXIS");
            ResultSet rs = entityAxis.selectAll();
            BOEntityReadWrite entity = (BOEntityReadWrite)this.getBOFactory().getEntity("REL_TERM_METRIC");
            iCluster = entity.field("TRM_CLUSTER_ID").getNextID();
            while (rs.next()) {
                entity.init();
                entity.field("TMD_PK").setNextIDValue();
                entity.field("TMD_DESCRIPTION").setValue("");
                entity.field("TRM_CLUSTER_ID").setValue(iCluster);
                entity.field("MET_NAME").setValue(null);
                entity.field("TRM_NAME").setValue(_TRM_NAME);
                entity.field("DQX_NAME").setValue(rs.getString("DQX_LABEL"));
                entity.insert();
            }
            this.getBOFactory().closeResultSet(rs);
            return iCluster;
            
        } catch (SQLException ex) {
            Joy.log().error( ex);
            return 0;
        }
    }

    /**
     * Classe pour gérer l'auto link
     */
    private class RelationShip {
        public String TRM_NAME;
        public String DQX_NAME;
        public String DQX_CODE;

        public RelationShip(String TRM_NAME, String DQX_NAME, String DQX_CODE) {
            this.TRM_NAME = TRM_NAME;
            this.DQX_NAME = DQX_NAME;
            this.DQX_CODE = DQX_CODE;
        }

    }

    /**
     * retire les caractères non nécessaires pour préparer à la comparaison
     * @param s chaine a traitée
     * @return chaine traitée
     */
    private String PrepareToComparison(String s) {
            String fin = s;
            fin = fin.replace(" ", "");
            fin = fin.replace("_", "");
            fin = fin.replace("@", "");
            fin = fin.replace("-", "");
            return fin;
    }
    
    /**
     * Créé la liste des dimensions disponibles
     * @return liste des dimensions disponibles
     */
    private List<RelationShip> getAvailableDimensions() {
        try {
            IEntity entity = this.getBOFactory().getEntity("Metric Condidate for autolink");
            List<RelationShip> tab = new ArrayList();
            ResultSet rs = entity.selectAll();
            
            while (rs.next()) {                 
                RelationShip rel = new RelationShip(rs.getString("TRM_NAME"), rs.getString("DQX_NAME"), rs.getString("DQX_CODE"));
                tab.add(rel);
            }
            this.getBOFactory().closeResultSet(rs);
            return tab;
            
        } catch (SQLException ex) {
            Joy.log().error( ex);
            return new ArrayList();
        }
    }

    /**
     * Effectue le controle pour savoir si s2 est inclu dans s1
     * @param s1 Chaine englobante
     * @param s2 chaine englobée
     * @return vrai si s2 est dans s1
     */
    private boolean myContain(String s1, String s2) {
        if (s1 == null)
            return false;
        if (s2 == null)
            return false;   
        String ref1 = s1.toUpperCase();
        String ref2 = s2.toUpperCase();
        
        return ref1.contains(ref2);
    }
    
    /**
     * Effectue le processus d'auto-link automatiquement.
     */
    private void AutoLink() {
        List<RelationShip> candidates = getAvailableDimensions();
        boolean noUpdate = true;
        try {
            BOEntityReadWrite entity = (BOEntityReadWrite)this.getBOFactory().getEntity("DIM_METRIC");
            ResultSet rs = entity.selectAll();

            while (rs.next()) { // parcours les metriques 
                for (RelationShip candidat : candidates) { // parcours les metrics
                    String trm = PrepareToComparison(candidat.TRM_NAME);
                    String metric = PrepareToComparison(rs.getString("MET_NAME"));
                    if (myContain(metric, trm) && (myContain(metric, candidat.DQX_CODE) || myContain(metric, candidat.DQX_NAME)) )  { // la metrique contien le nom du terme & l'axe
                        // Mise à jour !
                        BOEntityReadWrite maj = (BOEntityReadWrite)this.getBOFactory().getEntity("REL_TERM_METRIC");
                        maj.field("TRM_CLUSTER_ID").doNotUseThisField();
                        maj.field("TRM_NAME").setKeyValue(candidat.TRM_NAME);
                        maj.field("DQX_NAME").setKeyValue(candidat.DQX_NAME);
                        maj.field("MET_NAME").setValue(rs.getString("MET_NAME"));
                        maj.field("TMD_PK").setValue(rs.getInt("TMD_PK"));
                        maj.update();
                        this.addDisplayMessageInfo("RelationShip [" + candidat.TRM_NAME + "," + candidat.DQX_NAME + "," + rs.getString("MET_NAME") + "] updated successfully.");
                        noUpdate = false;
                    }
                }
            }
            if (noUpdate)
                this.addDisplayMessageInfo("00_NO_UPDATES_RELTERMMETRIC");
            this.getBOFactory().closeResultSet(rs);

        } catch (SQLException ex) {
            Joy.log().error( ex);
            this.addDisplayMessageError("00_UPDATED_RELTERMMETRIC_KO");
        }
    }
    
    /**
     * Retourne la PK d'une métrique selon son name
     * @param MET_NAME
     * @return MET_PK
     */
    private int getMetPKFromMetName(String metname) {
        int PK=0;
        try {
            
            IEntity entityMet = this.getBOFactory().getEntity("DIM_METRIC");
            entityMet.field("MET_NAME").setKeyValue(metname);
            ResultSet rs = entityMet.selectFiltered();
            if (rs.next()) {
                PK = rs.getInt("MET_PK");
            }
            this.getBOFactory().closeResultSet(rs);
            
        } catch (SQLException ex) {
            Joy.log().error( ex);
        }
        return PK;
    }

    /**
     * retourne la clé TRM_PK à partir du Cluster ID TRM_CLUSTER_ID
     * @param ClusterID TRM_CLUSTER_ID
     * @return TRM_PK
     */
    private int getTrmPKFromClusterID(String ClusterID) {
        int PK=0;
        try {
            IEntity entityMet = this.getBOFactory().getEntity("Rel Term Metric Cluster");
            entityMet.field("TRM_CLUSTER_ID").setKeyValue(Integer.valueOf(ClusterID));
            ResultSet rs = entityMet.selectFiltered();
            if (rs.next()) {
                PK = rs.getInt("TRM_PK");
            }
            this.getBOFactory().closeResultSet(rs);
            
        } catch (SQLException ex) {
            Joy.log().error( ex);
        }
        return PK;
    }
    
    private int getDqxPKFromDqxName(String DqxName) {
        int PK=0;
        try {
            IEntity entityMet = this.getBOFactory().getEntity("DIM_DQAXIS");
            entityMet.field("DQX_NAME").setKeyValue(DqxName);
            ResultSet rs = entityMet.selectFiltered();
            if (rs.next()) {
                PK = rs.getInt("DQX_PK");
            }
            this.getBOFactory().closeResultSet(rs);
            
        } catch (SQLException ex) {
            Joy.log().error( ex);
        }
        return PK;
    }
    
    /**
     * 1) récupère les PK des metrics old & new
 2) MAJ/update des Facts avec pour critère old ID --> Set New ID
     *       Modification de metrique existante (mais ne rajoute pas de metrique si elle n'existait pas)
     *       récupérer le fait via oldval et basculer TRM_FK & DQX_FK à zéro
     *       récupérer le fait via newval et basculer TRM_FK & DQX_FK avec les nouvelles valeurs
     */
    private void RefreshAllLinkage() {
        
        // 1) récupère les PK des metrics old & new
        int oldval = getMetPKFromMetName(getStrArgumentValue("Final_Old_Metric"));
        int newval = getMetPKFromMetName(getStrArgumentValue("Final_New_Metric"));
        int TRM_FK = getTrmPKFromClusterID(getStrArgumentValue("TRM_CLUSTER_ID"));
        int DQX_FK = getDqxPKFromDqxName(getStrArgumentValue("Final_DQAxis"));
        
        //2) MAJ/Update des Facts avec pour critère old ID --> Set New ID
        // MAJ la table de fait
        BOEntityReadWrite entityMAJ = (BOEntityReadWrite)this.getBOFactory().getEntity("All Facts");
        entityMAJ.useNoFields();
        entityMAJ.field("TRM_FK").useThisField();
        entityMAJ.field("DQX_FK").useThisField();
        entityMAJ.field("MET_FK").useThisField();
        entityMAJ.field("TRM_FK").setValue(0);
        entityMAJ.field("MET_FK").setKeyValue(oldval);
        entityMAJ.field("DQX_FK").setValue(0);
        entityMAJ.update();

        entityMAJ = (BOEntityReadWrite)this.getBOFactory().getEntity("All Facts");
        entityMAJ.useNoFields();
        entityMAJ.field("TRM_FK").useThisField();
        entityMAJ.field("DQX_FK").useThisField();
        entityMAJ.field("MET_FK").useThisField();
        entityMAJ.field("TRM_FK").setValue(TRM_FK);
        entityMAJ.field("MET_FK").setKeyValue(newval);
        entityMAJ.field("DQX_FK").setValue(DQX_FK);
        entityMAJ.update();
        
        this.addDisplayMessageInfo("Metrics & terms Linkages refreshed successfully." );
    }
    

    /**
     * Tente de créer une relation auto pour les termes sélectionnée règle de match :
     *       -> Si DQX_CODE, DQX_NAME dans la chaine metric
     *       -> Si TRM_NAME (avec tous les espaces retirés) dans la chaine metric
     * @param ActionType si AUTOLINK --> process d'auto link
     *                   si REFRESHLINKAGE --> process de rafraichissement du linkage (FACTs)
     * @return 
     */
    @Override
    public String execute(String ActionType) {
        
        if (ActionType.equalsIgnoreCase("AUTOLINK")) { // auto link
            AutoLink();
            return super.list();
               
        } else if (ActionType.equalsIgnoreCase("REFRESHLINKAGE")) { // Refresh linkage
            if(!this.getStrArgumentValue("ClusterID").equals(""))
                RefreshAllLinkage();
            return this.list();
        } else
            return this.list();
    }

    
}
