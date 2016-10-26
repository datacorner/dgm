<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dgm" uri="/WEB-INF/dgmTags.tld"%>
<%@taglib prefix="UI" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="joy" uri="/WEB-INF/joyTags.tld"%>
<%@taglib prefix="chart" tagdir="/WEB-INF/tags/charts" %>
<%@taglib prefix="dsb" uri="/WEB-INF/dsbTags.tld"%>

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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Dashboards" />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <joy:NaviBreadCrumbsTag xmlconfig="joy-menu.xml" activemenuid="Dashboards" />
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-3">
                        <div class="form-group">
                        <joy:JoyFormTag object='dashboard' actiontype='display' name='myform' id="myform" >
                        <label>Change Dashboard</label>
                        <joy:ActionComboBoxTag name="CBO_DASHBOARDS" CSSClass="js-states form-control" />
                        </div>
                        </joy:JoyFormTag>
                    </div>
                </div>
                
                <dsb:DsbHTMLRowLoop name="DASHBOARD" bootstrapSupport="true"> 
                    <dsb:DsbHTMLColLoop bootstrapSupport="true">
                        <dsb:DsbHTMLBloc body="true" />
                    </dsb:DsbHTMLColLoop>
                </dsb:DsbHTMLRowLoop>   

            </div>
        </div>
    </div>

<jsp:directive.include file="../templates/foot.jsp" />
<SCRIPT type='text/javascript'>
    $(document).ready(function(){
        $( '#CBO_DASHBOARDS' ).select2({
            placeholder: "Select an Dashboard"
        });
        $('#CBO_DASHBOARDS').on('change', function (evt) {
            $("#myform").submit();
        });
    });

    function createXHR() {
        var request = false;
            try {
                    request = new ActiveXObject('Msxml2.XMLHTTP');
            } catch (err2) {
                try {
                        request = new ActiveXObject('Microsoft.XMLHTTP');
                } catch (err3) {
                    try {
                            request = new XMLHttpRequest();
                    } catch (err1) {
                            request = false;
                    }
                }
            }
        return request;
    }
</SCRIPT>

<dsb:DsbHTMLRowLoop name="DASHBOARD" bootstrapSupport="false"> 
    <dsb:DsbHTMLColLoop bootstrapSupport="false">
        <dsb:DsbHTMLBloc body="false" />
    </dsb:DsbHTMLColLoop>
</dsb:DsbHTMLRowLoop>   

</body>
</html>