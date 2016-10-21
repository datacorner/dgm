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
package com.dgm.form.admin;

import com.joy.Joy;
import com.joy.common.ActionLogReport;
import com.joy.json.JSONObject;
import com.joy.mvc.actionTypes.ActionTypeForm;
import java.io.IOException;
import java.util.Collection;
import com.joy.bo.IEntity;

/**
 * This class manages the imports screen
 * @author Benoit CAYLA (benoit@famillecayla.fr)
 */
public class ImportAction extends ActionTypeForm {

    @Override
    public String list() {
        if (this.hasAttachedFiles()) {
            String stringContent = "";
            try {
                // importe les fichiers attachés et récupère le contenu dans un String
                stringContent = Joy.convertInputStreamToString(this.attachedFile(0));
            } catch (IOException ex) {
                Joy.log().error(ex);
            }
            
            if (!stringContent.isEmpty()) {
                JSONObject jsonContent = new JSONObject(stringContent);
                IEntity entity = this.getBOFactory().getEntity(this.getStrArgumentValue("import"));
                Collection<ActionLogReport> logs = entity.importJson(jsonContent, false);

            } else {
                Joy.log().info("The imported file is empty");
            }
        }
        return super.list(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
