<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dgm" uri="/WEB-INF/dgmTags.tld"%>
<%@taglib prefix="UI" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="joy" uri="/WEB-INF/joyTags.tld"%>
<%@taglib prefix="chart" tagdir="/WEB-INF/tags/charts" %>

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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Analytics-BG-Term" />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">

                <div class="row">
                    <div class="col-lg-12">
                        <joy:NaviBreadCrumbsTag xmlconfig="joy-menu.xml" activemenuid="Analytics-BG-Term" />
                        <h1 class="page-header"><IMG src='./images/glossary/<joy:ActionValueTag name="IMGICO" />' height="28px" />&nbsp;<joy:ActionValueTag name="TRM_NAME" /></h1>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-6">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="divtermblocheader">
                                    <p><UI:dgmGlyphe name="status" /><B>Status :</B> <joy:ActionValueTag name="TRM_PHASE" /></p>
                                    <p><UI:dgmGlyphe name="term" /><B>Type :</B> <joy:ActionValueTag name="TRT_NAME" /></p>
                                    <p><UI:dgmGlyphe name="user" /><B>Owner :</B> <joy:ActionValueTag name="TRM_OWNER_EMAIL" /></p>
                                    <p><UI:dgmGlyphe name="user" /><B>Steward :</B> <joy:ActionValueTag name="TRM_STEWARD_EMAIL" /></p>
                                    <p><UI:dgmGlyphe name="businessmap" /><A href="<joy:JoyBasicURL object="mapbyterm" actiontype="display" />&nbhops=3&term=<joy:ActionValueTag name="TRM_PK" />">Relationship map</A></p>
                                    <p><UI:dgmGlyphe name="common-configuration" /><A href="<joy:ActionValueTag name="CONFIG_TERM_LINK" />">Term Configuration</A></p>
                                </div>
                            </div> 
                        </div>   
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-yellow">
                                    <!--  <div class="panel-heading"><UI:dgmGlyphe name="term" />Business Term: <joy:ActionValueTag name="TRM_NAME" /></div> -->
                                    <!-- /.panel-heading -->
                                    <div class="panel-body">
                                        <DIV class="bloctitreprincipal">
                                        <h5>Metric <joy:ActionValueTag name="TRM_NAME" /> coming from <joy:ActionValueTag name="GLOSSARY_LINK" />&nbsp;/&nbsp;<joy:ActionValueTag name="CATEGORY_LINK" /> </h5>
                                        <joy:ActionConditionTag name="INFORMATICA">
                                            <H5><A href="<joy:ActionValueTag name="INFA_MM_LINK" />" target="other"><i class="fa fa-crosshairs fa-fw"></i>&nbsp;Informatica Metadata Data Lineage</a></h5>
                                            <H5><A href="<joy:ActionValueTag name="INFA_DIRECT_LINK" />" target="_self"><i class="fa fa-book fa-fw"></i>&nbsp;Jump to Business Term</a></h5>
                                        </joy:ActionConditionTag>
                                        </DIV>
                                        <joy:JoyFormTag inline="true" object='byterm' actiontype='display' name='myform'>
                                        <div class="form-group">
                                            <joy:ActionComboBoxTag name="term" CSSClass="js-states form-control" />
                                            <joy:JoyFormButtonTag id="btn1" label="Change" submit="true" CSSClass="btn btn-primary" />
                                        </div>
                                        </joy:JoyFormTag>
                                    </div>
                                </div>
                            </div>  
                        </div>
                        <div class="row">                
                            <div class="col-lg-12">
                                <div class="panel panel-red">
                                    <div class="panel-heading"><UI:dgmGlyphe name="glossary" />Glossary definition</div>
                                    <div class="panel-body" style="max-height: 300px; min-height: 300px;overflow-y: scroll;">
                                        <!-- Nav tabs -->
                                        <ul class="nav nav-tabs">
                                                <li class="active"><a href="#Description" data-toggle="tab">Description</a></li>
                                                <li><a href="#Usage" data-toggle="tab">Usage</a></li>
                                                <li><a href="#Example" data-toggle="tab">Example</a></li>
                                        </ul>

                                        <!-- Tab panes -->
                                        <div class="tab-content">
                                                <div class="tab-pane fade in active" id="Description">
                                                        <h4>Description</h4>
                                                        <p><joy:ActionValueTag name="TRM_DESCRIPTION" /></p>
                                                </div>
                                                <div class="tab-pane fade" id="Usage">
                                                        <h4>Usage</h4>
                                                        <p><joy:ActionValueTag name="TRM_USAGE" /></p>
                                                </div>
                                                <div class="tab-pane fade" id="Example">
                                                        <h4>Example</h4>
                                                        <p><joy:ActionValueTag name="TRM_EXAMPLE" /></p>
                                                </div>
                                        </div>
                                    </div>
                                </div>
                            </div>           
                        </div>  
                    </div> 
                    <div class="col-lg-6">
                        <div class="panel panel-primary">   
                           <div class="panel-heading"><UI:dgmGlyphe name="relationship" />Business Term Relationships</div>
                           <div class="panel-body" style="max-height: 300px;min-height: 600px;overflow-y: scroll;">
                               <div id="treeview" class=""></div>
                           </div>
                        </div>
                    </div>                 
                </DIV>
 

                            
            </div><!-- /.container-fluid -->
        </div><!-- /#page-wrapper -->
    </div><!-- /#wrapper -->

<jsp:directive.include file="../../templates/foot.jsp" />
<SCRIPT>
<chart:dgmDQAxisCounters_JS dqaxis="COUNTER_LIST" />
$(document).ready(function() {
    $( '#term' ).select2({
        placeholder: "Select an Term"
    });
    $( "#btn1" ).button();
});

// treeview display
function callbackSuccess(content, tag) {
    var jsonContent = JSON.stringify(content);
    $('#treeview').treeview({
      color: "#428bca",
      showBorder: false,
      enableLinks: true,
      data: jsonContent
    });
}
loadJSON('./rest/relterm/3/<joy:ActionValueTag name="TRM_PK" />', 'relationship');

</script>

</body>
</html>                                    

