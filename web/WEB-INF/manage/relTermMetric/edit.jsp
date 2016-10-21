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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Management-AC-MT" />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Terms and Metrics relationships</h1>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">Manage Terms and Metrics relationship for Business Term : <joy:ActionValueTag name="TERM_LINK" /> </div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <!-- update form -->
                                <dgm:dgmAlertTag display="true" /><p>
                                <joy:JoyFormTag object='reltermmetric' actiontype='update' name='frmEditItem' > 
                                <joy:ActionHiddenTag name="TRM_CLUSTER_ID" />
                                <joy:ActionHiddenTag name="Final_TMD_PK" />
                                <joy:ActionHiddenTag name="Final_New_Metric" />
                                <joy:ActionHiddenTag name="Final_Old_Metric" />
                                <joy:ActionHiddenTag name="Final_DQAxis" />
                                </joy:JoyFormTag>
                                <!-- update form -->
                                
                                <table id="matablelist" class="table table-striped table-bordered table-hover"  cellspacing="0" width="100%">
                                    <thead>
                                        <tr>
                                            <th>Quality Dimension</th>
                                            <th>Metric Name</th>
                                        </tr>
                                    </thead>
                                <joy:ActionMatrixRowLoopTag name="LIST">    
                                <tr class="MaTableHover">
                                    <th>
                                        <joy:ActionMatrixByRowTag name="DQX_NAME" />
                                        <input type="hidden" name="old_MET_NAME<joy:ActionMatrixRowIdxTag />" id="old_MET_NAME<joy:ActionMatrixRowIdxTag />" value="<joy:ActionMatrixByRowTag name="MET_NAME" />" />
                                        <input type="hidden" name="old_DQX_NAME<joy:ActionMatrixRowIdxTag />" id="old_DQX_NAME<joy:ActionMatrixRowIdxTag />" value="<joy:ActionMatrixByRowTag name="DQX_NAME" />" />

                                    </th>
                                    <TD>
                                        <SELECT name="MET_NAME<joy:ActionMatrixRowIdxTag />" id="MET_NAME<joy:ActionMatrixRowIdxTag />" class="js-states form-control">
                                            <OPTION value="<joy:ActionMatrixByRowTag name="MET_NAME" />" SELECTED><joy:ActionMatrixByRowTag name="MET_NAME" /></OPTION>
                                        </SELECT>
                                    </TD>
                                </tr>
                                </joy:ActionMatrixRowLoopTag> 
                                </table>  
                                <div class="col-lg-12">
                                    <joy:JoyFormButtonTag id="btn1" label="Back to list"  CSSClass="btn btn-default" link="true" object="reltermmetric" actiontype="list" />
                                </DIV>
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
        responsive : true
    });
    $( "#btn1" ).button();

var mydata = <joy:ActionValueTag name="AVAILABLE_METRICS" /> ;
<joy:ActionMatrixRowLoopTag name="LIST">  
    $("#MET_NAME<joy:ActionMatrixRowIdxTag />").select2({
        data: mydata,
        allowClear: true,
        placeholder: "Select an Term"
    });
    $("#MET_NAME<joy:ActionMatrixRowIdxTag />").on("change", function (e) {
        $("#Final_New_Metric").val($("#MET_NAME<joy:ActionMatrixRowIdxTag />").val());
        $("#Final_Old_Metric").val($("#old_MET_NAME<joy:ActionMatrixRowIdxTag />").val());
        $("#Final_DQAxis").val($("#old_DQX_NAME<joy:ActionMatrixRowIdxTag />").val());
        $("#Final_TMD_PK").val("<joy:ActionMatrixByRowTag name="TMD_PK" />");
        $("#frmEditItem").submit();
    });
</joy:ActionMatrixRowLoopTag> 
});
</script>
</body>
</html>