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
                        <h1 class="page-header">Business Term</h1>
                    </div>
                </div>
                
                <joy:JoyFormTag object='lndterms' actiontype='update' name='frmEditItem' inline="false" >
                    <joy:ActionHiddenTag name="NEW"   />  
                <div class="row">
                    <div class="col-lg-2">
                        <joy:NaviLeft2ndMenuTag xmlconfig="joy-menu-landing.xml" activemenuid="term" />   
                    </div>
                    
                    <div class="col-lg-10">
                        <div class="panel panel-default">
                            <div class="panel-heading">Manage Business Term</div>
                            
                            <div class="row">
                                <div class="col-lg-12">  
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label>Identifier</label>
                                            <joy:ActionInputTextTag name="JOYFUNCKEY" CSSId="JOYFUNCKEY" CSSClass="form-control" required="yes" maxlength="10" />
                                            <div class="help-block with-errors"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                                    
                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label>Name</label>
                                            <joy:ActionInputTextTag name="TERM_NAME" CSSId="TERM_NAME" CSSClass="form-control" />
                                        </div>
                                        <div class="form-group">
                                            <label>Status</label>
                                            <joy:ActionInputTextTag name="TERM_STATUS"   CSSClass="form-control" />
                                        </div>
                                        <div class="form-group">
                                            <label>Func. Key</label>
                                            <joy:ActionInputTextTag name="TERM_FUNCKEY"   CSSClass="form-control" />
                                        </div>
                                        <div class="form-group">
                                            <label>Description</label>
                                            <joy:ActionInputTextTag name="TERM_DESCRIPTION"   CSSClass="form-control" />
                                        </div>
                                        <div class="form-group">
                                            <label>Usage</label>
                                            <joy:ActionInputTextTag name="TERM_USAGE"   CSSClass="form-control" />
                                        </div>
                                        <div class="form-group">
                                            <label>Example</label>
                                            <joy:ActionInputTextTag name="TERM_EXAMPLE"   CSSClass="form-control" />
                                        </div>
                                        <div class="form-group">
                                            <label>Phase</label>
                                            <joy:ActionInputTextTag name="TERM_PHASE"   CSSClass="form-control" />
                                        </div>

                                    </DIV>
                                </div>
                                        
                                <div class="col-lg-6">
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label>Owner</label>
                                            <joy:ActionInputTextTag name="TERM_OWNER"   CSSClass="form-control" />
                                        </div>
                                        <div class="form-group">
                                            <label>Owner Email</label>
                                            <joy:ActionInputTextTag name="TERM_OWNER_EMAIL"   CSSClass="form-control" />
                                        </div>
                                        <div class="form-group">
                                            <label>Steward</label>
                                            <joy:ActionInputTextTag name="TERM_STEWARD"   CSSClass="form-control" />
                                        </div>                                
                                        <div class="form-group">
                                            <label>Steward Email</label>
                                            <joy:ActionInputTextTag name="TERM_STEWARD_EMAIL"   CSSClass="form-control" />
                                        </div>  
                                        <div class="form-group">
                                            <label>Glossary</label>
                                            <joy:ActionComboBoxTag name="GLOSSARY" CSSClass="combobox form-control" />
                                        </div>
                                        <div class="form-group">
                                            <label>Category</label>
                                            <joy:ActionComboBoxTag name="CATEGORY" CSSClass="combobox form-control" />
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
        $( '#GLOSSARY' ).select2({
            allowClear: true,
            placeholder: "Select an glossary"
        });
        $( '#CATEGORY' ).select2({
            allowClear: true,
            placeholder: "Select an category"
        });
    });
</SCRIPT>
</body>
</html>