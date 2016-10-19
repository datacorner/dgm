/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.form.analytics.global;

/**
 *
 * @author Administrator
 */
public class ReportGlobalCostsAction extends ReportGlobalInvalidsAndCosts {

    @Override
    public String display() {
        fieldValue = "COSTBYTERM";
        return super.display(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
