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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Administration-A" />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Parameters</h1>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">Manage here the application parameters</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="dataTable_wrapper">
                                    <table id="matablelist" class="table table-striped table-bordered table-hover"  cellspacing="0" width="100%">                                                                                                                                                                                                                                                                                                                          
                                        <thead>
                                            <tr>
                                                <th>Name</th>
                                                <th>Description</th>
                                                <th>Numeric Value</th>
                                                <th>String value</th>
                                                <th style="text-align: center">Manage</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <joy:ActionMatrixRowLoopTag name="LIST">
                                            <tr>
                                                <td><B><joy:ActionMatrixByRowTag name="PARAM_NAME" /></B></td>
                                                <td><joy:ActionMatrixByRowTag name="PARAM_LABEL" /></td>
                                                <td><joy:ActionMatrixByRowTag name="PARAM_INTVALUE" /></td>
                                                <td><joy:ActionMatrixByRowTag name="PARAM_STRVALUE" /></td>
                                                <td align="center">
                                                    <button type="button" class="btn btn-primary btn-circle" onclick="window.open('<joy:JoyBasicURL object="parameters" actiontype="edit" />&parameter=<joy:ActionMatrixByRowTag name="PARAM_NAME" />', '_self');"><i class="fa fa-edit"></i></button>
                                                </td>
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

<jsp:directive.include file="../../templates/foot.jsp" />

<script>
$(document).ready(function() {
    $('#matablelist').DataTable({
            responsive: true
    });
});
</script>
</body>
</html>
