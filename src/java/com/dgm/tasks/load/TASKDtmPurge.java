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
package com.dgm.tasks.load;

import com.dgm.common.providers.ParamProvider;
import com.joy.Joy;
import com.joy.tasks.JoyTaskStatus;

/**
 *
 * @author Benoit CAYLA <benoit@famillecayla.fr>
 */
public class TASKDtmPurge extends TASKCommonRAZ {

    @Override
    protected void init() {
        super.init();
    }
    
    @Override
    public JoyTaskStatus taskExecute() {
        try {
            ParamProvider myParams = new ParamProvider(this.getEntities());
            
            // vide les tables
            dtmpurge();

            // initialisation des données
            init();
            
            super.taskExecute();
            this.setMessage("DataMart purged.");
            
        } catch (Exception e) {
            Joy.log().fatal(e);
            this.setMessage(e.toString());
            return JoyTaskStatus.Failed;
        }
        return JoyTaskStatus.Success;
    }
    
}
