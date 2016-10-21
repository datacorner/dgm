<%-- 
    Document   : dgmGlyphe
    Created on : Apr 6, 2016, 9:06:03 AM
    Author     : Administrator
--%>

<%@tag description="bootstrap glyphe" pageEncoding="UTF-8" import="com.joy.Joy, com.joy.common.JoyParameter" %>

<%@attribute name="name"%>
<%@attribute name="color"%>
<%@attribute name="size"%>
<%
    JoyParameter glypheParam = Joy.parameters().getParameter("ApplicationIconsBSGlyphe").get(name);
    String myglyphe = "";
    String mycolor = "";
    String mysize = "";
    if (glypheParam != null) 
        myglyphe = (String)glypheParam.getValue();
    if (color != null) 
        mycolor = "style=\"color:" + color + "\"";
    if (size != null) 
        mysize = size;
%>
<i class="fa <%= myglyphe %> fa-fw <%= mysize %>" <%= mycolor %>></i>&nbsp;