/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.beans;

import com.dgm.common.Constants;

/**
 *
 * @author Benoit CAYLA benoit@famillecayla.fr
 */
public class ParamBean {
    private String strValue;
    private int intValue;
    private String Name;
    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ParamBean() {
        this.Name = "";
        intValue = -1;
        strValue = Constants.PARAM_NO_VALUE;
    }
    
    public ParamBean(String Name) {
        this.Name = Name;
        intValue = -1;
        strValue = Constants.PARAM_NO_VALUE;
    }

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

}
