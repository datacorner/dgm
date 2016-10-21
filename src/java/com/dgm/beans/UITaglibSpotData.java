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
package com.dgm.beans;

import com.joy.Joy;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr)
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
