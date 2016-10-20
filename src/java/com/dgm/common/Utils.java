/*
 * Copyright (C) 2016 Benoit CAYLA <benoit@famillecayla.fr>
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
package com.dgm.common;

import com.joy.Joy;
import com.joy.bo.BOFactory;
import java.sql.ResultSet;
import com.joy.bo.IEntity;

/**
 * This class contains all the statics utilities
 * @author Benoit CAYLA <benoit@famillecayla.fr> 
 */
public class Utils {
    
    /** 
     * Score display format
     * @param val
     * @return 
     */
    public static String scoreDisplay(Float val) {
        return String.format(Constants.FORMAT_FLOAT, val) + "%";
    }
    
    /**
    * retourne la ligne de commande d'exécution d'un workflow
     * @param infacmd infacmd.bat path & filename
     * @param infadomain
     * @param infadis
     * @param infauser
     * @param infapwd
     * @param infaapp
     * @param workflow
     * @return 
    */
    public static String getInformaticaWorkflowCommandLine(String infacmd,
                                                           String infadomain,
                                                           String infadis,
                                                           String infauser,
                                                           String infapwd,
                                                           String infaapp,
                                                           String workflow) {
        String cmd  = "";
        Joy.log().debug ("Action type=Workflow");

        // infacmd.bat wfs startWorkflow -dn Domain_WIN2K8 -sn DIS -un Administrator -pd Administrator -a App_Governance_Framework -wf wf_Full_Delta -w true
        cmd += infacmd + " wfs startWorkflow";
        cmd += " -dn " + infadomain;
        cmd += " -sn " + infadis;
        cmd += " -un " + infauser;
        cmd += " -pd " + infapwd;
        cmd += " -a " + infaapp;
        cmd += " -wf " + workflow;
        cmd += " -w true";

        return cmd;
    }
    
    /**
     * Return the Term's icon
     * @param entities
     * @param Glossary
     * @return 
    */
    public static String getTermTypeIcon(BOFactory entities, String Glossary) {
        try {
            IEntity entity = entities.getEntity("SRC_TERMTYPE");
            entity.field("GIO_TERMTYPE_NAME").setKeyValue(Glossary);
            String result;
            
            ResultSet rs = entity.selectFiltered();
            if (rs.next()) 
                result = rs.getString("GIO_ICON_PATHNAME");
            else
                result =  Constants.DEFAULT_TERMTYPE_ICON;
            
            entities.closeResultSet(rs);
            return result;
            
        } catch (Exception e) {
            Joy.log().error(e);
            return Constants.DEFAULT_TERMTYPE_ICON;
        }
    } 
    
}
