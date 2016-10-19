/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.dashboard;

import com.joy.dashboard.Dashboard;
import com.joy.dashboard.Dashboards;
import com.joy.Joy;
import com.joy.mvc.actionTypes.ActionTypeForm;
import com.joy.mvc.formbean.JoyFormVectorEntry;

/**
 *
 * @author Administrator
 */
public class DashboardAction  extends ActionTypeForm {

    @Override
    public String display() {
        String dashbSelected = this.getStrArgumentValue("CBO_DASHBOARDS");
        Dashboards dashbs = new Dashboards();
        dashbs.init(Joy.parameters().getParameter("dashboardconfig").getValue().toString()); 

        if (!dashbSelected.isEmpty())
            this.addFormSingleEntry("DASHBOARD", dashbs.getDashboard(dashbSelected));
        else {
            dashbSelected = dashbs.getDefaultDashboard().getID();
            this.addFormSingleEntry("DASHBOARD", dashbs.getDefaultDashboard());
        }
        loadCboDashboards(dashbs, dashbSelected);
        
        return super.display(); 
    }
    
    /**
     * Fill the dashboard selection combox box
     * @param dashbs
     * @param selected 
     */
    private void loadCboDashboards(Dashboards dashbs, String selected) {
        JoyFormVectorEntry cbo = new JoyFormVectorEntry();
        for (Dashboard ds : dashbs.getList()) {
            cbo.addValue(ds.getName(), ds.getID(), ds.getTitle());
        }
        cbo.setSelected(selected);
        this.addFormVectorEntry("CBO_DASHBOARDS", cbo);
    }
    
}
