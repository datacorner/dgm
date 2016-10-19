/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.form.admin;

import com.joy.Joy;
import com.joy.bo.BOEntityReadWrite;
import com.joy.common.ActionLogReport;
import com.joy.json.JSONObject;
import com.joy.mvc.actionTypes.ActionTypeForm;
import java.io.IOException;
import java.util.Collection;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA
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
