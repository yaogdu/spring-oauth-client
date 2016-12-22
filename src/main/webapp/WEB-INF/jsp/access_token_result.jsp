<%--
 * 
 * @author Shengzhao Li
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>authorization_code</title>
</head>
<body>
<a href="${contextPath}/">Home</a>

<h2>authorization_code
    <small>用 'access_token' 去访问 openrealm 的API</small>
</h2>
<br/>


<div class="panel panel-default">
    <div class="panel-heading">步骤3: <code>用 'access_token' 去访问 openrealm 的API</code></div>
    <div class="panel-body">
        <dl class="dl-horizontal">
            <dt>accessToken</dt>
            <dd><code>${accessTokenDto.accessToken}</code></dd>
            <dt>tokenType</dt>
            <dd><code>${accessTokenDto.tokenType}</code></dd>
            <dt>refreshToken</dt>
            <dd><code>${accessTokenDto.refreshToken}</code></dd>
            <dt>scope</dt>
            <dd><code>${accessTokenDto.scope}</code></dd>
            <dt>customerOpenId</dt>
            <dd><code>${accessTokenDto.customerOpenId}</code></dd>
            <dt>expiresIn</dt>
            <dd><code>${accessTokenDto.expiresIn}</code></dd>
        </dl>
        <hr/>
        <p>
            获取access_token成功, 现在可以访问server资源了, 以下提供两种方式去访问server资源(或API).
        </p>
        <ul>
            <li>
                方式1: 调用本地的接口,由后台去向服务器获取资源并进行处理(如将JSON数据转化成对象), 通过页面展示信息
                <br/>
                <a href="${contextPath}/to_pay?access_token=${accessTokenDto.accessToken}">Oauth Server
                    创建支付链接</a>

            </li>
            <li>

                                <br/>
                                <a href="${contextPath}/customerinfo?access_token=${accessTokenDto.accessToken}">Oauth Server
                                                    获取商户信息</a>
                        </li>
            <!--<li>
                方式2: 直接通过access_token去访问服务器的资源(该方式将直接获取JSON数据)
                <br/>
                <a href="${unityUserInfoUri}?access_token=${accessTokenDto.accessToken}" target="_blank">Oauth Server
                    用户信息
                    [JSON]</a>
            </li>
            <li>...</li>-->
        </ul>
        <p class="help-block">
            <em class="glyphicon glyphicon-info-sign"></em> 至于使用哪一种方式, 在实际中请根据具体的需求或服务器资源提供的访问方式去选择
        </p>
    </div>
</div>
</body>
</html>