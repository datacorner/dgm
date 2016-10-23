<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dgm" uri="/WEB-INF/dgmTags.tld"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="UI" %>
<%@taglib prefix="joy" uri="/WEB-INF/joyTags.tld"%>
<%@taglib prefix="chart" tagdir="/WEB-INF/tags/charts" %>

<html lang="en">

<head>
    <jsp:directive.include file="./templates/head.jsp" />
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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Home" />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">

                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Data Governance Dashboard</h1>
                    </div>
                </div>
                
                <div class="row">
                    <UI:dgmSpot tag="TERMS" panelcolor="spot-primary" panelicon="term" classbloc="col-lg-3 col-md-6" />
                    <UI:dgmSpot tag="METRICS" panelcolor="spot-red" panelicon="metric"  classbloc="col-lg-3 col-md-6" />
                    <UI:dgmSpot tag="GLOSSARIES" panelcolor="spot-green" panelicon="glossary"  classbloc="col-lg-3 col-md-6" />
                    <UI:dgmSpot tag="DQAXIS" panelcolor="spot-yellow" panelicon="dqaxis"  classbloc="col-lg-3 col-md-6" />
                </div>
                
                <div class="row">
                    <div class="col-lg-6">
                        <div class="panel panel-default"  id="panel-dashbydqaxis">
                            <div class="panel-heading"><UI:dgmGlyphe name="dqaxis" />Global scoring per Data Quality Dimension</div>
                            <div class="panel-body">
                                <div class="dataTable_wrapper">
                                    <canvas id="MyPolar"></canvas>
                                    <div id="radarLegend" ></div>
                                </div>
                            </div>
                        </div>            
                    </div>       
                                
                    <div class="col-lg-6">
                        <div class="panel panel-default" id="panel-dashbyglossary">
                            <div class="panel-heading"><UI:dgmGlyphe name="dashboard" />Global scoring by Glossary</div>
                            <div class="panel-body">
                                <div class="dataTable_wrapper">
                                    <canvas id="MyBars"></canvas>
                                    <div id="barLegend" ></div>
                                </div>
                            </div>
                        </div>            
                    </div>   
                </div>
                            
                <div class="row">
                    <div class="col-lg-3">
                        <div class="panel panel-default panel-scores">
                            <div class="panel-heading">
                                <i class="fa fa-dashboard fa-fw"></i> Global Scores
                            </div>
                            <div class="panel-body">
                                <div class="list-group">
                                    <joy:ActionMatrixRowLoopTag name="SCORES">
                                        <a href="#">
                                            <div>
                                                <p>
                                                    <strong><UI:dgmGlyphe name="dqaxis" /><joy:ActionMatrixByRowTag name="DQX_NAME" /></strong>
                                                    <span class="pull-right text-muted"><joy:ActionMatrixByRowTag name="SCORE" />&nbsp;% Complete</span>
                                                </p>
                                                <div class="progress progress-striped active">
                                                    <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="<joy:ActionMatrixByRowTag name="SCORE" />" aria-valuemin="0" aria-valuemax="100" style="width: <joy:ActionMatrixByRowTag name="SCORE" />%">
                                                        <span class="sr-only"><joy:ActionMatrixByRowTag name="SCORE" />&nbsp;% Complete</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
                                    </joy:ActionMatrixRowLoopTag>
                                </div>
                            </div>
                        </div>    
                    </div>  
                    
                    <div class="col-lg-3">
                        <div class="panel panel-default panel-scores">
                            <div class="panel-heading">
                                <i class="fa fa-dashboard fa-fw"></i> Best Terms
                            </div>
                            <div class="panel-body">
                                <div class="list-group">
                                    <joy:ActionMatrixRowLoopTag name="BEST_TERMS">
                                        <a href="#">
                                            <div>
                                                <p>
                                                    <strong><UI:dgmGlyphe name="term" /><a href="<joy:JoyBasicURL object="byterm" actiontype="display" />&term=<joy:ActionMatrixByRowTag name="TRM_FK" />"><joy:ActionMatrixByRowTag name="TRM_NAME" /></a></strong>
                                                    <span class="pull-right text-muted"><joy:ActionMatrixByRowTag name="GLOBALSCORE" />&nbsp;% Complete</span>
                                                </p>
                                                <div class="progress progress-striped active">
                                                    <div class="progress-bar progress-striped" role="progressbar" aria-valuenow="<joy:ActionMatrixByRowTag name="GLOBALSCORE" />" aria-valuemin="0" aria-valuemax="100" style="width: <joy:ActionMatrixByRowTag name="GLOBALSCORE" />%">
                                                        <span class="sr-only"><joy:ActionMatrixByRowTag name="GLOBALSCORE" />&nbsp;% Complete</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
                                    </joy:ActionMatrixRowLoopTag>
                                </div>
                            </div>
                        </div>    
                    </div>   
                    
                    <div class="col-lg-6">
                        <div class="panel panel-default" id="panel-dashOwnerMissing">
                            <div class="panel-heading"><UI:dgmGlyphe name="dashboard" />Owner missing</div>
                            <div class="panel-body">
                                <div class="dataTable_wrapper">
                                    <canvas id="MyNoOwner" ></canvas>
                                    <div id="barLegend" ></div>
                                </div>
                            </div>
                        </div>            
                    </div>   
                    
                </div>

            </div><!-- /.container-fluid -->
        </div><!-- /#page-wrapper -->
    </div><!-- /#wrapper -->

<jsp:directive.include file="./templates/foot.jsp" />
<script>
$(function(){
    $('#panel-dashbyglossary').lobiPanel({
       reload: false,
       close: false,
       editTitle: false,
       sortable: true
     });
    $('#panel-dashbyglossary').on('onFullScreen.lobiPanel', function(ev, lobiPanel){
        document.getElementById("MyBars").style.height = "90%";
        window.myBarArea.resize();
    });
    $('#panel-dashbyglossary').on('onSmallSize.lobiPanel', function(ev, lobiPanel){
        window.myBarArea.resize();
    });
    
    $('.panel-scores').lobiPanel({
       reload: false,
       close: false,
       editTitle: false,
       sortable: true
    });
     
    $('#panel-dashbydqaxis').on('onFullScreen.lobiPanel', function(ev, lobiPanel){
        window.myPolarArea.resize();
    });
    $('#panel-dashbydqaxis').on('onSmallSize.lobiPanel', function(ev, lobiPanel){
        window.myPolarArea.resize();
    });
    $('#panel-dashbydqaxis').lobiPanel({
       reload: false,
       close: false,
       editTitle: false,
       sortable: true
    });
    
    $('#panel-dashOwnerMissing').on('onFullScreen.lobiPanel', function(ev, lobiPanel){
        window.MyNoOwner.resize();
    });
    $('#panel-dashOwnerMissing').on('onSmallSize.lobiPanel', function(ev, lobiPanel){
        window.MyNoOwner.resize();
    });
    $('#panel-dashOwnerMissing').lobiPanel({
       reload: false,
       close: false,
       editTitle: false,
       sortable: true
    });
 });
 
// Load the charts
function callbackSuccess(content, tag) {
    switch(tag) {
        case 'polaraxis':
            var ctx = document.getElementById("MyPolar").getContext("2d");
            var config = {
                data: content,
                options: {
                    responsive: true,
                    legend: { position: 'bottom' },
                    title: { display: true, text: 'Global scoring per Data Quality Dimension' },
                    scale: {
                        ticks: { beginAtZero: true },
                        reverse: false
                    },
                    animateRotate:true,
                    segmentShowStroke : true,
                    scaleShowLine : true
                    }
                };
            var ctx = document.getElementById("MyPolar");
            window.myPolarArea = Chart.PolarArea(ctx, config);

            break;
            
        case 'barbyglossary':
            var ctx = document.getElementById("MyBars").getContext("2d");
            window.myBarArea = new Chart(ctx, {
                type: 'bar',
                data: content,
                options: {
                    elements: {  rectangle: { borderWidth: 2, borderSkipped: 'bottom' } },
                    responsive: true,
                    legend: { position: 'bottom' },
                    title: { display: true, text: 'Global scoring by Glossary' }
                }
            });
            break;
            
        case 'noowner': 
            var ctx = document.getElementById("MyNoOwner").getContext("2d");
            window.MyNoOwner = new Chart(ctx, {
                type: 'bar',
                data: content,
                options: {
                    elements: {
                        rectangle: { borderWidth: 2, borderSkipped: 'bottom' }
                    },
                    responsive: true,
                    legend: { position: 'bottom' },
                    title: { display: true, text: 'No Owners/stewards' }
                }
            });
            break;
        default:
    }
}
loadJSON('./rest/charts/sds/AXIS_SCORE_HOME_00', 'polaraxis');
loadJSON('./rest/charts/mds/GLOBAL_SCORING_HOME_01', 'barbyglossary');
loadJSON('./rest/charts/mds/NO_OWNER_HOME_02', 'noowner');
</script>

</body>
</html>
