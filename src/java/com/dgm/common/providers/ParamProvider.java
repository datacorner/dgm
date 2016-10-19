/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.common.providers;

import com.dgm.beans.ParamBean;
import com.dgm.common.Constants;
import com.joy.Joy;
import com.joy.bo.BOFactory;
import com.joy.bo.BOEntityReadOnly;
import com.joy.bo.BOEntityReadWrite;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA benoit@famillecayla.fr
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
