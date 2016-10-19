/**
*    Chaque element de menu est placé dans un tag <joy-menu></joy-menu>
*    Attributs du tag:
*    * name (obligatoire) : nom du menu
*    [choix]
*    * url (obligatoire): URL du menu (http://)
*    Ou
*    * object (obligatoire) nom du tag Object (joy)
*    * actiontype (facultatif) nom de l'actiontype (joy)
*    * parametres (entier x de 1 à ...)
*        - pnx= Nom du parametre x
*        - pnv= Valeur du parametre x
 */
package com.dgm.taglibs;

import com.joy.C;
import com.joy.Joy;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * Taglib d'affichage d'un menu
 * @author Benoit CAYLA
 */
public class JoyMenuTag extends SimpleTagSupport {
    private boolean display;    // affichage ou non
    private String cssDivMenu;  // ID du CSS du menu (tag DIV)
    private String xmlConfig;   // fichier de configuration
    private String cssID;       // ID du CSS du menu (tag LU) / exemple = menu-v
    
    private static String menuCache; // Mise en cache du menu
    
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
     * Fonction recursive de construction de blocs HTML de menu
     * @param bloc Bloc courant (donnée récurssive)
     * @param CurrentBloc Chaine contenant le code HTML du menu
     * @return Chaine contenant le code HTML complete avec les éléments enfants
     */
    private String buildLeftMenuBloc(List bloc, 
                                    String CurrentBloc) {
        if (!bloc.isEmpty()) {
            CurrentBloc += (cssID.equals("") ? "<UL>" : "<UL id='" + cssID + "'>");
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
            SAXBuilder sxb = new SAXBuilder();
            org.jdom2.Document document;
            Element racine;
            String sMenu ="";
            String xmlConfigFile = (xmlConfig.equals("") ? "joy-menu.xml" : xmlConfig);

            document = sxb.build(Thread.currentThread().getContextClassLoader().getResourceAsStream(xmlConfigFile));
            racine = document.getRootElement();
            List root = racine.getChildren("joy-menu");
            sMenu = buildLeftMenuBloc(root, sMenu);

            out.println("<div id='" + cssDivMenu + "'>");
            
            out.println(sMenu);
            out.println("</div>");
            menuCache = sMenu;
                
        } catch (IOException | JDOMException ex) {
            Joy.log().debug ( ex.toString());
            out.println("No menu defined.");
        }
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
        
        // Create a top menu or reuse the existing one
        if (menuCache == null) {
            leftMenu(out);
            
        } else {
            out.println("<div id='" + cssDivMenu + "'>");
            out.println(menuCache);
            out.println("</div>");
        }
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

}
