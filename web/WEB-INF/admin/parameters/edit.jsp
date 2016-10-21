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
                                    <joy:JoyFormTag object='parameters' actiontype='update' name='frmEditItem' inline="false" >  
                                        <joy:ActionHiddenTag name="PARAM_DISPLAY" />
                                        <div class="form-group">
                                            <label>Name</label>
                                            <joy:ActionInputTextTag name="PARAM_NAME" CSSClass="form-control" />
                                        </div>
                                        <div class="form-group">
                                            <label>Label</label>
                                            <joy:ActionInputTextTag name="PARAM_LABEL" CSSClass="form-control" />
                                        </div>
                                        <div class="form-group">
                                            <label>Numeric Value</label>
                                            <joy:ActionInputTextTag name="PARAM_INTVALUE" CSSClass="form-control" />
                                        </div>
                                        <div class="form-group">
                                            <label>String Value</label>
                                            <joy:ActionInputTextTag name="PARAM_STRVALUE" CSSClass="form-control" />
                                        </div>
                                        <div class="col-lg-12">
                                            <joy:JoyFormButtonTag id="btn1" back="true" label="Back" CSSClass="btn btn-default" />
                                            <joy:JoyFormButtonTag id="btn2" label="Update" submit="true" CSSClass="btn btn-primary" />
                                            <joy:JoyFormButtonTag id="btn3" label="Cancel" cancel="true" CSSClass="btn btn-warning" />
                                        </DIV>
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
<SCRIPT type='text/javascript'>
    $(document).ready(function(){
        $( "#btn1" ).button();
        $( "#btn2" ).button();
        $( "#btn3" ).button();
    });
</SCRIPT>
</body>
</html>
