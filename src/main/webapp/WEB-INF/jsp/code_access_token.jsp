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
    <small>用 'code' 换取 'access_token'</small>
</h2>
<br/>


<div class="panel panel-default">
    <div class="panel-heading"> <code>用 'code' 换取 'access_token'</code></div>
    <div class="panel-body">
        <div ng-controller="CodeAccessTokenCtrl" class="col-md-10">

            <form action="code_access_token" method="post" class="form-horizontal">
                <input type="hidden" name="accessTokenUri" value="${accessTokenDto.accessTokenUri}"/>

                <div class="form-group">
                    <label class="col-sm-2 control-label">accessTokenUrl</label>

                    <div class="col-sm-10">
                        <p class="form-control-static"><code>${accessTokenDto.accessTokenUri}</code>
                            &nbsp;<!--<a href="${accessTokenDto.accessTokenUri}" target="_blank">测试连接</a>-->
                        </p>

                        <p class="help-block">
                            accessTokenUrl value from 'spring-oauth-client.properties'
                        </p>
                    </div>
                </div>

                <a href="javascript:void(0);" ng-click="showParams()">显示请求参数</a>

                <div ng-show="visible">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">grantType</label>

                        <div class="col-sm-10">
                            <input type="text" name="grantType" readonly="readonly"
                                   class="form-control" ng-model="grantType"/>

                            <p class="help-block">固定值 '${accessTokenDto.grantType}'</p>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">appNum</label>

                        <div class="col-sm-10">
                            <input type="text" name="clientId" value="10001114515422820071874" required="required"
                                   class="form-control" ng-model="clientId"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">appSecretKey</label>

                        <div class="col-sm-10">
                            <input type="text" name="clientSecret" value="64db096fae1549c6876c2a025e9621161689a32d" required="required"
                                   class="form-control" ng-model="clientSecret"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">code</label>

                        <div class="col-sm-10">
                            <input type="text" name="code" required="required" class="form-control"
                                   ng-model="code" readonly="readonly"/>

                            <p class="help-block">值是从 'spring-oauth-server' 返回的</p>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">redirectUrl</label>

                        <div class="col-sm-10">
                            <input type="text" name="redirectUri" class="form-control"
                                   required="required" size="50" ng-model="redirectUri"/>

                            <p class="help-block">这一步的 'redirectUrl' 必须与上一步的 'redirectUrl' 一样</p>
                        </div>
                    </div>


                    <div class="well well-sm">
                        <span class="text-muted">最终获取 'access_token'的 URL:</span>
                        <br/>

                        <div class="text-primary">
                            {{accessTokenUri}}?appNum={{clientId}}&appSecretKey={{clientSecret}}&grantType={{grantType}}&redirectUrl={{redirectUri}}&code={{code}}
                        </div>
                    </div>
                </div>
                <br/>
                <br/>
                <button type="submit" class="btn btn-primary">获取 access_token</button>
                <span class="text-muted">后台将通过 HttpClient 去获取 access_token</span> <span
                    class="label label-warning">POST</span>
                <br/>
                <small class="text-muted">
                    <em class="glyphicon glyphicon-info-sign"></em> 在实际应用中, 该步骤一般由后台代码完成,前端不需要表现.
                </small>
            </form>

        </div>
    </div>
</div>

<script>
    var CodeAccessTokenCtrl = ['$scope', function ($scope) {
        $scope.accessTokenUri = '${accessTokenDto.accessTokenUri}';
        $scope.grantType = '${accessTokenDto.grantType}';
        $scope.clientId = '10101114787739180621034';

        $scope.clientSecret = '64db096fae1549c6876c2a025e9621161689a32d';
        $scope.code = '${accessTokenDto.code}';
        $scope.redirectUri = '${host}callback';

        $scope.visible = false;

        $scope.showParams = function () {
            $scope.visible = !$scope.visible;
        };
    }];
</script>

</body>
</html>