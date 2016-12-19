<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>refresh_token</title>
</head>
<body>
<a href="${contextPath}/">Home</a>

<h2>refresh_token</h2>

<div>
    grant_type = 'refresh_token' 模式用在当获取的access_token未过期之前向服务端换取新的access_token.
    <br/>
    在获取access_token时返回的数据如下
    <pre>
{"accessToken":"3420d0e0-ed77-45e1-8370-2b55af0a62e8","tokenType":"bearer","refreshToken":"b36f4978-a172-4aa8-af89-60f58abe3ba1","expiresIn":43199,"scope":"read"}
    </pre>
    <p>
        数据中的<code>expiresIn</code>即accessToken的有效时间(单位:秒), 默认的有效时间为2592000(30天), 在服务端可配置默认的有效时间.
    </p>


    <small class="text-muted">
        <em class="glyphicon glyphicon-info-sign"></em> 在实际应用中, refresh_token一般都是由后台来完成的,前台没有任何表现.
    </small>
</div>

<hr/>
<div ng-controller="RefreshTokenCtrl">

    <div class="panel panel-default">
        <div class="panel-heading"><em>调用 grant_type='refresh_token' 去换取新的access_token</em></div>
        <div class="panel-body">
            <div ng-show="tokenVisible" class="col-md-11">

                <form class="form-horizontal" action="#" onsubmit="return false;">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">refreshTokenUrl</label>

                        <div class="col-sm-10">
                            <p class="form-control-static"><code>${accessTokenUri}</code>
                                &nbsp;<a href="${accessTokenUri}" target="_blank">测试连接</a></p>
                        </div>
                    </div>
                    <a href="javascript:void(0);" ng-click="showTokenParams()">显示请求参数</a>

                    <div ng-show="tokenParamVisible">

                        <div class="form-group">
                            <label class="col-sm-2 control-label">appNum</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="client_id" required="required"
                                       ng-model="clientId"/>

                                <p class="help-block">客户端从 Oauth Server 申请的appNum, 有的Oauth服务器中又叫 appKey</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">appSecretKey</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="client_secret" required="required"
                                       ng-model="clientSecret"/>

                                <p class="help-block">客户端从 Oauth Server 申请的appSecretKey, 有的Oauth服务器中又叫 appSecret</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">grantType</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="grant_type" readonly="readonly"
                                       ng-model="refreshGrantType"/>

                                <p class="help-block">固定值 'refresh_token'</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">refresh_token</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="refresh_token"
                                       ng-model="refreshToken"/>

                                <p class="help-block">从 Oauth Server 返回的 'refresh_token'</p>
                            </div>
                        </div>

                    </div>
                    <br/>
                    <br/>
                    <button class="btn btn-info" ng-click="executeRefreshToken()">刷新access_token</button>
                    <span class="label label-warning">POST</span>
                </form>

                <div ng-show="refreshTokenVisible">
                    <hr/>
                    刷新后的access_token信息
                    <div class="well well-sm">
                        <dl class="dl-horizontal">
                            <dt>access_token</dt>
                            <dd><code>{{newAccessToken}}</code></dd>
                            <dt>token_type</dt>
                            <dd><code>{{newTokenType}}</code></dd>
                            <dt>refresh_token</dt>
                            <dd><code>{{newRefreshToken}}</code></dd>
                            <dt>scope</dt>
                            <dd><code>{{newTokenScope}}</code></dd>
                            <dt>expires_in</dt>
                            <dd><code>{{newExpiresIn}}</code></dd>
                        </dl>
                        <p class="text-danger">{{newTokenError}}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    var RefreshTokenCtrl = ['$scope', '$http', function ($scope, $http) {
        $scope.accessTokenUri = "${accessTokenUri}";
        $scope.clientId = "110";
        $scope.clientSecret = "";
        $scope.grantType = "password";

        $scope.username = "mobile";
        $scope.password = "mobile";
        $scope.scope = "read";

        $scope.refreshGrantType = "refresh_token";

        $scope.visible = true;
        $scope.tokenVisible = true;
        $scope.tokenParamVisible = true;

        $scope.refreshTokenVisible = true;

        $scope.showParams = function () {
            $scope.visible = !$scope.visible;
        };

        $scope.showTokenParams = function () {
            $scope.tokenParamVisible = !$scope.tokenParamVisible;
        };

        $scope.getAccessToken = function () {
            var uri = "password_access_token?clientId=" + $scope.clientId + "&clientSecret=" + $scope.clientSecret + "&grantType=" + $scope.grantType
                    + "&scope=" + $scope.scope + "&username=" + $scope.username + "&password=" + $scope.password
                    + "&accessTokenUri=" + encodeURIComponent($scope.accessTokenUri);

            $http.get(uri).success(function (data) {
                $scope.accessToken = data.accessToken;
                $scope.tokenType = data.tokenType;
                $scope.refreshToken = data.refreshToken;

                $scope.tokenScope = data.scope;
                $scope.expiresIn = data.expiresIn;
                //if have error
                $scope.tokenError = data.error + " " + data.errorDescription;

                $scope.tokenVisible = true;
            });
        };


        $scope.executeRefreshToken = function () {
            var uri = "refresh_access_token?appNum=" + $scope.clientId + "&appSecretKey=" + $scope.clientSecret + "&grantType=" + $scope.refreshGrantType
                    + "&refreshToken=" + $scope.refreshToken + "&refreshAccessTokenUrl=" + encodeURIComponent($scope.accessTokenUri);

            $http.get(uri).success(function (data) {
                $scope.newAccessToken = data.accessToken;
                $scope.newTokenType = data.tokenType;
                $scope.newRefreshToken = data.refreshToken;

                $scope.newTokenScope = data.scope;
                $scope.newExpiresIn = data.expiresIn;
                //if have error
                $scope.newTokenError = data.error + " " + data.errorDescription;

                $scope.refreshTokenVisible = true;
            });
        };
    }];
</script>
</body>
</html>