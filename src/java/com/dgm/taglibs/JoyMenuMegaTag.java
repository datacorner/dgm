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
package com.dgm.taglibs;

import com.joy.C;
import com.joy.Joy;
import com.joy.mvc.Action;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.jdom2.Element;


/**
 * Taglib d'affichage d'un menu :
 * 
 *    Chaque element de menu est placé dans un tag <joy-menu></joy-menu>
 *    Attributs du tag:
 *    - name (obligatoire) : nom du menu
 *    [choix]
 *    - url (obligatoire): URL du menu (http://)
 *    Ou
 *    - object (obligatoire) nom du tag Object (joy)
 *    - actiontype (facultatif) nom de l'actiontype (joy)
 *    - parametres (entier x de 1 à ...)
 *        - pnx= Nom du parametre x
 *        - pnv= Valeur du parametre x
 * 
 * @author Benoit CAYLA (benoit@famillecayla.fr)
 */
public class JoyMenuMegaTag extends SimpleTagSupport {
    private boolean display;    // affichage ou non
    private String cssDivMenu;  // ID du CSS du menu (tag DIV)
    private String xmlConfig;   // fichier de configuration
    private String cssID;       // ID du CSS du menu (tag LU) / exemple = menu-v
    
    public String getCssID() {
        return cssID;
    }

    public void setCssID(String cssID) {
        this.cssID = cssID;
    }

    public String getCssDivMenu() {
        return cssDivMenu;
    }

    public void setCssDivMenu(String cssDivmenu) {
        this.cssDivMenu = cssDivmenu;
    }
    
    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public String getXMLConfig() {
        return xmlConfig;
    }

    public void setXMLConfig(String XMLConfig) {
        this.xmlConfig = XMLConfig;
    }
    
    /**
     * Construit un morceau d'url avec les parametres seulement (x étant un chiffre)
 PNx : Nom du parametre
 PVx : Valeur du parametre
     * @param element
     * @return 
     */
    private String buildURLParameter(Element element) {
        int i=1;
        String urlPart = "&";
        while (element.getAttributeValue("pn" + i) != null) {
            urlPart += element.getAttributeValue("pn" + i) + "=" + element.getAttributeValue("pv" + i) + "&";
            i++;
        }
        return urlPart.substring(0, urlPart.length()-1);
    }
    
    /**
     * Buil a href hyperlink
     * @param liURL
     * @param liObject
     * @param liActionType
     * @param liName
     * @param child
     * @return 
     */
    private String buildAHREFTag(String liURL, 
                                 String liObject, 
                                 String liActionType, 
                                 String liName, 
                                 Element child) {
        String menu = "";

        if (liURL == null) {
            if (liObject != null) {
                menu += "<A href='." + Joy.parameters().getJoyDefaultURLPattern() + "?";
                if (liObject != null)
                    menu += C.ACTION_TAG_OBJECT + "=" + liObject + "&";
                if (liActionType!= null)
                    menu += C.ACTION_TYPE_TAG + "="  + liActionType;
                menu += buildURLParameter(child);
                menu +=  "'>" + liName + "</A>"; 
            } else { 
                menu += "<A href='#'>" + liName + "</A>";
            }
        } else { 
            menu += "<A href='" + liURL + "'>" + liName + "</A>";
        }
        return menu;
    }
    
    /**
     * Fonction de construction de blocs HTML de menu
     * @param bloc Bloc courant (donnée récurssive)
     * @return Chaine contenant le code HTML complete avec les éléments enfants
     */
    private String buildTopMenuBloc(List bloc) {
        String menu = "";
        if (!bloc.isEmpty()) {
            menu = "<UL class='nav' role='menubar'>" + "\n";
            Iterator root = bloc.iterator();
            
            while(root.hasNext()) { // Get all elements
                Element child = (Element)root.next();
                String liObject = child.getAttributeValue(C.ACTION_TAG_OBJECT);
                String liActionType = child.getAttributeValue(C.ACTION_TYPE_TAG);
                String liName = child.getAttributeValue("name");
                String liURL = child.getAttributeValue("url");
                String liType= child.getAttributeValue("type");
                
                if (liType == null) {
                    // Level 1 item
                    menu += "<li role='menuitem'>" + "\n";
                    menu += buildAHREFTag(liURL, liObject, liActionType, liName, child);

                    // Level 2 items exists ?
                    List childs = child.getChildren("joy-menu");
                    if (!childs.isEmpty()) {
                        int nbItems = (childs.size() > 3 ? 4 : childs.size());
                        // Add columns into the menu
                        menu += "<DIV class='mega-menu" + nbItems + "' aria-hidden='true' role='menu'>" + "\n";
                        
                        Iterator columns = childs.iterator();
                        while(columns.hasNext()) {
                            Element column = (Element)columns.next();
                            menu += "<DIV class='nav-column'>" + "\n";
                            menu += "<H3>";
                            if (column.getAttributeValue("url") == null || column.getAttributeValue(C.ACTION_TAG_OBJECT) == null) {
                                menu += column.getAttributeValue("name");
                            } else
                                menu += buildAHREFTag(column.getAttributeValue("url"), 
                                                      column.getAttributeValue(C.ACTION_TAG_OBJECT), 
                                                      column.getAttributeValue(C.ACTION_TYPE_TAG), 
                                                      column.getAttributeValue("name"), 
                                                      column);
                            menu += "</H3>" + "\n";
                            // Get the sub items here
                            List items = column.getChildren("joy-menu");
                            if (!items.isEmpty()) {
                                menu += "<UL>" + "\n";
                                Iterator itemsIt = items.iterator();
                                while(itemsIt.hasNext()) {
                                    Element item = (Element)itemsIt.next();
                                    menu += "<LI role='menuitem'>" + "\n";
                                    menu += buildAHREFTag(item.getAttributeValue("url"), 
                                                          item.getAttributeValue(C.ACTION_TAG_OBJECT), 
                                                          item.getAttributeValue(C.ACTION_TYPE_TAG), 
                                                          item.getAttributeValue("name"), 
                                                          item);
                                    menu += "</LI>" + "\n";
                                }
                                menu += "</UL>" + "\n";
                            }
                            menu += "</DIV>" + "\n";
                        }
                        menu += "</DIV>" + "\n";
                    }
                    menu += "</LI>" + "\n";
                    
                } else {
                    // Search special item
                    if (liType.equalsIgnoreCase("search")) {
                        menu += "<LI role='menuitem' class='nav-search'>";
                        menu += "<FORM action='#'>";
                        menu += "<LABEL for='search'></label>";
                        menu += "<INPUT type='text' id='search' title='Search...' placeholder='Search...'>";
                        menu += "<INPUT type='submit' value=''>";
                        menu += "</FORM>";
                         menu += "</LI>" + "\n";
                    }
                }
            }
            menu += "</UL>" + "\n";
        }
        return menu;
    }
    
    /**
     * Ecriture HTML du Tag
     * @throws JspException
     * @throws IOException 
     */
    @Override
    public void doTag() throws JspException, IOException {
        JspContext jsp = this.getJspContext();
        JspWriter out =jsp.getOut();

        try {
            Action actionform = (Action)jsp.findAttribute(C.ACTION_FORM_BEAN);
            String embedded = actionform.getSessionAttr("mode");
            if (embedded.equalsIgnoreCase("embedded")) 
                leftMenu(out);
            else 
                topMenu(out);
            
        } catch (IOException ex) {
            Joy.log().debug ( ex.toString());
            out.println("No menu defined.");
        }
    }

    /**
     * Create a top menu
     * @param out write output
     * @throws IOException 
     */
    private void topMenu(JspWriter out) throws IOException {  
        String MenuToDisplay = "";   
        try {

            org.jdom2.Document document;
            Element racine;
            String sMenu ="";

            document = Joy.openXMLConfig(Joy.parameters().getConfigFolder() + getMenuFile());
            racine = document.getRootElement();
            
            List root = racine.getChildren("joy-menu");
            sMenu = buildTopMenuBloc(root);
            MenuToDisplay = sMenu;

            out.println("<!-- Joy Menu -->");
            out.println("<DIV class='" + cssDivMenu + "' role='navigation'>");
            out.println("<LINK REL='stylesheet' HREF='./joy-external/menu/mega/css/menu.css'>");
            out.println(MenuToDisplay);
            out.println("</DIV>");
            out.println("<!-- Joy Menu -->");
            
        } catch (Exception ex) {
            Joy.log().debug ( ex.toString());
            out.println("No menu defined.");
        }
    }

    /**
     * Fonction recursive de construction de blocs HTML de menu
     * @param bloc Bloc courant (donnée récurssive)
     * @param CurrentBloc Chaine contenant le code HTML du menu
     * @return Chaine contenant le code HTML complete avec les éléments enfants
     */
    private String buildLeftMenuBloc(List bloc, 
                                    String CurrentBloc) {
        if (!bloc.isEmpty()) {
            CurrentBloc += (cssID.equals("") ? "<UL>" : "<UL id='menu-v'>");
            Iterator iter = bloc.iterator();
            
            while(iter.hasNext()) { // parcours tous les éléments du bloc 
                Element child = (Element)iter.next();
                String liObject = child.getAttributeValue(C.ACTION_TAG_OBJECT);
                String liActionType = child.getAttributeValue(C.ACTION_TYPE_TAG);
                String liName = child.getAttributeValue("name");
                String liURL = child.getAttributeValue("url");
                boolean hasChild = false;
                String styleActive = "active ";
                String childBloc = "";
                
                // regarde si l'élément a des enfants
                List childs = child.getChildren("joy-menu");
                if (!childs.isEmpty()) {
                    childBloc = buildLeftMenuBloc(childs, childBloc);
                    hasChild = true;
                }
                
                if (liName != null) {
                    // Construit l'item avec le lien
                    CurrentBloc += "<LI class=" + styleActive + (hasChild ? "has-sub" : "") + ">"; 
                    if (liURL == null) {
                        if (liObject != null) {
                            CurrentBloc += "<A href='." + Joy.parameters().getJoyDefaultURLPattern() + "?";
                            if (liObject != null)
                                CurrentBloc += C.ACTION_TAG_OBJECT + "=" + liObject + "&";
                            if (liActionType!= null)
                                CurrentBloc += C.ACTION_TYPE_TAG + "="  + liActionType;
                            CurrentBloc += buildURLParameter(child);
                            CurrentBloc +=  "'>" + liName + "</A>"; 
                        } else { 
                            CurrentBloc += "<A href='#'>" + liName + "</A>";
                        }
                    } else { 
                        CurrentBloc += "<A href='" + liURL + "'>" + liName + "</A>";
                    }
                    CurrentBloc += (hasChild ? childBloc : "");
                    CurrentBloc += "</LI>";
                }
            }
            CurrentBloc += "</UL>";
        }
        return CurrentBloc;

    }

    /**
     * Create a top menu
     * @param out write output
     * @throws IOException 
     */
    private void leftMenu(JspWriter out) throws IOException {         
        try {
            org.jdom2.Document document;
            Element racine;
            String sMenu ="";

            document = Joy.openXMLConfig(Joy.parameters().getConfigFolder() + getMenuFile());
            racine = document.getRootElement();
            List root = racine.getChildren("joy-menu");
            sMenu = buildLeftMenuBloc(root, sMenu);

            out.println("<LINK REL='stylesheet' HREF='./joy-external/menu/menu-vertical.css'>");
            out.println("<SCRIPT src='./joy-external/menu/menu-vertical.js'></SCRIPT>");
            out.println("<DIV id='cssmenu'>");
            out.println(sMenu);
            out.println("</DIV>");
                
        } catch (Exception ex) {
            Joy.log().debug ( ex.toString());
            out.println("No menu defined.");
        }
    }
    
    private String getMenuFile() {
        return (xmlConfig.equals("") ? "joy-mega-menu.xml" : xmlConfig);
    }
}
