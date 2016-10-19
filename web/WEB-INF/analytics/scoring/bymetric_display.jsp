<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dgm" uri="/WEB-INF/dgmTags.tld"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="UI" %>
<%@taglib prefix="joy" uri="/WEB-INF/joyTags.tld"%>
<%@taglib prefix="chart" tagdir="/WEB-INF/tags/charts" %>

<html lang="en">

<head>
    <jsp:directive.include file="../../templates/head.jsp" />
</head>

<body>
    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navdgm navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <joy:NaviTopLeftMenuTag />
            <joy:NaviTopRightMenu>
                <joy:NaviTopRightShortcutsMenuTag xmlconfig="joy-menu.xml" />
                <joy:NaviTopRightTasksMenuTag />
                <joy:NaviTopRightUserMgtMenuTag />
            </joy:NaviTopRightMenu>
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Analytics-S-Metric" />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header"><joy:ActionValueTag name="MET_NAME" /></h1>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-8">
                        <div class="panel panel-primary">
                            <div class="panel-heading"><UI:dgmGlyphe name="common-chart" />Metrics value history</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div>
                                    <canvas id="LastRun"></canvas>
                                    <div id="barLegend" ></div>
                                </div>
                                <joy:JoyFormTag inline="true" object='bymetric' actiontype='display' name='myform'>
                                <div class="form-group col-lg-12">
                                    <label>Change Metric:</label>
                                    <joy:ActionComboBoxTag name="metric" CSSClass="js-states form-control" />
                                    <joy:JoyFormButtonTag id="btn1" label="Change" submit="true" CSSClass="btn btn-primary" />
                                </div>
                                </joy:JoyFormTag>
                            </div>
                        </div>                
                    </div> 
                    <div class="col-lg-4">
                        <div class="panel panel-default">
                            <div class="panel-heading"><UI:dgmGlyphe name="metric" />Metric Scores</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="form-group">
                                    <label>Current KPI:&nbsp;</label>
                                    <joy:ActionValueTag name="FRS_KPI_SCORE" />%
                                </div>   
                                <div class="form-group">
                                    <label>Invalid rows:&nbsp;</label>
                                    <joy:ActionValueTag name="FRS_INVALID_ROWS" />
                                </div>  
                                <div class="form-group">
                                    <label>Valid rows:&nbsp;</label>
                                    <joy:ActionValueTag name="FRS_VALID_ROWS" />
                                </div>  
                                <div class="form-group">
                                    <label>Total rows:&nbsp;</label>
                                    <joy:ActionValueTag name="FRS_TOTALROWS" />
                                </div>  
                                <div class="form-group">
                                    <label>Cost:&nbsp;</label>
                                    <joy:ActionValueTag name="FRS_COST" />
                                </div>  
                                <div class="form-group">
                                    <label>Last run:&nbsp;</label>
                                    <joy:ActionValueTag name="FRS_DATETIME_LOAD" />
                                </div>
                            </div>
                        </div>            
                    </div>   
                </div>  
                            
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-red">
                            <div class="panel-heading"><UI:dgmGlyphe name="metric" />Metric Description</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="form-group">
                                    <label>Metric Name:&nbsp;</label>
                                    <joy:ActionValueTag name="MET_NAME" />
                                </div>   
                                <div class="form-group">
                                    <label>Metric Description:&nbsp;</label>
                                    <joy:ActionValueTag name="MET_DESCRIPTION" />
                                </div>  
                                <div class="form-group">
                                    <label>Metric Weight:&nbsp;</label>
                                    <joy:ActionValueTag name="MET_WEIGHT" />
                                </div>  
                                <div class="form-group">
                                    <label>Scorecard Group:&nbsp;</label>
                                    <joy:ActionValueTag name="SCG_NAME" />
                                </div>  
                                <div class="form-group">
                                    <label>Scorecard:&nbsp;</label>
                                    <joy:ActionValueTag name="SCO_NAME" />
                                </div>  
                                <div class="form-group">
                                    <label>Project:&nbsp;</label>
                                    <joy:ActionValueTag name="SCO_PROJECT" />
                                </div>   
                            </div>
                        </div>            
                    </div>    
                </div>  
                       

            </div><!-- /.container-fluid -->
        </div><!-- /#page-wrapper -->
    </div><!-- /#wrapper -->

<jsp:directive.include file="../../templates/foot.jsp" />

<script>
// display the last run bars
var ctx = document.getElementById("LastRun").getContext("2d");
window.myBarArea = new Chart(ctx, {
    type: 'bar',
    data: <joy:ActionValueTag name="LASTRUNS" />,
    options: {
        elements: {
            rectangle: {  borderWidth: 2,  borderSkipped: 'bottom' }
        },
        responsive: true,
        legend: { position: 'bottom' },
        title: { display: true, text: 'Last runs (grouped per day)' }
    }
});

$(document).ready(function(){
    $( '#metric' ).select2({
        placeholder: "Select an Metric"
    });
    $( "#btn1" ).button();
});
</script>
</body>

</html>