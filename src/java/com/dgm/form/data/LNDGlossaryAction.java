/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.form.data;

import com.joy.bo.IEntity;

/**
 *
 * @author benoit
 */
public class LNDGlossaryAction extends LNDCommonAction {

    public LNDGlossaryAction() {
        this.LandingKeyName = "JOYFUNCKEY";
        this.LandingTableName = "LND_GLOSSARY";
    }
    
    @Override
    public void updateSpecific(IEntity Entity) {
        Entity.field("GLOSSARY_NAME").setValue(getStrArgumentValue("GLOSSARY_NAME"));
        Entity.field("GLOSSARY_DESCRIPTION").setValue(getStrArgumentValue("GLOSSARY_DESCRIPTION"));
    }
}
