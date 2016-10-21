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
                            <div class="panel-heading"><UI:dgmGlyphe name="dqaxis" />View by Data Quality Dimension</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <joy:JoyFormTag inline="true" object='bydqaxis' actiontype='display' name='myform'>
                                <div class="form-group col-lg-12">
                                    <joy:ActionComboBoxTag name="dqaxis" CSSClass="js-states form-control" />
                                    <joy:JoyFormButtonTag id="btn1" label="Go" submit="true" CSSClass="btn btn-primary" />
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
<SCRIPT type='text/javascript'>
    $(document).ready(function(){
        $( '#dqaxis' ).select2({
            placeholder: "Select an DQ Dimension"
        });
        $( "#btn1" ).button();
    });
</SCRIPT>
</body>
</html>