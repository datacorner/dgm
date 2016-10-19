<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dgm" uri="/WEB-INF/dgmTags.tld"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="UI" %>
<%@taglib prefix="joy" uri="/WEB-INF/joyTags.tld"%>

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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Analytics-S-QV" />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Analytics</h1>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading"><UI:dgmGlyphe name="dqaxis" />Data Quality Dimension: <joy:ActionValueTag name="DQX_NAME" /></div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <DIV>
                                    <joy:JoyFormTag inline="true" object='bydqaxis' actiontype='display' name='myform'>
                                    <div class="form-group col-lg-12">
                                        <joy:ActionComboBoxTag name="dqaxis" CSSClass="js-states form-control" />
                                        <joy:JoyFormButtonTag id="btn1" label="Change" submit="true" CSSClass="btn btn-primary" />
                                    </div>
                                    </joy:JoyFormTag>
                                </DIV>
                                <p>&nbsp;<p>
                                <table id="matablelist" class="table table-striped table-bordered table-hover"  cellspacing="0" width="100%"> 
                                    <thead>
                                         <tr>
                                             <th>Metric/Rules</th>
                                             <th>Quality Dimension</th>
                                             <th>Total rows</th>
                                             <th>Valid rows</th>
                                             <th>Invalid rows</th>
                                             <th>Score</th>
                                             <th align="center">Weight</th>
                                             <th align="center">Scorecard</th>
                                         </tr> 
                                     </thead>
                                     <tbody>
                                     <joy:ActionMatrixRowLoopTag name="METRIC_LIST">
                                     <tr>
                                         <td><UI:dgmGlyphe name="metric" /><joy:ActionMatrixByRowTag name="METRIC_LINK" /></td>
                                         <td><joy:ActionMatrixByRowTag name="AXIS_LINK" /></td>
                                         <td><joy:ActionMatrixByRowTag name="FRS_TOTALROWS" /></td>
                                         <td><joy:ActionMatrixByRowTag name="FRS_INVALID_ROWS" /></td>
                                         <td><joy:ActionMatrixByRowTag name="FRS_KPI_SCORE" /></td>
                                         <td><joy:ActionMatrixByRowTag name="FRS_WEIGHT" /></td>
                                         <td><joy:ActionMatrixByRowTag name="FRS_KPI_SCORE" /></td>
                                         <td><joy:ActionMatrixByRowTag name="SCO_NAME" /></td>
                                     </tr>
                                     </joy:ActionMatrixRowLoopTag>
                                     </tbody>
                                     <tfoot>
                                         <tr>
                                             <th>Metric/Rules</th>
                                             <th>Quality Dimension</th>
                                             <th>Total rows</th>
                                             <th>Valid rows</th>
                                             <th>Invalid rows</th>
                                             <th>Score</th>
                                             <th align="center">Weight</th>
                                             <th align="center">Scorecard</th>
                                         </tr> 
                                     </tfoot>
                                 </table>
                            </div>
                        </div>            
                    </div>            
                </div>            

            </div><!-- /.container-fluid -->
        </div><!-- /#page-wrapper -->
    </div><!-- /#wrapper -->
    
<jsp:directive.include file="../../templates/foot.jsp" />
<script>
$(document).ready(function() {
    $('#matablelist').DataTable({
        responsive: true
    });
    $( '#dqaxis' ).select2({
        placeholder: "Select an Context"
    });
    $( "#btn1" ).button();
});

</script>
</body>

</html>