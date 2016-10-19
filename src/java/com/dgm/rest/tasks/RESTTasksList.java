/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.rest.tasks;

import com.joy.Joy;
import com.joy.mvc.actionTypes.ActionTypeREST;

/**
 *
 * @author Administrator
 */
public class RESTTasksList extends ActionTypeREST {

    @Override
    public String restGet() {
        int limit = 0;
        try {
            limit = this.getIntArgumentValue("P1");
        } catch (Exception e) {}
        
        try {
            return Joy.taskManager().getJSONTasksDesc(limit); 
        } catch (Exception e) {
            Joy.log().fatal(e);
            return "";
        }
    }
    
}
