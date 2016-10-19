/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author Administrator
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
