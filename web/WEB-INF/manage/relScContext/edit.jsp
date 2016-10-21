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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Management-AC-CS" />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Scorecards & Contexts relationships</h1>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">Manage Scorecards & Contexts relationships</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <joy:JoyFormTag object='relsccontext' actiontype='update' name='frmEditItem' inline="false" >
                                <div class="form-group">
                                    <label>Identifier</label>
                                    <joy:ActionInputTextTag name="SCX_PK" readonly="true" CSSClass="inputtext" />
                                </div>
                                <div class="form-group">
                                    <label>Context Name</label>
                                    <joy:ActionComboBoxTag name="CON_DESCRIPTION" CSSClass="combobox form-control" />
                                </div>
                                <div class="form-group">
                                    <label>Scorecard Name</label>
                                    <joy:ActionComboBoxTag name="SCO_NAME" CSSClass="combobox form-control" />
                                </div>
                                <div class="form-group">
                                    <label>Description</label>
                                    <joy:ActionInputTextTag name="SCX_DESCRIPTION" CSSClass="form-control" />
                                </div>
                                <div class="col-lg-12">
                                    <joy:JoyFormButtonTag id="btn1" back="true" label="Back" CSSClass="btn btn-default" />
                                    <joy:JoyFormButtonTag id="btn2" label="Update" submit="true" CSSClass="btn btn-primary" />
                                    <joy:JoyFormButtonTag id="btn3" label="Reset" cancel="true" CSSClass="btn btn-warning" />
                                </DIV>
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
    $(document).ready(function(){
        $( '#CON_DESCRIPTION' ).select2({
            allowClear: true,
            placeholder: "Select an Context"
        });
        $( '#SCO_NAME' ).select2({
            allowClear: true,
            placeholder: "Select an scorecard"
        });
        $( "#btn1" ).button();
        $( "#btn2" ).button();
        $( "#btn3" ).button();
    });
</SCRIPT>
</body>
</html>


