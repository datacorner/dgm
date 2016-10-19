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
                        <dgm:dgmAlertTag display="true" />
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">Manage Terms and Metrics relationships</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <table id="matablelist" class="table table-striped table-bordered table-hover"  cellspacing="0" width="100%">
                                    <thead>
                                        <tr>
                                            <th>Business Term</th>
                                            <th>Description</th>
                                            <th style="width: 90px;">Manage</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <joy:ActionMatrixRowLoopTag name="LIST"> 
                                    <tr>
                                        <td><UI:dgmGlyphe name="term" /><joy:ActionMatrixByRowTag name="TERMLINK" /></td>
                                        <td><joy:ActionMatrixByRowTag name="TMD_DESCRIPTION" /></td>
                                        <td align="center">
                                            <button type="button" class="btn btn-primary btn-circle" onclick="window.open('<joy:JoyBasicURL object="reltermmetric" actiontype="edit" />&TRM_CLUSTER_ID=<joy:ActionMatrixByRowTag name="TRM_CLUSTER_ID" />', '_self');"><i class="fa fa-edit"></i></button>
                                            <button type="button" class="btn btn-danger btn-circle" onclick="Delete('<joy:JoyBasicURL object="reltermmetric" actiontype="delete" />&TRM_CLUSTER_ID=<joy:ActionMatrixByRowTag name="TRM_CLUSTER_ID" />');"><i class="fa fa-times"></i></button>
                                        </td>
                                     </tr>
                                    </joy:ActionMatrixRowLoopTag> 
                                </table>  
                                <joy:JoyFormTag object='reltermmetric' actiontype='add' name='frmEditItem' inline="true">
                                <div class="col-lg-12 form-group">
                                    <label for="TERM_NAME">Add a new Business Term (select below)</label><P>
                                    <joy:ActionComboBoxTag name="TERM_NAME" CSSClass="js-states form-control" />
                                    <joy:JoyFormButtonTag id="btn1" label="<i class='fa fa-plus-circle'></i>&nbsp;Add new Business Term" CSSClass="btn btn-success" onclick="ValidateForm();" />
                                </div>
                                </joy:JoyFormTag>
                            </div>
                        </div>            
                    </div>            
                </div>            
            </div><!-- /.container-fluid -->
        </div><!-- /#page-wrapper -->
    </div><!-- /#wrapper -->

<jsp:directive.include file="../../templates/foot.jsp" />

<script>
function ValidateForm() {
    if(document.forms['frmEditItem'].TERM_NAME.value == '') { 
      alert('Please enter a business term'); 
    } else {
        document.forms['frmEditItem'].submit();
    }
} 

function Delete(link) {
    bootbox.confirm("Are you sure you want to delete this relationship ?", function(result) {
      if (result)
          window.open(link, '_self');
    }); 
} 

$(document).ready(function() {
    $('#matablelist').DataTable({
        responsive : true
    });
    $( '#TERM_NAME' ).select2({
        allowClear: true,
        placeholder: "Select an Term"
    });
});

</script>
</body>
</html>

