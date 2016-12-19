<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Home</title>
    <script language="javascript">
       function init(){
        var customerNum= '10001199455944954105461';
        		var exp = new Date();
            	exp.setTime(exp.getTime() + 90*24*60*60 * 1000);//过期时间 2分钟
        		document.cookie="CustomerNumaaa="+customerNum+";path=/;expires=" + exp.toGMTString();
        		console.log('111');
       }

       window.onload = init();

    </script>
</head>
<body  >
<h2> <a style="text-decoration: none;" href="http://172.19.24.178:8090/main?page=123">点我测试</a></h2>



</body>


</html>