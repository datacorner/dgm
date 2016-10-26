<%-- 
    Document   : dgmList
    Created on : 25 oct. 2016, 17:47:28
    Author     : Benoit CAYLA (benoit@famillecayla.fr)
--%>
<%@tag description="Tag List" pageEncoding="UTF-8"  import="com.dgm.common.providers.ParamProvider, com.joy.Joy, com.joy.mvc.Action, com.dgm.beans.UITaglibSpotData, com.joy.common.JoyParameter, com.joy.mvc.formbean.JoyFormMatrixEntry, com.joy.mvc.formbean.JoyFormVectorEntry"%>
<%@attribute name="matrixname"%>
<%@attribute name="labelname"%>
<%@attribute name="scorename"%>
<%@attribute name="termkey"%>
<div class="list-group">
<%
    int EOF_BAD = 30;
    int EOF_WARNING = 50;
    try {
        Action actionform = (Action)Joy.currentAction(request);
        JoyFormMatrixEntry matrix = actionform.getFormMatrixEntry(matrixname);
        JoyParameter glypheParam = Joy.parameters().getParameter("ApplicationIconsBSGlyphe").get("term");
        try {
            EOF_BAD = (Joy.parameters().getParameter("thresold_bad") == null ? 30 : Integer.parseInt(Joy.parameters().getParameter("thresold_bad").getValue().toString()));
            EOF_WARNING = (Joy.parameters().getParameter("thresold_good") == null ? 50 : Integer.parseInt(Joy.parameters().getParameter("thresold_good").getValue().toString()));
        } catch (Exception e) {}
        
        for (JoyFormVectorEntry myLine : matrix.getMatrix()) {
            float score = Float.valueOf(myLine.getVectorValue(scorename));
            String color = "progress-bar-danger";
            if (score >= EOF_BAD && score < EOF_WARNING) 
                color = "progress-warning";
            else if (score >= EOF_WARNING)
                color = "progress-bar-success";
            
            String url = Joy.basicURL("byterm", "display");
            url += "&term=" + myLine.getVectorValue(termkey);
%>
            <div>
                <p>
                    <strong><i class="fa <%= (String)glypheParam.getValue() %> fa-fw"></i>&nbsp;
                        <a href="<%= url %>"><%= myLine.getVectorValue(labelname) %> </a>
                    </strong>
                    <span class="pull-right text-muted"><%= myLine.getVectorValue(scorename) %>&nbsp;% Complete</span>
                </p>
                <div class="progress progress-striped active">
                    <div class="progress-bar <%= color %>" role="progressbar" aria-valuenow="<%= myLine.getVectorValue(scorename) %>" aria-valuemin="0" aria-valuemax="100" style="width: <%= myLine.getVectorValue(scorename) %>%">
                        <span class="sr-only"><%= myLine.getVectorValue(scorename) %>&nbsp;% Complete</span>
                    </div>
                </div>
            </div>
<%      }   %>
</div>
<%  } catch (Exception e) { %>
    <!-- Error !! -->
<%  }   %>