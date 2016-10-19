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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Management-AC-GI" />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Terms Type</h1>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">Manage Terms Types here</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="col-lg-6">
                                    <joy:JoyFormTag object='termtype' actiontype='update' name='frmEditItem' inline="false" > 
                                    <div class="form-group">
                                        <label>Identifier</label>
                                        <joy:ActionInputTextTag name="GIO_PK" size="100" readonly="true" CSSClass="inputtext" />
                                    </div>
                                    <div class="form-group">
                                        <label>Term Type</label>
                                        <joy:ActionComboBoxTag name="GIO_TERMTYPE_NAME" CSSClass="combobox form-control" />
                                    </div>
                                    <div class="form-group">
                                        <label>Image-Icon</label>
                                        <joy:ActionComboBoxTag name="GIO_ICON_PATHNAME" CSSClass="combobox form-control" />
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

<SCRIPT type='text/javascript'>
    $(document).ready(function(){
        $( '#GIO_TERMTYPE_NAME' ).select2({
            allowClear: true,
            placeholder: "Select an term type Icon"
        });
        $( '#GIO_ICON_PATHNAME' ).select2({
            allowClear: true,
            placeholder: "Select an filename"
        });
        $( "#btn1" ).button();
        $( "#btn2" ).button();
        $( "#btn3" ).button();
    });
</SCRIPT>
</body>
</html>
