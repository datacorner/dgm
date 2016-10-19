package com.dgm.rest.actions;

import com.dgm.rest.RESTPairMgtCommon;
import com.joy.C;
import com.joy.Joy;
import com.joy.bo.BOEntityReadOnly;
import com.joy.bo.BOFieldType;
import com.joy.bo.IEntity;

/**
 * search terms with criterias
 * call with ./rest/search/[Entity]/[criteria 1]/[Value 1]/[criteria 2]/[Value 2]
 *           Example : http://localhost:18080/dgm/rest/search/term/termtypeid/3
 * @author B.Cayla
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
            return entityTerm.exportJsonWithFilter().toString();
            
        } catch (Exception e) {
            Joy.log().error(e);
            return C.JSON_EMPTY;
        }
    }
    
}
