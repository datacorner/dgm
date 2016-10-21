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
package com.dgm.rest;

import com.joy.bo.BOField;
import com.joy.mvc.actionTypes.ActionTypeREST;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr)
 */
public class RESTDataCommon extends ActionTypeREST {
    
    /**
     * Get the entity whatever it is
     * @param Name
     * @return 
     */
    private IEntity RetrieveEntity(String Name) {
        IEntity entity = null;
        try {
            entity = this.getBOFactory().getEntity(Name);
        } catch (Exception e) {}
        return entity;
    }
    
    /**
     * Build the entity with all the filters
     * P1 = Entity name
     * (P2, P3) = (Column Name, Column Value) to define the criterias
     * (P4, P5)
     * (P6,P7) etc.
     * @param paramIndexFrom Define the first parameter into the URL query (first is entity)
     * @return entity filtered
     */
    protected IEntity getEntityFromPOST(int paramIndexFrom) {
        int i = paramIndexFrom + 1; // 2
        boolean hasPair = true;
        
        try {
            IEntity entity = RetrieveEntity(this.getStrArgumentValue("P" + paramIndexFrom));
            
            while (hasPair) {
                String argName = this.getStrArgumentValue("P" + i++);
                String argValue = this.getStrArgumentValue("P" + i++);
                hasPair = (!argName.isEmpty() &&  !argValue.isEmpty());
                if (hasPair) {
                    // Filter the result
                    BOField field =  entity.field(argName);
                    if (field != null) 
                        switch (field.getFieldType()) {
                            case fieldInteger:  field.setKeyValue(Integer.valueOf(argValue)); break;
                            case fieldBoolean:  field.setKeyValue((Integer.valueOf(argValue) != 0)); break;
                            case fieldDate:     field.setKeyValue(argValue); break;
                            case fieldFloat:    field.setKeyValue(Float.valueOf(argValue)); break;
                            case fieldString:   
                            default:            field.setKeyValue(argValue); 
                        }
                } 
            }
            return entity;
            
        } catch (Exception e) {
            return null;
        }
    }
}
