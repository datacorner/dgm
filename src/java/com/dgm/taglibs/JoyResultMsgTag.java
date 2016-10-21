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
import com.joy.mvc.Action;
import com.joy.common.ActionLogReport;
import java.io.IOException;
import java.util.List;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr)
 */
public class JoyResultMsgTag extends SimpleTagSupport {
    private boolean display;
    
    @Override
    public void doTag() throws JspException, IOException {
        
        JspContext jsp = this.getJspContext();
        //Get the writer object for output.
        JspWriter out =jsp.getOut();
        // get the action object
        
        String infoMsg =  "";
        String errorMsg = "";
        
        Action actionform = (Action)jsp.findAttribute(C.ACTION_FORM_BEAN);
        if (actionform != null) {
            List<ActionLogReport> errors = actionform.getAllDisplayMessages();
            if (errors != null) {
                for (ActionLogReport error : errors) {
                    if (error.getCriticity() == ActionLogReport.enum_CRITICITY.ERROR) {
                        errorMsg += error.getDescription() + "<BR>";
                    } else {
                        infoMsg += error.getDescription() + "<BR>";
                    }
                }
            }
            
            if (!errorMsg.isEmpty()) {
                out.println("<div class='alert alert-danger'>");
                out.println(errorMsg);
                out.println("</div>");
            }
            
            if (!infoMsg.isEmpty()) {
                out.println("<div class='alert alert-success'>");
                out.println(infoMsg);
                out.println("</div>");
            }
            
        }

        //out.println("TEST");
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }
}
