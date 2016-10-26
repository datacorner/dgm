<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dgm" uri="/WEB-INF/dgmTags.tld"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="UI" %>
<%@taglib prefix="joy" uri="/WEB-INF/joyTags.tld"%>

<html lang="en">

<head>
    <jsp:directive.include file="../../templates/head.jsp" />
  <style type="text/css">
    #mycontainer {
      border: 0px solid lightgray;
      /* overflow-y: scroll; */
    }
    .panel-resizable {
      resize: vertical;
      overflow: hidden;
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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Analytics-BM-Relationships" />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <joy:NaviBreadCrumbsTag xmlconfig="joy-menu.xml" activemenuid="Analytics-BM-Relationships" />
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-3">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading"><i class="fa fa-gears fa-fw"></i>&nbsp;Change the Business Terms Relationships view</div>
                                    <!-- /.panel-heading -->
                                    <div class="panel-body">
                                        <div class="dataTable_wrapper">
                                            <joy:JoyFormTag object='mapbyterm' actiontype='display' name='myform' >   
                                                <label>Business Term</label>
                                                <joy:ActionComboBoxTag name="term" CSSClass="combobox form-control" /> <P>&nbsp;<P>
                                                <label>Map Depth</label>
                                                <joy:ActionComboBoxTag name="nbhops" CSSClass="combobox form-control" /> <P>&nbsp;<P>
                                                <joy:JoyFormButtonTag id="btn1" label="Refresh" submit="true" CSSClass="btn btn-primary" />
                                            </joy:JoyFormTag>
                                        </div>
                                    </div>
                                </div>  
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading"><i class="fa fa-gears fa-fw"></i>&nbsp;View Management</div>
                                    <!-- /.panel-heading -->
                                    <div class="panel-body">
                                        <div class="dataTable_wrapper">
                                            <div class="form-group">
                                                <label>Hierarchy type</label>
                                                <SELECT  name="hierarchy"  class="js-states form-control"  id="hierarchy">
                                                    <OPTION Value='none'>None</OPTION>
                                                    <OPTION Value='UD'>Up to Down</OPTION>
                                                    <OPTION Value='DU'>Down to Up</OPTION>
                                                    <OPTION Value='LR'>Left to Right</OPTION>
                                                    <OPTION Value='RL'>Right to Left</OPTION>
                                                </SELECT>
                                            </div>
                                            <div class="form-group">
                                                <label><joy:ActionCheckBoxTag name="termtypecriteria"/>&nbsp;Term Type Cluster</label>
                                                <joy:ActionComboBoxTag name="termtypes" CSSClass="js-states form-control" />
                                            </div>
                                            <div class="form-group">
                                            <joy:JoyFormButtonTag id="btnC1" label="Cluster"  CSSClass="btn btn-primary" onclick="clusterByTermType()" />
                                            <joy:JoyFormButtonTag id="btnC2" label="Reset Cluster"  CSSClass="btn btn-warning" onclick="draw()" />
                                            </div>
                                        </div>
                                    </div>
                                </div>  
                            </div>
                        </div>      
                    </div>
                    
                    <div class="col-lg-9">
                        <div class="panel panel-primary" id ="panelnetwork">
                            <div class="panel-heading"><i class="fa fa-pagelines fa-fw"></i>&nbsp;View the Business Terms Relationships here</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body panel-resizable" id="containerplus">
                                <div id="mycontainer"></div>
                            </div>
                        </div>            
                    </div>    
                    
                </div> 

            </div><!-- /.container-fluid -->
        </div><!-- /#page-wrapper -->
    </div><!-- /#wrapper -->

<jsp:directive.include file="../../templates/foot.jsp" />

<SCRIPT type='text/javascript'>
    $(document).ready(function(){
        $('#panelnetwork').lobiPanel({
           reload: false,
           close: false,
           editTitle: false,
           sortable: true
        });
        $( '#nbhops' ).select2( { width: '100%'  } );
        $( '#term' ).select2({
            placeholder: "Select an Term",
            width: '100%'
        });
        $( "#btn1" ).button();
    });
    
    $("#mycontainer").height(600);
    var nodes = null;
    var edges = null;
    var network = null;
    var EDGE_LENGTH_MAIN = 150;
    var EDGE_LENGTH_SUB = 50;

    // Create a data table with nodes.
    nodes = <joy:ActionValueTag name="NODES" />;
    // Create a data table with links.
    edges = <joy:ActionValueTag name="RELATIONSHIPS" />;
    var data = { nodes: nodes, edges: edges };
    
    // Destroy the chart
    function destroy() {
        if (network !== null) {
            network.destroy();
            network = null;
        }
    }
    
    // First chart drawing
    function draw() {
        destroy();
        // create a network
        var container = document.getElementById('mycontainer');
        var options = { autoResize: true,  
                        height: '100%',
                        physics: { enabled : false, "minVelocity": 0.75 },
                        edges: {
                            smooth: {
                                type: 'cubicBezier',
                                forceDirection: 'horizontal',
                                roundness: 0.4
                            }
                        },
                        layout: {
                            hierarchical: {
                                enabled : (document.getElementById("hierarchy").value == "none"  ? false : true ),
                                direction: (document.getElementById("hierarchy").value == "none"  ? "UD" : document.getElementById("hierarchy").value ),
                                sortMethod: "directed"
                            }
                        }
                      };
        
        network = new vis.Network(container, data, options);
        //network.setOptions({physics:{stabilization:{fit: false}}});
        network.stabilize();
        // Click on node
        network.on("doubleClick", function (params) {
            if (params.nodes != "cidCluster")
                window.open('<joy:JoyBasicURL actiontype="display" object="byterm" />&term=' + params.nodes, '_self'); 
        });
    }
    
    // Regroup by term type
    function clusterByTermType() {
        network.setData(data);
        _termtype = document.getElementById("termtypes").value;
        var clusterOptionsByData = {
            joinCondition:function(childOptions) {
                return childOptions.termtype == _termtype;
            },
            clusterNodeProperties: {id:'cidCluster', borderWidth:3, shape:'database', label:' All ' + _termtype + ' '}
        };
        network.cluster(clusterOptionsByData);
    }

    // Effective draw
    draw();
    
    // Events
    $("#panelnetwork").on('onFullScreen.lobiPanel', function(ev, lobiPanel){
        document.getElementById("mycontainer").style.height = "100%";
        network.redraw();
    });
    $("#containerplus").resize(function() {
        alert("ok");
    });
    $('#hierarchy').on('change', function (evt) {
        draw();
    });
    $('#termtypes').select2({
        placeholder: "Select an Term Type"
    });
    $('#hierarchy').select2({
        placeholder: "Select an Term Hierarchy type"
    });
    
</SCRIPT>
</body>
</html>
                                
                                
