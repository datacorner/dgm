<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dgm" uri="/WEB-INF/dgmTags.tld"%>
<%@taglib prefix="UI" tagdir="/WEB-INF/tags" %>
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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Analytics-BG-Glossary" />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <joy:NaviBreadCrumbsTag xmlconfig="joy-menu.xml" activemenuid="Analytics-BG-Glossary" />
                        <h1 class="page-header"><joy:ActionValueTag name="GLO_NAME" /></h1>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-4">
                        <div class="panel panel-yellow">
                            <div class="panel-heading"><UI:dgmGlyphe name="glossary" />Glossary: <joy:ActionValueTag name="GLO_NAME" /></div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <Label>Definition : </Label>
                                <joy:ActionValueTag name="GLO_NAME" />
                                <joy:JoyFormTag inline="true" object='byglossary' actiontype='display' name='myform'>
                                <div class="form-group">
                                    <joy:ActionComboBoxTag name="glossary" CSSClass="js-states form-control" />
                                    <joy:JoyFormButtonTag id="btn1" label="Change" submit="true" CSSClass="btn btn-primary" />
                                </div>
                                </joy:JoyFormTag>
                            </div>
                        </div>
                    </div>            
                    <div class="col-lg-8">
                        <div class="panel panel-green">
                            <div class="panel-heading"><UI:dgmGlyphe name="dqaxis" />Data Quality Dimensions</div>
                            <div class="panel-body">
                                <chart:dgmDQAxisCounters_HTML dqaxis="COUNTER_LIST"  trends="TENDANCE_LIST" />
                            </div>
                        </div>
                    </div>   
                </DIV>
                            
                <div class="row">
                    <div class="col-lg-4">
                        <div class="panel panel-success">
                            <div class="panel-heading"><UI:dgmGlyphe name="common-chart" />Radar Synthesis</div>
                            <div class="panel-body">
                                <div>
                                    <canvas id="radar"></canvas>
                                    <div id="radarLegend"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-8">
                        <div class="panel panel-success">
                            <div class="panel-heading"><UI:dgmGlyphe name="common-chart" />Last runs</div>
                                <div>
                                    <canvas id="LastRun"></canvas>
                                    <div id="barLegend" ></div>
                                </div>
                        </div>
                    </div>   
                </div>
                            
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-info">
                            <div class="panel-heading"><UI:dgmGlyphe name="common-data" />Data</div>
                            <div class="panel-body">
                                <!-- /.panel-heading -->
                                <div class="panel-body">
                                    <!-- Nav tabs -->
                                    <ul class="nav nav-tabs">
                                        <li class="active"><a href="#Metrics" data-toggle="tab">Metrics</a></li>
                                        <li><a href="#Terms" data-toggle="tab">Business Terms</a></li>
                                    </ul>

                                    <!-- Tab panes -->
                                    <div class="tab-content">
                                        <div class="tab-pane fade in active" id="Metrics">
                                            <h4>Metrics</h4>
                                                <table id="matablelist" class="table table-striped table-bordered table-hover"  cellspacing="0" width="100%">
                                                <thead>
                                                <tr>
                                                    <th>Metric/Rules</th>
                                                    <th>DQ Dimension</th>
                                                    <th>Total Rows</th>
                                                    <th>Invalid Rows</th>
                                                    <th>Score</th>
                                                    <th>Scorecard</th>
                                                </tr> 
                                                </thead>
                                                <tbody>
                                                <joy:ActionMatrixRowLoopTag name="METRIC_LIST">
                                                <tr>
                                                    <td><UI:dgmGlyphe name="metric" /><joy:ActionMatrixByRowTag name="METRIC_LINK" /></td>
                                                    <td><joy:ActionMatrixByRowTag name="AXIS_LINK" /></td>
                                                    <td><joy:ActionMatrixByRowTag name="FRS_TOTALROWS" /></td>
                                                    <td><joy:ActionMatrixByRowTag name="FRS_INVALID_ROWS" /></td>
                                                    <td><B><joy:ActionMatrixByRowTag name="FRS_KPI_SCORE" /></B></td>
                                                    <td><joy:ActionMatrixByRowTag name="SCORECARD_REF" /></td>
                                                </tr>
                                                </joy:ActionMatrixRowLoopTag>
                                                </tbody>
                                                </table>
                                        </div>
                                        <div class="tab-pane fade" id="Terms">
                                           <h4>Business Terms</h4>
                                           <table id="matablelist2" class="table table-striped table-bordered table-hover"  cellspacing="0" width="100%">
                                                <thead>
                                                    <tr>
                                                        <th width="200px">Term</th>
                                                        <th width="100px">DQ. Axis</th>
                                                        <th width="70px">Score</th>
                                                        <th width="70px">Cost</th>
                                                    </tr>   
                                                </thead> 
                                                <tbody>
                                                    <joy:ActionMatrixRowLoopTag name="TERM_LIST">
                                                    <tr>
                                                        <td><UI:dgmGlyphe name="term" /><a href="<joy:JoyBasicURL object="byterm" actiontype="display" />&term=<joy:ActionMatrixByRowTag name="TRM_FK" />"><joy:ActionMatrixByRowTag name="TRM_NAME" /></a></td>
                                                        <td><joy:ActionMatrixByRowTag name="DQX_NAME" /></td>
                                                        <td><joy:ActionMatrixByRowTag name="SCORE" /></td>
                                                        <td><joy:ActionMatrixByRowTag name="COST" /></td>
                                                    </tr>
                                                    </joy:ActionMatrixRowLoopTag>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <!-- /.panel-body -->
                            </div>
                        </div>
                    </div>
                </div>        
            
            </div><!-- /.container-fluid -->
        </div><!-- /#page-wrapper -->
    </div><!-- /#wrapper -->

<jsp:directive.include file="../../templates/foot.jsp" />
<SCRIPT>
<chart:dgmDQAxisCounters_JS dqaxis="COUNTER_LIST" />
$(document).ready(function() {
    $('#matablelist').DataTable({
        responsive: true
    });
    $('#matablelist2').DataTable({
        responsive: true
    });
    $( '#glossary' ).select2({
        placeholder: "Select an Term"
    });
    $( "#btn1" ).button();

});


// display the last run bars
var ctx = document.getElementById("LastRun").getContext("2d");
window.myBarArea = new Chart(ctx, {
    type: 'bar',
    data: <joy:ActionValueTag name="LASTRUNS" />,
    options: {
        elements: { rectangle: { borderWidth: 2, borderSkipped: 'bottom' } },
        responsive: true,
        legend: { position: 'bottom' },
        title: { display: true, text: 'Last runs (grouped per day)' }
    }
});

// display the radar
var config = {
    type: 'radar',
    data: <joy:ActionValueTag name="MULTIPLE_RADAR" />,
    options: {
        legend: { position: 'bottom' },
        title: { display: true, text: 'Synthesis per Data Quality Dimension' },
        scale: { reverse: false, ticks: { beginAtZero: true } }
    }
};
window.myRadar = new Chart(document.getElementById("radar"), config);
</SCRIPT>

</body>
</html>                                    

