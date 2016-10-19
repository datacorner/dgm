/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.ui;

import com.joy.Joy;

/**
 *
 * @author Administrator
 */
public class UITaglibSpotData {
    private String bigshorttext;
    private String littlelongtext;
    private String linktext;
    private String linkURL;
    private String panelcolor;
    private String panelicon;

    public String getBigshorttext() {
        return bigshorttext;
    }

    public void setBigshorttext(String bigshorttext) {
        this.bigshorttext = bigshorttext;
    }

    public String getLittlelongtext() {
        return littlelongtext;
    }

    public void setLittlelongtext(String littlelongtext) {
        this.littlelongtext = littlelongtext;
    }

    public String getLinkURL() {
        return linkURL;
    }

    public void setLinkURL(String object, String actiontype) {
        this.linkURL = Joy.basicURL(object, actiontype);
    }

    public String getPanelcolor() {
        return panelcolor;
    }

    public void setPanelcolor(String panelcolor) {
        this.panelcolor = panelcolor;
    }

    public String getPanelicon() {
        return panelicon;
    }

    public String getLinktext() {
        return linktext;
    }

    public void setLinktext(String linktext) {
        this.linktext = linktext;
    }

    public void setPanelicon(String panelicon) {
        this.panelicon = panelicon;
    }
    
    
}
