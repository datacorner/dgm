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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Analytics-BM-Relationships" />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Business Relationships</h1>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading"><i class="fa fa-pagelines fa-fw"></i>&nbsp;View the Business Terms Relationships here</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="dataTable_wrapper">
                                    <joy:JoyFormTag object='mapbyterm' actiontype='display' name='myform' >   
                                    <joy:ActionComboBoxTag name="term" CSSClass="combobox form-control" />
                                    &nbsp;
                                    <joy:ActionComboBoxTag name="nbhops" CSSClass="combobox form-control" />
                                    <joy:JoyFormButtonTag id="btn1" label="View" submit="true" CSSClass="btn btn-primary" />
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
        $( '#nbhops' ).select2();
        $( '#term' ).select2({
            placeholder: "Select an Term"
        });
        $( "#btn1" ).button();
    });
</SCRIPT>
</body>
</html>
                                
                                
