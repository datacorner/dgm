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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Administration-CA" />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Application Health check</h1>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">You can here Check the Application configuration</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="dataTable_wrapper">
                                    <joy:JoyFormButtonTag id="btn1" label="Check Application entities"  CSSClass="btn btn-default" onclick="loadJSON('./rest/checks', 'check')" />
                               </div>
                            </div>
                        </div>            
                    </div>            
                </div>
                                    
                <div class="row">
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">Entity intialization status</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <table id="Entitycheck" class="table table-striped table-bordered table-hover"  cellspacing="0" width="100%">
                                    <thead>
                                        <tr>
                                            <th>Entity</th>
                                            <th>Status</th>
                                            <th>Nb Rows</th>
                                            <th>Message</th>
                                        </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>            
                    </div>            
                    
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">Tables intialization status</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <table id="TableCheck" class="table table-striped table-bordered table-hover"  cellspacing="0" width="100%">
                                    <thead>
                                        <tr>
                                            <th>Entity</th>
                                            <th>Status</th>
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

<jsp:directive.include file="../templates/foot.jsp" />

<script type="text/javascript">
    function callbackSuccess(content, tag) {
        var t1 = $('#Entitycheck').DataTable();
        for (i=0; i < content.EntityCheck.length; i++) {
            t1.row.add( [
                content.EntityCheck[i].Check.Entity,
                content.EntityCheck[i].Check.Status,
                content.EntityCheck[i].Check.Count,
                content.EntityCheck[i].Check.Message
            ] ).draw( false );
        }
        
        var t2 = $('#TableCheck').DataTable();
        for (i=0; i < content.TableCheck.length; i++) {
            t2.row.add( [
                content.TableCheck[i].Check.Entity,
                content.TableCheck[i].Check.Status
            ] ).draw( false );
        }
    }

    function callbackError(tag) {
            document.getElementById("zone").innerHTML = "Error";
    }
    $( "#btn1" ).button();
</script>

</body>
</html>
        
        
        
        
        

