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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Administration-InternalImport" />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Native Import</h1>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">Direct Import from the import Area</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="dataTable_wrapper">
                                    <div class="form-group">
                                        <button type="button" class="btn btn-warning" onclick="loadJSON('./task/landingload/landingload', 'landingload');"><i class="fa fa-arrow-circle-right "></i>&nbsp;Import From Landing tables</button><p>
                                        <p><code>This task gathers all the data (Terms, Categories, Glossary, etc.) from the import area and load them in the Datamart.</code>
                                    </div>      
                                    <div class="form-group">
                                        <button type="button" class="btn btn-warning" onclick="loadJSON('./task/dtmpurge/dtmpurge', 'dtmpurge');"><i class="fa fa-arrow-circle-right "></i>&nbsp;Datamart Purge</button><p>
                                        <p><code>BE CAREFUL, this task purge all the data into the Datamart !</code>
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
                            <div class="panel-heading">Landing Contents</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <table id="landing" class="table table-striped table-bordered table-hover"  cellspacing="0" width="100%">
                                    <thead>
                                        <tr>
                                            <th>Import table</th>
                                            <th>Count</th>
                                        </tr>
                                    </thead>
                                </table>
                                <div class="form-group">
                                    <button type="button" class="btn btn-warning" onclick="loadJSON('./task/landingpurge/landingpurge', 'landingpurge');"><i class="fa fa-arrow-circle-right "></i>&nbsp;Landing Purge</button><p>
                                    <p><code>BE CAREFUL, this task purge all the data into the import tables !</code>
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
            case 'landingload':
                bootbox.alert('Landing tables are importing in background.', null);
                break;
            case 'dtmpurge':
                bootbox.alert('Purging datamart in background.', null);
                break;
            case 'landingpurge':
                bootbox.alert('Purging import tables in background.', null);
                break;
            case 'landingcount':
                var t1 = $('#landing').DataTable();
                t1.clear();
                for (i=0; i < content.LandingCount.length; i++) {
                    t1.row.add( [
                        content.LandingCount[i].landing.tablename,
                        content.LandingCount[i].landing.tablecount
                    ] ).draw( false );
                }
                break;
            default:
        }
    }
    loadJSON('./rest/landingcount', 'landingcount');
</script>
</body>
</html>