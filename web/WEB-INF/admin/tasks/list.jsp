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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Administration-Tasks" />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Tasks List</h1>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">Tasks</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <joy:JoyFormButtonTag id="btn1" label="Refresh tasks list"  CSSClass="btn btn-default" onclick="loadJSON('./rest/taskslist', 'tasks')" /><p>
                                <table id="tasks" class="table table-striped table-bordered table-hover"  cellspacing="0" width="100%">
                                    <thead>
                                        <tr>
                                            <th>Name</th>
                                            <th>Status</th>
                                            <th>Started</th>
                                            <th>End</th>
                                            <th>Duration</th>
                                            <th style="width: 25%;">Message</th>
                                            <th style="width: 50%;">Trace</th>
                                        </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>            
                    </div>                 
                </div>                   
            </div><!-- /.container-fluid -->
        </div><!-- /#page-wrapper -->
    </div><!-- /#wrapper -->

<jsp:directive.include file="../../templates/foot.jsp" />

<script type="text/javascript">
function callbackSuccess(content, tag) {
    var t1 = $('#tasks').DataTable();
    t1.clear();
    for (i=0; i < content.length; i++) {
        var tracetmp = '';
        for (j=0; j < content[i].trace.length; j++) {
            tracetmp = tracetmp + content[i].trace[j].message + "<BR>";
        }
        
        t1.row.add( [
            content[i].name,
            content[i].status,
            content[i].start,
            content[i].end,
            content[i].duration,
            content[i].message,
            tracetmp
        ] ).draw( false );
    }
}

function callbackError(tag) {
    var t1 = $('#tasks').DataTable();
    t1.clear();
}

$( "#btn1" ).button();
loadJSON('./rest/taskslist', 'tasks');
</script>

</body>
</html>
        
        
        
        
        

