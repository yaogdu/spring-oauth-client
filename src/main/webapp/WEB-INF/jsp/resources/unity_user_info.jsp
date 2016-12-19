<%--
 * 
 * @author Shengzhao Li
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>API测试</title>
</head>
<body>
<a href="${contextPath}/">Home</a>

<h2>
    <small>数据来源于 'server' 中提供的API接口</small>
</h2>

<dl class="dl-horizontal">
    <dt>result</dt>
    <dd><code>${result}</code></dd>


</dl>

<a href="javascript:history.back();" class="btn btn-default">Back</a>
</body>
</html>