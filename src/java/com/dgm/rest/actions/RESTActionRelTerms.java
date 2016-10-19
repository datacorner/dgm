/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.rest.actions;

import com.dgm.beans.termreltree.TermTree;
import com.dgm.beans.termreltree.TermBean;
import com.joy.mvc.actionTypes.ActionTypeREST;

/**
 * @author Benoit CAYLA
 * retourne la hiérarchie du terme avec ses relations pour un affichage avec vis.js
 * http://localhost:18180/GovManagementTool/rest/relterm/[TRM_PK]
 */
public class RESTActionRelTerms extends ActionTypeREST{

    @Override
    public String restGet() {
        
        String stream = "[ {\"text\": \"Nothing\"} ]";
        TermTree mytree = new TermTree(this.getBOFactory());
        TermBean ars = mytree.build(this.getIntArgumentValue("P2"), 1, getIntArgumentValue("P1"));
        stream = "[ " + ars.getJSONObject(this.getURI() + "/images/glossary/").toString() + "]"; //To change body of generated methods, choose Tools | Templates.
        
        return stream;
    }
    
}
