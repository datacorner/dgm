<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dgm" uri="/WEB-INF/dgmTags.tld"%>
<%@taglib prefix="UI" tagdir="/WEB-INF/tags" %>
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
                        <h1 class="page-header">Test page</h1>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">test</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <canvas id="MyPolar"  class="blocradar_chart_canvas"></canvas>
                                <div id="radarLegend" ></div>
                            </div>
                        </div>            
                    </div>   
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">test</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div>
                                    <!-- Button trigger modal -->
                                    <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
                                        Launch Demo Modal
                                    </button>
                                    <!-- Modal -->
                                    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                    <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                                                </div>
                                                <div class="modal-body">
                                                    bla bla
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                    <button type="button" class="btn btn-primary">Save changes</button>
                                                </div>
                                            </div>
                                            <!-- /.modal-content -->
                                        </div>
                                        <!-- /.modal-dialog -->
                                    </div>
                                    <!-- /.modal -->
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
function multiDStooltip(v) 
{ 
    return v.datasetLabel + ' : ' + v.value + ' %';
}
function singleDStooltip(v) 
{ 
    return v.label + ' : ' + v.value + ' %';
}

var myBarChart;
function callbackSuccess(content, tag) {
    switch(tag) {
        case 'polaraxis':
            polarData = content;
            var ctx = document.getElementById("MyPolar").getContext("2d");
            window.myPolarArea = new Chart(ctx).PolarArea(polarData, {
                    responsive:true,
                    tooltipTemplate:  function(v) {return singleDStooltip(v);}
            });
            legend(document.getElementById("radarLegend"), polarData);
            break;
            
        case 'barbyglossary':
            barData = content;
            var ctx = document.getElementById("MyBars").getContext("2d");
            myBarChart = new Chart(ctx).Bar(barData, {
                    responsive:true,
                    multiTooltipTemplate:  function(v) {return multiDStooltip(v);},
            });
            legend(document.getElementById("barLegend"), barData);
            break;
        default:
    }
}
loadJSON('./rest/charts/polar/AXIS_SCORE_HOME_00', 'polaraxis');
loadJSON('./rest/charts/bar/TEST', 'barbyglossary');
</script>
</body>
</html>