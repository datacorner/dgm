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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Administration-BC" />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Import / Export application data</h1>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">Export here</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="dataTable_wrapper">
                                    <UL>
                                        <li><a href="./rest/data/APP_PARAMS">Export the application parameters here</A></li>
                                        <li><a href="./rest/data/REL_TERM_METRIC">Export the Terms/DQ Types/metrics here</A></li>
                                        <li><a href="./rest/data/SRC_CONTEXT">Export the Contexts here</A></li>
                                        <li><a href="./rest/data/SRC_DQAXIS">Export the Data Quality types here</A></li>
                                        <li><a href="./rest/data/SRC_TERMTYPE">Export the Glossary icons here</A></li>
                                        <li><a href="./rest/data/SRC_SCORECARD">Export the Scorecards informations here</A></li>
                                    </UL>   
                                </div>
                            </div>
                        </div>            
                    </div>  
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">import here</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="dataTable_wrapper">
                                    <UL>
                                        <li>
                                            <h4>Import Application parameters</h4>
                                            <FORM ACTION="<joy:JoyBasicURL object="import" actiontype="list" />&import=APP_PARAMS" ENCTYPE="multipart/form-data" METHOD=POST NAME="APP_PARAMS">
                                            <INPUT TYPE=FILE NAME=APP_PARAMS>
                                            <INPUT type="submit" value="Import data">
                                            </FORM>  
                                        </li>
                                        <li>
                                            <h4>Import the Terms/DQ Types/metrics relationships</h4>
                                            <FORM ACTION="<joy:JoyBasicURL object="import" actiontype="list" />&import=REL_TERM_METRIC" ENCTYPE="multipart/form-data" METHOD=POST NAME="REL_TERM_METRIC">
                                            <INPUT TYPE=FILE NAME=REL_TERM_METRIC>
                                            <INPUT type="submit" value="Import data">
                                            </FORM>  
                                        </li>
                                    </UL>  
                                </div>
                            </div>
                        </div>            
                    </div>                                       
                </div>            

            </div><!-- /.container-fluid -->
        </div><!-- /#page-wrapper -->
    </div><!-- /#wrapper -->

<jsp:directive.include file="../templates/foot.jsp" />

</body>
</html>
