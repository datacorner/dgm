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
package com.dgm.rest.actions;

import com.dgm.rest.RESTPairMgtCommon;
import com.joy.C;
import com.joy.Joy;
import com.joy.bo.BOFieldType;
import com.joy.bo.IEntity;

/**
 * search terms with criterias
 * call with ./rest/search/[Entity]/[criteria 1]/[Value 1]/[criteria 2]/[Value 2]
 *           Example : http://localhost:18080/dgm/rest/search/term/termtypeid/3
 * @author Benoit CAYLA (benoit@famillecayla.fr)
 */

public class RESTSearch extends RESTPairMgtCommon {

    @Override
    public String restGet() {
        String searchEntity = this.getStrArgumentValue("P1");
        return searchByEntity(searchEntity.toUpperCase());
    }
    
    /**
     * Search for a TERM
     * @return JSON Term's list
     */
    private String searchByEntity(String entityName) {
        try {
            IEntity entityTerm = this.getBOFactory().getEntity(entityName);

            // Manage Criterias for entity
            this.CollectPairs(2);
            for (int i=0; i< this.getPairsCount(i); i++) {
                String ColName = this.getPair(i).getName().toUpperCase();
                BOFieldType colType = entityTerm.field(ColName).getFieldType();
                switch (colType) {
                    case fieldBoolean: 
                    case fieldInteger: 
                        int ii = 0;
                        try {
                            ii= Integer.valueOf(this.getPair(i).getValue());
                        } catch (Exception e) { }
                        entityTerm.field(ColName).setKeyValue(ii);
                        break;

                    case fieldFloat:
                        float j = 0;
                        try {
                            j= Float.valueOf(this.getPair(i).getValue());
                        } catch (Exception e) { }
                        entityTerm.field(ColName).setKeyValue(j);
                        break;

                    case fieldDate:
                    case fieldString: 
                    default: 
                        entityTerm.field(ColName).setKeyValue(this.getPair(i).getValue());
                }

            }
            Joy.log().info("REST search successful. Return JSON data.");
            return entityTerm.exp().toString();
            
        } catch (Exception e) {
            Joy.log().error(e);
            return C.JSON_EMPTY;
        }
    }
    
}
