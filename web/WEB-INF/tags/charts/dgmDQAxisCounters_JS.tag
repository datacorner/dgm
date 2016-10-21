<%@tag description="DGM Counter Tag" pageEncoding="UTF-8" import="com.joy.Joy, com.joy.common.JoyParameter, com.joy.charts.gaugejs.ChartCounterData, com.joy.mvc.Action, com.joy.C, com.joy.mvc.formbean.JoyFormMatrixEntry, com.joy.mvc.formbean.JoyFormVectorEntry" %>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="dqaxis"%>

<%
    try {
        Action actionform = (Action)Joy.currentAction(request);
        JoyFormMatrixEntry matrix = (JoyFormMatrixEntry)actionform.getFormMatrixEntry(dqaxis); 

        for (JoyFormVectorEntry axis : matrix.getMatrix()) {
            ChartCounterData myChart = (ChartCounterData)axis.getVectorObject("COUNTER_OBJECT");
%>
var opts<%= myChart.getCode() %> = {
    lines: 12, // The number of lines to draw
    angle: 0.15, // The length of each line
    lineWidth: 0.44, // The line thickness
    pointer: {
        length: 0.9, // The radius of the inner circle
        strokeWidth: 0.035, // The rotation offset
        color: '#000000' // Fill color
    },
    limitMax: 'false',   // If true, the pointer will not go past the end of the gauge
    colorStart: '<%= myChart.getColor() %>',   // Colors
    colorStop: '#8FC0DA',    // just experiment with them
    strokeColor: '#E0E0E0',   // to see which ones work best for you
    generateGradient: true
};
var target = document.getElementById('canvas-<%= myChart.getCode() %>'); // your canvas element
var gauge = new Gauge(target).setOptions(opts<%= myChart.getCode() %>); // create sexy gauge!
gauge.maxValue = 100; // set max gauge value
gauge.animationSpeed = 32; // set animation speed (32 is default value)
gauge.set(<%= myChart.getValue() %>); // set actual value

<%      } 
    } catch (Exception e) { 
%>
    <!-- Error !! -->
<%
    }
%>
