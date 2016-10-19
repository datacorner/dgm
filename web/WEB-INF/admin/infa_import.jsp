<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dgm" uri="/WEB-INF/dgmTags.tld"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="UI" %>
<%@taglib prefix="joy" uri="/WEB-INF/joyTags.tld"%>

<html lang="en">

<head>
    <jsp:directive.include file="../templates/head.jsp" />
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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Administration-II" />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Informatica Platform connectivity</h1>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">Collect Data from Informatica platform</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="dataTable_wrapper">
                                    <div class="form-group">
                                        <button type="button" class="btn btn-warning" onclick="loadJSON('./task/infawf/wf_Landing', 'wf_Landing');"><i class="fa fa-arrow-circle-right "></i>&nbsp;Collect IDQ Scorecards</button><p>
                                        <p><code>This tasks collects all the scorecards metrics and push them directly into the datamart</code>
                                    </div>
                                    <div class="form-group">
                                        <joy:JoyFormButtonTag id="btn1" label="Go to tasks"  CSSClass="btn btn-default" link="true" object="tasks" />
                                    </div>     
                                </div>
                            </div>
                        </div>            
                    </div> 
                    
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">Informatica platform Connectivity</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="dataTable_wrapper">
                                    <div class="form-group">
                                        <div class="form-group">
                                            <label>Informatica Command line: </label>
                                            <joy:ActionInputTextTag name="CMD" CSSClass="form-control" readonly="true" />
                                        </div>
                                        <div class="form-group">
                                            <label>Informatica Domain: </label>
                                            <joy:ActionInputTextTag name="DOMAIN" CSSClass="form-control" readonly="true"  />
                                        </div>
                                        <div class="form-group">
                                            <label>Informatica Application Name: </label>
                                            <joy:ActionInputTextTag name="APP" CSSClass="form-control" readonly="true"  />
                                        </div>
                                        <div class="form-group">
                                            <label>Informatica Data Integration Service Name: </label>
                                            <joy:ActionInputTextTag name="DIS" CSSClass="form-control" readonly="true"  />
                                        </div>
                                        <div class="form-group">
                                            <label>Informatica User: </label>
                                            <joy:ActionInputTextTag name="USER" CSSClass="form-control" readonly="true"  />
                                        </div>
                                        <div class="form-group">
                                            <label>Informatica Password: </label>
                                            <joy:ActionInputTextTag name="PWD" CSSClass="form-control" readonly="true"  />
                                        </div>
                                        <div class="form-group">
                                            <joy:JoyFormButtonTag id="btn1" label="Modify parameters"  CSSClass="btn btn-default" link="true" object="parameters" actiontype="list" />
                                        </DIV>
                                    </div>
                                </div>
                            </div>
                        </div>            
                    </div>                               
                </div>            

            </div><!-- /.container-fluid -->
        </div><!-- /#page-wrapper -->
    </div><!-- /#wrapper -->

<jsp:directive.include file="../templates/foot.jsp" />
<script>
    function callbackSuccess(content, tag) {
        switch(tag) {
            case 'wf_Landing':
                bootbox.alert('Informatica Workflow for refreshing scorecards launched sucessfully in background.', null);
                break;
            default:
        }
    }
</script>
</body>
</html>