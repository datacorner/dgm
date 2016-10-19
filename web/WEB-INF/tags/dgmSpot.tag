<%-- 
    Document   : Spots here
    Created on : 5 mars 2016, 09:03:52
    Author     : benoit
--%>

<%@tag description="Spots here" pageEncoding="UTF-8" import="com.joy.Joy, com.joy.mvc.Action, com.dgm.ui.UITaglibSpotData, com.joy.common.JoyParameter" %>

<%@attribute name="tag"%>
<%@attribute name="panelcolor"%>
<%@attribute name="panelicon"%>
<%@attribute name="classbloc"%> <!-- example: col-lg-3 col-md-6 -->
<%
    try {
        Action actionform = (Action)Joy.currentAction(request);
        UITaglibSpotData spotdata = (UITaglibSpotData)actionform.getFormSingleEntry(tag).getValue();
        
        String myglyphe = "";
        JoyParameter glypheParam = Joy.parameters().getParameter("ApplicationIconsBSGlyphe").get(panelicon);
        if (glypheParam != null) 
            myglyphe = (String)glypheParam.getValue();
%>
<%-- Texts top: --%>

<div class="<%= classbloc %>">
    <div class="panel <%= panelcolor %>">
        <div class="panel-heading">
            <div class="row">
                <div class="col-xs-3">
                    <i class="fa <%= myglyphe %> fa-5x"></i>
                </div>
                <div class="col-xs-9 text-right">
                    <div class="huge"><%= spotdata.getBigshorttext() %></div>
                    <div><%= spotdata.getLittlelongtext() %></div>
                </div>
            </div>
        </div>
        <a href="<%= spotdata.getLinkURL() %>">
        <div class="panel-footer">
            <span class="pull-left"><%= spotdata.getLinktext() %></span>
            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
            <div class="clearfix"></div>
        </div>
        </a>
    </div>
</div>
<%
    } catch (Exception e) { 
%>
    <!-- Error !! -->
<%
    }
%>