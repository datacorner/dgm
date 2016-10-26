<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dgm" uri="/WEB-INF/dgmTags.tld"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="UI" %>
<%@taglib prefix="joy" uri="/WEB-INF/joyTags.tld"%>

<html lang="en">

<head>
    <jsp:directive.include file="../../templates/head.jsp" />
    <style type="text/css">
        #searchresult tr.odd:hover {
                background-color: #E6FF99;
        }
        #searchresult tr.even:hover {
                background-color: #E6FF99;
        }
    </style> 
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
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-3">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading"><UI:dgmGlyphe name="term" />Direct access</div>
                                    <!-- /.panel-heading -->
                                    <div class="panel-body">
                                        <joy:JoyFormTag inline="true" object='byterm' actiontype='display' name='myform'>
                                        <div class="form-group">
                                            <label>&nbsp;Just select a Business Term</label>
                                            <joy:ActionComboBoxTag name="term" CSSClass="js-states form-control" />
                                            <joy:JoyFormButtonTag id="btn1" label="Go" submit="true" CSSClass="btn btn-primary" />
                                        </div>
                                        </joy:JoyFormTag>
                                        <div class="form-group">
                                            <joy:JoyFormTag  object='byterm' actiontype='search' name='myform2'>
                                            <joy:ActionCheckBoxTag name="termsdefined" onclick="document.forms['myform2'].submit();" />&nbsp;Show all Business terms<P>
                                            </joy:JoyFormTag>
                                        </div>
                                    </div>
                                </div> 
                            </div> 
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading"><UI:dgmGlyphe name="term" />Search criterias</div>
                                    <!-- /.panel-heading -->
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label>&nbsp;Term Type</label>
                                            <joy:ActionComboBoxTag name="termtypes" CSSClass="js-states form-control" /><p>&nbsp;<p>
                                            <joy:JoyFormButtonTag id="btn2" label="Search" CSSClass="btn btn-primary" onclick="search();" />
                                        </div>
                                    </div>
                                </div>            
                            </div> 
                                
                        </div>
                    </div>
                    <div class="col-lg-9">
                        <div class="panel panel-default">
                            <div class="panel-heading"><UI:dgmGlyphe name="term" />Search results</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div id="pleasewait"></div>
                                <table id="searchresult" class="table table-striped table-bordered table-hover"  cellspacing="0" width="100%">
                                    <thead>
                                        <tr>
                                            <th style="width: 10%;">ID</th>
                                            <th style="width: 25%;">Name</th>
                                            <th>Description</th>
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
<SCRIPT>

$(document).ready(function() {
    $( '#term' ).select2({
        placeholder: "Select an Term"
    });

    $( '#termtypes' ).select2({
        placeholder: "Select an Term Type"
    });
    $( "#btn1" ).button();

    $('#searchresult').on('dblclick', 'tr', function (e) {
        var valSelected = $('td:eq(0)', this).html();
        window.open('<joy:JoyBasicURL actiontype="display" object="byterm" />&term=' + valSelected, '_self');
    });
});

function search() {
    var t1 = $('#searchresult').DataTable();
    t1.clear();
    t1.draw();
    //document.getElementById('searchresult').style.display="none";
    document.getElementById('pleasewait').innerHTML = '<P>Please wait while searching ...</P> ';
    var myurlsearch = './rest/search/SEARCH_TERM/TRT_FK/' + document.getElementById('termtypes').value;
    loadJSON(myurlsearch, 'search');
}

function callbackSuccess(content, tag) {
    var t1 = $('#searchresult').DataTable();
    t1.clear();
    document.getElementById('pleasewait').innerHTML = '';
    //document.getElementById('searchresult').style.display="block";
    for (i=0; i < content.SEARCH_TERM.length; i++) {
        t1.row.add( [
            content.SEARCH_TERM[i].row.TRM_PK.value,
            content.SEARCH_TERM[i].row.TRM_NAME.value,
            content.SEARCH_TERM[i].row.TRM_DESCRIPTION.value
        ] ).draw( false );
    }
}

function callbackError(tag) {
    var t1 = $('#searchresult').DataTable();
    t1.clear();
    t1.draw();
}

</SCRIPT>

</body>
</html>