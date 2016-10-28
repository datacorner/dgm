<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dgm" uri="/WEB-INF/dgmTags.tld"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="UI" %>
<%@taglib prefix="joy" uri="/WEB-INF/joyTags.tld"%>

<html lang="en">

<head>
    <jsp:directive.include file="../../../templates/head.jsp" />
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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Data-Landing" />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <joy:NaviBreadCrumbsTag xmlconfig="joy-menu.xml" activemenuid="Data-Landing" />
                        <h1 class="page-header">Landing tables management</h1>
                    </div>
                </div>
                
                <joy:JoyFormTag object='lndmetric' actiontype='update' name='frmEditItem' inline="false" >
                    <joy:ActionHiddenTag name="NEW"   />  
                <div class="row">
                    <div class="col-lg-2">
                        <joy:NaviLeft2ndMenuTag xmlconfig="joy-menu-landing.xml" activemenuid="metric" />   
                    </div>
                    
                    <div class="col-lg-10">
                        <div class="panel panel-default">
                            <div class="panel-heading">Manage Metrics</div>
                            
                            <div class="row">
                                <div class="col-lg-12">  
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label>Identifier</label>
                                            <div class="input-group">
                                                <span class="input-group-addon " id="basic-addon2"><span class="glyphicon glyphicon-star" aria-hidden="true"></span> </span>
                                                <joy:ActionInputTextTag name="JOYFUNCKEY" CSSId="JOYFUNCKEY" CSSClass="form-control" required="yes" maxlength="10" placeholder="Unique Identifier" ariadescribedby="basic-addon2" />
                                            </div>
                                            <label>Scorecard</label>
                                            <div class="row">
                                                <div class="col-lg-5">
                                                    <joy:ActionComboBoxTag name="SCORECARD_CBO" CSSClass="combobox form-control" id="SCORECARD_CBO" />
                                                </div>  
                                                <div class="col-lg-1">
                                                    <joy:JoyFormButtonTag id="btnselect1" label="Select >" CSSClass="btn btn-default largeinput" onclick="document.getElementById('SCORECARD_KEY').value = document.getElementById('SCORECARD_CBO').value;" />
                                                </div>  
                                                <div class="col-lg-6">
                                                    <joy:ActionInputTextTag name="SCORECARD_KEY" CSSClass="form-control" CSSId="SCORECARD_KEY" placeholder="Scorecard Identifier" />
                                                </div> 
                                            </div>
                                            <label>Scorecard Group</label>
                                            <div class="row">
                                                <div class="col-lg-5">
                                                    <joy:ActionComboBoxTag name="SCORECARD_GROUP_CBO" CSSClass="combobox form-control" id="SCORECARD_GROUP_CBO" />
                                                </div>  
                                                <div class="col-lg-1">
                                                    <joy:JoyFormButtonTag id="btnselect2" label="Select >" CSSClass="btn btn-default largeinput" onclick="document.getElementById('SCORECARDGRP_KEY').value = document.getElementById('SCORECARD_GROUP_CBO').value;" />
                                                </div>  
                                                <div class="col-lg-6">
                                                    <joy:ActionInputTextTag name="SCORECARDGRP_KEY" CSSClass="form-control" CSSId="SCORECARDGRP_KEY" placeholder="Scorecard Group Identifier" />
                                                </div> 
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                                    
                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label>Name</label>
                                            <joy:ActionInputTextTag name="MET_NAME" CSSId="TERM_NAME" CSSClass="form-control" placeholder="Metric Name" />
                                        </div>
                                        <div class="form-group">
                                            <label>Description</label>
                                            <joy:ActionInputTextTag name="MET_DESRIPTION"   CSSClass="form-control" placeholder="Metric Description" />
                                        </div>
                                        <div class="form-group">
                                            <label>Weight</label>
                                            <joy:ActionInputTextTag name="MET_WEIGHT"   CSSClass="form-control" placeholder="Metric Weight" />
                                        </div>
                                        <div class="form-group">
                                            <label>Number of Valid rows</label>
                                            <joy:ActionInputTextTag name="FRS_VALID_ROWS"   CSSClass="form-control" placeholder="Number of Valid rows" />
                                        </div>
                                        <div class="form-group">
                                            <label>Total of Rows</label>
                                            <joy:ActionInputTextTag name="FRS_TOTAL_ROWS"   CSSClass="form-control" placeholder="Number total of rows" />
                                        </div>
                                        <div class="form-group">
                                            <label>Cost</label>
                                            <joy:ActionInputTextTag name="FRS_COST"   CSSClass="form-control" placeholder="Estimated Cost" />
                                        </div>
                                        <div class="form-group">
                                            <label>Score</label>
                                            <joy:ActionInputTextTag name="MET_SCORE"   CSSClass="form-control" placeholder="Score in percent" />
                                        </div>
                                        <div class="form-group">
                                            <label>Cost Unit</label>
                                            <joy:ActionInputTextTag name="FRS_COSTUNIT"   CSSClass="form-control" placeholder="Cost Unit" />
                                        </div>
                                    </DIV>
                                </div>
                                        
                                <div class="col-lg-6">
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label>Metric Type</label>
                                            <joy:ActionInputTextTag name="MET_TYPE"   CSSClass="form-control" placeholder="Type of Metric" />
                                        </div>                                
                                        <div class="form-group">
                                            <label>Connection</label>
                                            <joy:ActionInputTextTag name="MET_CONNECTION"   CSSClass="form-control" placeholder="Data Source Connection" />
                                        </div>  
                                        <div class="form-group">
                                            <label>Data Source</label>
                                            <joy:ActionInputTextTag name="MET_SOURCENAME"   CSSClass="form-control" placeholder="Data Source Name"/>
                                        </div>
                                        <div class="form-group">
                                            <label>Calculation date (Format: yyyy-mm-dd hh:ii:ss)</label>
                                            <joy:ActionInputTextTag name="FRS_CALCULATION_DATE"  CSSId="FRS_CALCULATION_DATE"  CSSClass="form-control" placeholder="Metric Calcultation date" />
                                        </div>                                        
                                    </div>
                                </div>
                            </div>
                                        
                            <div class="row">
                                <div class="col-lg-12">  
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <joy:JoyFormButtonTag id="btn1" back="true" label="Back" CSSClass="btn btn-default" />
                                            <joy:JoyFormButtonTag id="btn2" label="Update" submit="true" CSSClass="btn btn-primary" />
                                            <joy:JoyFormButtonTag id="btn3" label="Cancel" cancel="true" CSSClass="btn btn-warning" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>            
                    </div>            
                </div>    
                </joy:JoyFormTag>
            </div><!-- /.container-fluid -->
        </div><!-- /#page-wrapper -->
    </div><!-- /#wrapper -->

<jsp:directive.include file="../../../templates/foot.jsp" />

<SCRIPT type='text/javascript'>
    $(document).ready(function(){
        $( "#JOYFUNCKEY").validator();
        $( "#btn1" ).button();
        $( "#btn2" ).button();
        $( "#btn3" ).button();
        $( "#btnselect1" ).button();
        $( "#btnselect2" ).button();
        $( '#SCORECARD_CBO' ).select2({
            allowClear: true,
            placeholder: "Select an scorecard"
        });
        $( '#SCORECARD_GROUP_CBO' ).select2({
            allowClear: true,
            placeholder: "Select an group"
        });
        $("#FRS_CALCULATION_DATE").datetimepicker({
            format: "yyyy-mm-dd hh:ii:ss",
            autoclose: true,
            todayBtn: true
        });
        
    });
</SCRIPT>
</body>
</html>