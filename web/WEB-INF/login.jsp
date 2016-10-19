<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dgm" uri="/WEB-INF/dgmTags.tld"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="UI" %>
<%@taglib prefix="joy" uri="/WEB-INF/joyTags.tld"%>

<head>
    <jsp:directive.include file="./templates/head.jsp" />
</head>

<body>
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Please Sign In</h3>
                    </div>
                    <div class="panel-body">
                        <joy:JoyFormTag object='login' actiontype='login' inline="false" name="login"> 
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="User" name="user" type="text" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" name="password" type="password" value="">
                                </div>
                                <!-- Change this to a button or input when using this as a form -->
                                <a href="#" class="btn btn-lg btn-success btn-block" onclick="document.forms['login'].submit();">Login</a>
                            </fieldset>
                        </joy:JoyFormTag>  
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
<jsp:directive.include file="./templates/foot.jsp" />
</html>
