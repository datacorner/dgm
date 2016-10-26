<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dgm" uri="/WEB-INF/dgmTags.tld"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="UI" %>
<%@taglib prefix="joy" uri="/WEB-INF/joyTags.tld"%>

<html lang="en">

<head>
    <jsp:directive.include file="../templates/head.jsp" />
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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Data-DataMart" />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <joy:NaviBreadCrumbsTag xmlconfig="joy-menu.xml" activemenuid="Data-DataMart" />
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">View Datamart metrics</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="dataTable_wrapper">
                                    <joy:JoyFormTag object="dtmlist" actiontype="list">  
                                        Row Max to display&nbsp;:&nbsp;<joy:ActionInputTextTag name="LIMIT" CSSClass="inputtext" />&nbsp;<joy:JoyFormButtonTag id="Refresh" label="Refresh" submit="true" CSSClass="btn btn-warning" /> (last calculated first)
                                    </joy:JoyFormTag>
                                    <p>
                                    <table id="matablelist" class="table table-striped table-bordered table-hover"  cellspacing="0" width="100%">           
                                        <thead>
                                            <tr>
                                                <th>Metric</th>
                                                <th>Quality Dimension</th>
                                                <th>Scorecard Group</th>
                                                <th>Scorecard</th>
                                                <th>Business Term</th>
                                                <th>Cost</th>
                                                <th>Total Rows</th>
                                                <th>Invalid Rows</th>
                                                <th>Score</th>
                                                <th>Loaded</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <joy:ActionMatrixRowLoopTag name="LIST">
                                            <tr>
                                                <td><joy:ActionMatrixByRowTag name="MET_NAME" /></td>
                                                <td><joy:ActionMatrixByRowTag name="DQX_NAME" /></td>
                                                <td><joy:ActionMatrixByRowTag name="SCG_NAME" /></td>
                                                <td><joy:ActionMatrixByRowTag name="SCO_NAME" /></td>
                                                <td><joy:ActionMatrixByRowTag name="TRM_NAME" /></td>
                                                <td><joy:ActionMatrixByRowTag name="FRS_COST" /></td>
                                                <td><joy:ActionMatrixByRowTag name="FRS_TOTALROWS" /></td>
                                                <td><joy:ActionMatrixByRowTag name="FRS_INVALID_ROWS" /></td>
                                                <td><joy:ActionMatrixByRowTag name="FRS_KPI_SCORE" />%</td>
                                                <td><joy:ActionMatrixByRowTag name="TIM_CALENDAR_DATE" /></td>
                                            </tr>
                                            </joy:ActionMatrixRowLoopTag>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>            
                    </div>            
                </div>            

            </div><!-- /.container-fluid -->
        </div><!-- /#page-wrapper -->
    </div><!-- /#wrapper -->

<jsp:directive.include file="../templates/foot.jsp" />
<script>
$(document).ready(function() {
    $('#matablelist').DataTable({
            responsive: true
    });
});
</script>
</body>
</html>
