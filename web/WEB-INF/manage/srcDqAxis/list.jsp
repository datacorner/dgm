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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Management-AC-QV" />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Data Quality Dimensions</h1>
                        <dgm:dgmAlertTag display="true" />
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">Manage Data Quality Dimensions</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <table id="matablelist" class="table table-striped table-bordered table-hover"  cellspacing="0" width="100%">
                                    <thead>
                                        <tr>
                                            <th>Code</th>
                                            <th>Label</th>
                                            <th>Description</th>
                                            <th>Weight</th>
                                            <th style="text-align:center;">Manage</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <joy:ActionMatrixRowLoopTag name="LIST">    
                                        <tr>
                                            <td><joy:ActionMatrixByRowTag name="DQX_CODE" /></td>
                                            <td><joy:ActionMatrixByRowTag name="DQX_LABEL" /></td>
                                            <td><joy:ActionMatrixByRowTag name="DQX_DESCRIPTION" /></td>
                                            <td><joy:ActionMatrixByRowTag name="DQX_WEIGHT" /></td>
                                            <td align="center">
                                                <button type="button" class="btn btn-primary btn-circle" onclick="window.open('<joy:JoyBasicURL object="dqaxis" actiontype="edit" />&DQX_PK=<joy:ActionMatrixByRowTag name="DQX_PK" />', '_self');"><i class="fa fa-edit"></i></button>
                                                <button type="button" class="btn btn-danger btn-circle" onclick="Delete('<joy:JoyBasicURL object="dqaxis" actiontype="delete" />&DQX_PK=<joy:ActionMatrixByRowTag name="DQX_PK" />');"><i class="fa fa-times"></i></button>
                                            </td>
                                        </tr>
                                    </joy:ActionMatrixRowLoopTag> 
                                    </tbody>
                                </table>
                                <div>
                                    <joy:JoyFormTag object='dqaxis' actiontype='add' name='frmEditItem' >  
                                    <joy:JoyFormButtonTag id="btn1" label="<i class='fa fa-plus-circle'></i>&nbsp;Add new Data Quality Dimension" CSSClass="btn btn-success" onclick="document.forms['frmEditItem'].submit();" />
                                    </joy:JoyFormTag>
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
$(document).ready(function() {
    $('#matablelist').DataTable({
            responsive: true
    });
});
function Delete(link) {
    bootbox.confirm("Are you sure you want to delete this Data Quality Dimension ?", function(result) {
        if (result)
          window.open(link, '_self');
    }); 
} 
</script>
</body>
</html>

