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
package com.dgm.form.data;

import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr)
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
