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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Analytics-BG-Term" />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <joy:NaviBreadCrumbsTag xmlconfig="joy-menu.xml" activemenuid="Analytics-BG-Term" />
                        <h1 class="page-header"><IMG src='./images/glossary/<joy:ActionValueTag name="IMGICO" />' height="28px" />&nbsp;<joy:ActionValueTag name="TRM_NAME" /></h1>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-5">
                        <div class="row">
                            <div class="col-lg-5">       
                                <div class="blocGlobalScore">
                                    <div class="globalScoreTitle"><span id="gauge-value"></span>%</div>
                                    <canvas width=200 id="canvasScore" alt="Global score"></canvas>
                                </div>
                            </div> 
                            <div class="col-lg-7">
                                <div class="divtermblocheader">
                                    <p><UI:dgmGlyphe name="status" /><B>Status :</B> <joy:ActionValueTag name="TRM_PHASE" /></p>
                                    <p><UI:dgmGlyphe name="term" /><B>Type :</B> <joy:ActionValueTag name="TRT_NAME" /></p>
                                    <p><UI:dgmGlyphe name="user" /><B>Owner :</B> <joy:ActionValueTag name="TRM_OWNER_EMAIL" /></p>
                                    <p><UI:dgmGlyphe name="user" /><B>Steward :</B> <joy:ActionValueTag name="TRM_STEWARD_EMAIL" /></p>
                                    <p><UI:dgmGlyphe name="businessmap" /><A href="<joy:JoyBasicURL object="mapbyterm" actiontype="display" />&nbhops=3&term=<joy:ActionValueTag name="TRM_PK" />">Relationship map</A></p>
                                    <p><UI:dgmGlyphe name="common-configuration" /><A href="<joy:ActionValueTag name="CONFIG_TERM_LINK" />">Term Configuration</A></p>
                                </div>
                            </div> 
                        </div>   
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-yellow">
                                    <!--  <div class="panel-heading"><UI:dgmGlyphe name="term" />Business Term: <joy:ActionValueTag name="TRM_NAME" /></div> -->
                                    <!-- /.panel-heading -->
                                    <div class="panel-body">
                                        <DIV class="bloctitreprincipal">
                                        <h5>Metric <joy:ActionValueTag name="TRM_NAME" /> coming from <joy:ActionValueTag name="GLOSSARY_LINK" />&nbsp;/&nbsp;<joy:ActionValueTag name="CATEGORY_LINK" /> </h5>
                                        <joy:ActionConditionTag name="INFORMATICA">
                                            <H5><A href="<joy:ActionValueTag name="INFA_MM_LINK" />" target="other"><i class="fa fa-crosshairs fa-fw"></i>&nbsp;Informatica Metadata Data Lineage</a></h5>
                                            <H5><A href="<joy:ActionValueTag name="INFA_DIRECT_LINK" />" target="_self"><i class="fa fa-book fa-fw"></i>&nbsp;Jump to Business Term</a></h5>
                                        </joy:ActionConditionTag>
                                        </DIV>
                                        <joy:JoyFormTag inline="true" object='byterm' actiontype='display' name='myform'>
                                        <div class="form-group">
                                            <joy:ActionComboBoxTag name="term" CSSClass="js-states form-control" />
                                            <joy:JoyFormButtonTag id="btn1" label="Change" submit="true" CSSClass="btn btn-primary" />
                                        </div>
                                        </joy:JoyFormTag>
                                    </div>
                                </div>
                            </div>  
                        </div>
                    </div>    
                    <div class="col-lg-7">
                        <div class="panel panel-green">
                            <div class="panel-heading"><UI:dgmGlyphe name="dqaxis" />Data Quality Dimensions</div>
                            <div class="panel-body">
                                <chart:dgmDQAxisCounters_HTML dqaxis="COUNTER_LIST"  trends="TENDANCE_LIST" />
                            </div>
                        </div>
                    </div>
                </DIV>
                <div class="row">                
                    <div class="col-lg-6">
                        <div class="panel panel-red">
                            <div class="panel-heading"><UI:dgmGlyphe name="glossary" />Glossary definition</div>
                            <div class="panel-body" style="max-height: 300px;min-height: 300px;overflow-y: scroll;">
                                <!-- Nav tabs -->
                                <ul class="nav nav-tabs">
                                        <li class="active"><a href="#Description" data-toggle="tab">Description</a></li>
                                        <li><a href="#Usage" data-toggle="tab">Usage</a></li>
                                        <li><a href="#Example" data-toggle="tab">Example</a></li>
                                </ul>

                                <!-- Tab panes -->
                                <div class="tab-content">
                                        <div class="tab-pane fade in active" id="Description">
                                                <h4>Description</h4>
                                                <p><joy:ActionValueTag name="TRM_DESCRIPTION" /></p>
                                        </div>
                                        <div class="tab-pane fade" id="Usage">
                                                <h4>Usage</h4>
                                                <p><joy:ActionValueTag name="TRM_USAGE" /></p>
                                        </div>
                                        <div class="tab-pane fade" id="Example">
                                                <h4>Example</h4>
                                                <p><joy:ActionValueTag name="TRM_EXAMPLE" /></p>
                                        </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <div class="panel panel-success">
                            <div class="panel-heading"><UI:dgmGlyphe name="common-chart" />Last runs</div>
                            <div class="panel-body">
                                <div>
                                    <canvas id="LastRun"></canvas>
                                    <div id="barLegend"></div>
                                </div>
                            </div>
                        </div>
                    </div>                 
            
                </div>   
                            
                <div class="row">
                    <div class="col-lg-6">
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
                    <div class="col-lg-6">
                        <div class="panel panel-primary">   
                           <div class="panel-heading"><UI:dgmGlyphe name="relationship" />Business Term Relationships</div>
                           <div class="panel-body" style="max-height: 300px;min-height: 300px;overflow-y: scroll;">
                               <div id="treeview" class=""></div>
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
                                            <li><a href="#Context" data-toggle="tab">Context</a></li>
                                            <li><a href="#DataSource" data-toggle="tab">DataSource</a></li>
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
                                                <div class="tab-pane fade" id="Context">
                                                        <h4>Context</h4>
                                                   <table id="matablelist2" class="table table-striped table-bordered table-hover"  cellspacing="0" width="100%">
                                                        <thead>
                                                                <tr>
                                                                <th>Context</th>
                                                                </tr> 
                                                        </thead>
                                                        <tbody>
                                                        <joy:ActionMatrixRowLoopTag name="CONTEXT_LIST">
                                                        <tr>
                                                                <td><UI:dgmGlyphe name="context" /><A href="<joy:JoyBasicURL object="bycontext" actiontype="display" />&context=<joy:ActionMatrixByRowTag name="CON_PK" />"><joy:ActionMatrixByRowTag name="CON_DESCRIPTION" /></a></td>
                                                        </tr>
                                                        </joy:ActionMatrixRowLoopTag>
                                                        </tbody>
                                                        </table>
                                                </div>
                                                <div class="tab-pane fade" id="DataSource">
                                                    <h4>Data sources</h4>
                                                    <table id="matablelist3" class="table table-striped table-bordered table-hover"  cellspacing="0" width="100%">
                                                    <thead>
                                                            <tr>
                                                            <th>Data Source</th>
                                                            </tr> 
                                                    </thead>
                                                    <tbody>
                                                    <joy:ActionMatrixRowLoopTag name="DATASOURCE_LIST">
                                                    <tr>
                                                        <td><UI:dgmGlyphe name="datasource" /><A href="<joy:JoyBasicURL object="byds" actiontype="display" />&ds=<joy:ActionMatrixByRowTag name="DSO_PK" />"><joy:ActionMatrixByRowTag name="DSO_SOURCENAME" /></a></td>
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
    $('#matablelist3').DataTable({
            responsive: true
    });
    $( '#term' ).select2({
        placeholder: "Select an Term"
    });
    $( "#btn1" ).button();
    
    $('a[data-toggle="Metrics"]').on( 'shown.bs.tab', function (e) {
        $.fn.dataTable.tables( {visible: true, api: true} ).columns.adjust();
    } );
});

// treeview display
function callbackSuccess(content, tag) {
    var jsonContent = JSON.stringify(content);
    $('#treeview').treeview({
      color: "#428bca",
      showBorder: false,
      enableLinks: true,
      data: jsonContent
    });
}
loadJSON('./rest/relterm/3/<joy:ActionValueTag name="TRM_PK" />', 'relationship');

// display the last run bars
var configRuns = {
    type: 'bar',
    data: <joy:ActionValueTag name="LASTRUNS" />,
    options: {
        elements: {
            rectangle: { borderWidth: 2, borderSkipped: 'bottom' }
        },
        responsive: true,
        legend: { position: 'bottom' },
        title: { display: true, text: 'Last runs (grouped per day)' }
    }
};
window.LastRun = new Chart(document.getElementById("LastRun"), configRuns);

// display the radar
var configRadar = {
    type: 'radar',
    data: <joy:ActionValueTag name="MULTIPLE_RADAR" />,
    options: {
        legend: {  position: 'bottom' },
        title: { display: true, text: 'Synthesis per Data Quality Dimension' },
        scale: { reverse: false, ticks: { beginAtZero: true  } }
    }
};
window.myRadar = new Chart(document.getElementById("radar"), configRadar);

// Display the global score
var optsGlobalScore = {
    lines: 1, // The number of lines to draw
    angle: 0.15, // The length of each line
    lineWidth: 0.25, // The line thickness
    limitMax: 'false',   // If true, the pointer will not go past the end of the gauge
    colorStart: '#A395C0',   // Colors
    colorStop: '#A395C0',
    strokeColor: '#EEEEEE',
    generateGradient: true
};
var target = document.getElementById('canvasScore'); // your canvas element
var gauge = new Donut(target).setOptions(optsGlobalScore); // create sexy gauge!
gauge.maxValue = 100; // set max gauge value
gauge.animationSpeed = 32; // set animation speed (32 is default value)
gauge.set(<joy:ActionValueTag name="GLOBALSCORE" />); // set actual value
gauge.setTextField(document.getElementById("gauge-value"));
</script>

</body>
</html>                                    

