<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html ng-app>
<head>
    <meta charset="utf-8"/>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/>

    <meta name="viewport" content="width=device-width,user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>

    <title><decorator:title default=""/> - Spring Security&Oauth2 Client</title>

    <link href="${contextPath}/resources/bootstrap/bootstrap.min.css" rel="stylesheet"/>
    <script src="${contextPath}/resources/angular.min.js"></script>

    <decorator:head/>

</head>
<body>
<div class="container">
    <div>
        <decorator:body/>
    </div>

    <%--footer--%>
    <div class="row">
        <div class="col-md-12">
            <hr/>
            <p class="text-center text-muted">
                &copy; 2013 - 2015
                 <a
                    href="http://open.duolabao.com" target="_blank">哆啦宝开放平台</a>
            </p>
        </div>
    </div>
</div>
</body>
</html>