/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.rest.actions;

import com.dgm.rest.RESTDataCommon;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA
 * Retourne le contenu d'une table au format JSON
 * P1 : nom de la table
 * http://localhost:18180/GovManagementTool/rest/data/[Nom de table]
 */
public class RESTData extends RESTDataCommon {

    @Override
    public String restGet() {
        
        IEntity entity = this.getEntityFromPOST(1);
        if (entity != null)
            return entity.exportJsonWithFilter().toString();
        else
            return "";
    }
}
