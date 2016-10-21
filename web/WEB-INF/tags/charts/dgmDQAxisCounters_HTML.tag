<%@tag description="DGM Counter Tag" pageEncoding="UTF-8" import="com.joy.Joy, com.joy.common.JoyParameter, com.joy.charts.gaugejs.ChartCounterData, com.joy.mvc.Action, com.joy.C, com.joy.mvc.formbean.JoyFormMatrixEntry, com.joy.mvc.formbean.JoyFormVectorEntry" %>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="dqaxis"%>
<%@attribute name="trends"%>
<%
    try {
        Action actionform = (Action)Joy.currentAction(request);
        JoyFormMatrixEntry matrix = (JoyFormMatrixEntry)actionform.getFormMatrixEntry(dqaxis); 
        int i = matrix.getMatrix().size();
        
        if (i > 0) {
%>
	<div class="table-responsive">
		<table class="table">
			<tbody>
                            <tr>
<%
            for (JoyFormVectorEntry axis : matrix.getMatrix()) {
                ChartCounterData myChart = (ChartCounterData)axis.getVectorObject("COUNTER_OBJECT");
%>
                                <TH style="text-align:center;"><canvas width=100 height=70 id="canvas-<%= myChart.getCode() %>"></canvas><br><%= myChart.getLabel() %></TH>
<%          } %>
                            </tr>
                            
                            <tr>
<%          for (JoyFormVectorEntry axis : matrix.getMatrix()) {
                ChartCounterData myChart = (ChartCounterData)axis.getVectorObject("COUNTER_OBJECT");
%>
                                <td style="text-align:center;">
                                    <span style="font-size: 18px; font-style:  italic"><%= myChart.getValue()%>%</span>
                                </td>
<%
        }
%>
                            </TR>
                    <TR>
<%
        JoyFormMatrixEntry matrixtrends = (JoyFormMatrixEntry)actionform.getFormMatrixEntry(trends); 
        JoyParameter glypheParam;
        String myglyphe = "";
        String mycolor = "";
        
        for (JoyFormVectorEntry axis : matrixtrends.getMatrix()) {
            if (axis.getVectorValue("TREND").equalsIgnoreCase("UP")) {
               glypheParam = Joy.parameters().getParameter("ApplicationIconsBSGlyphe").get("trend-up");
               mycolor = "style=\"color:" + Joy.rgba(Joy.parameters().getParameter("ColorGood").getValue().toString(), "1") + "\"";
            } else if (axis.getVectorValue("TREND").equalsIgnoreCase("DOWN")) {
               glypheParam = Joy.parameters().getParameter("ApplicationIconsBSGlyphe").get("trend-down");
               mycolor = "style=\"color:" + Joy.rgba(Joy.parameters().getParameter("ColorBad").getValue().toString(), "1") + "\"";
            } else if (axis.getVectorValue("TREND").equalsIgnoreCase("EQUAL")) {
               glypheParam = Joy.parameters().getParameter("ApplicationIconsBSGlyphe").get("trend-stable");
               mycolor = "style=\"color:" + Joy.rgba(Joy.parameters().getParameter("ColorNoMove").getValue().toString(), "1") + "\"";
            } else {
               glypheParam = Joy.parameters().getParameter("ApplicationIconsBSGlyphe").get("trend-new");
               mycolor = "style=\"color:" + Joy.rgba(Joy.parameters().getParameter("ColorNoMove").getValue().toString(), "1") + "\"";
            }
            if (glypheParam != null) 
                myglyphe = (String)glypheParam.getValue();
%>
            <TD style="text-align:center;">
                <DIV class="bloctendance_prev">Previous: <%= axis.getVectorValue("PREV") %></DIV> 
                <DIV class="bloctendance_last">Variation: <%= axis.getVectorValue("TREND_SCORE") %>%</DIV> 
                <i class="fa <%= myglyphe %> fa-fw fa-4x" <%= mycolor %>></i>&nbsp;
            </TD>
<%
        }
%>                                
                </TR>
			</tbody>
		</table>
	</div>
<%
        } else {
%>
No data to display.
<%
}
    } catch (Exception e) { 
%>
    <!-- Error !! -->
<%
    }
%>
