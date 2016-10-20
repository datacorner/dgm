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
package com.dgm.common.providers;

import com.dgm.beans.ParamBean;
import com.dgm.common.Constants;
import com.joy.Joy;
import com.joy.bo.BOFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.joy.bo.IEntity;

/**
 * 
 * @author Benoit CAYLA <benoit@famillecayla.fr>
 */
public class ParamProvider {
    BOFactory entities;

    public ParamProvider(BOFactory entities) {
        this.entities = entities;
    }
    
    public boolean lockWorkflow() {
        ParamBean bean = new ParamBean(Constants.LOCK_WORKFLOW);
        bean.setIntValue(Constants.LOCKED);
        return setParamValue(bean);
    }
    
    public boolean unlockWorkflow() {
        ParamBean bean = new ParamBean(Constants.LOCK_WORKFLOW);
        bean.setIntValue(Constants.UNLOCKED);
        return setParamValue(bean);
    }
    
    public boolean isWorklowLocked() {
        return (getParamValue(Constants.LOCK_WORKFLOW).getIntValue() == Constants.LOCKED);
    }
    
    public boolean activateScheduler() {
        ParamBean bean = new ParamBean(Constants.ACTIVE_SCHEDULER);
        bean.setIntValue(Constants.ACTIVATED);
        return setParamValue(bean);
    }
    
    public boolean deactivateScheduler() {
        ParamBean bean = new ParamBean(Constants.ACTIVE_SCHEDULER);
        bean.setIntValue(Constants.DEACTIVATED);
        return setParamValue(bean);
    }
    
    public boolean isSchedulerActivated() {
        return (getParamValue(Constants.ACTIVE_SCHEDULER).getIntValue() == Constants.ACTIVATED);
    }
    
    public ParamBean getParamValue(String _Name) {
        ParamBean result = new ParamBean(_Name);

        try {
            IEntity entity = entities.getEntity("APP_PARAMS");
            entity.field("PARAM_NAME").setKeyValue(_Name);
            ResultSet rs = entity.selectFiltered();

            if (rs.next()) {
                result.setStrValue(rs.getString("PARAM_STRVALUE"));
                result.setIntValue(rs.getInt("PARAM_INTVALUE"));
            }
            entities.closeResultSet(rs);
            
        } catch (SQLException e) {
            Joy.log().error("Issue while getting parameter " + _Name + " Error is -> " +  e);
        }
        return result;
    }
    
    public boolean setParamValue(ParamBean _bean) {
    	try {
            IEntity entity = entities.getEntity("APP_PARAMS");
            entity.field("PARAM_NAME").setKeyValue(_bean.getName());
            entity.field("PARAM_STRVALUE").setValue(_bean.getStrValue());
            entity.field("PARAM_INTVALUE").setValue(_bean.getIntValue());
            entity.field("PARAM_DISPLAY").doNotUseThisField();
            entity.update();

            return true;
            
        } catch (Exception e) {
            Joy.log().error(e);
            return false;
        }
    }

}
