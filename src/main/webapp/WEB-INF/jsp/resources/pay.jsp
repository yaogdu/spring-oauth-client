<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>支付测试</title>
</head>
<body>
<a href="${contextPath}/">Home</a>
<h2>支付测试</h2>
<br/>


<div class="panel panel-default">
    <div class="panel-heading"></div>
    <div class="panel-body">
        <div ng-controller="payCtrl" class="col-md-10">

            <form action="realPay" method="post" class="form-horizontal">
                <input type="hidden" name="payUrl" value="${payUrl}"/>

                <div class="form-group">
                    <label class="col-sm-2 control-label">payUrl</label>

                    <div class="col-sm-10">
                        <p class="form-control-static"><code>${payUrl}</code>
                            &nbsp;
                        </p>

                        <p class="help-block">
                            payUrl value from 'spring-oauth-client.properties'
                        </p>
                    </div>
                </div>
                <a href="javascript:void(0);" ng-click="showParams()">显示请求参数</a>
                <div ng-show="visible">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">accessToken</label>

                        <div class="col-sm-10">
                            <input type="text" name="accessToken" readonly="readonly"
                                   class="form-control" ng-model="accessToken"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">customerOpenId</label>

                        <div class="col-sm-10">
                            <input type="text" name="customerOpenId"
                                   class="form-control" ng-model="customerOpenId"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">机具号</label>

                        <div class="col-sm-10">
                            <input type="text" name="machineNum"
                                   class="form-control" ng-model="machineNum"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">店铺号</label>

                        <div class="col-sm-10">
                            <input type="text" name="shopNum"
                                   class="form-control" ng-model="shopNum"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">金额</label>

                        <div class="col-sm-10">
                            <input type="text" name="amount"
                                   class="form-control" ng-model="amount" value="0.01"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">桌号</label>

                        <div class="col-sm-10">
                            <input type="text" name="tableNum"
                                   class="form-control" ng-model="tableNum"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">回调</label>

                        <div class="col-sm-10">
                            <input type="text" name="callBackUrl"
                                   class="form-control" ng-model="callBackUrl" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">accessKey</label>

                        <div class="col-sm-10">
                            <input type="text" name="accessKey"
                                   class="form-control" ng-model="accessKey" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">secretKey</label>

                        <div class="col-sm-10">
                            <input type="text" name="secretKey"
                                   class="form-control" ng-model="secretKey" />
                        </div>
                    </div>

                </div>
                <br/>
                <br/>
                <button type="submit" class="btn btn-primary">确认</button>
                <span class="label label-info">POST</span>
            </form>

        </div>
    </div>
</div>

<script>
    var payCtrl = ['$scope', function ($scope) {
        $scope.payUrl = '${payUrl}';
        $scope.accessToken = '${accessToken}';
        $scope.visible = true;
        $scope.amount='0.01';
        $scope.accessKey='35649fdf38be45858ba7ea1de9404e0bd58c9846';
        $scope.secretKey='12f3d4f076944921857ff10def2ef3c974d6c04f';
        $scope.callBackUrl='open.duolabao.cn';
        $scope.tableNum='110';

        $scope.showParams = function () {
            $scope.visible = !$scope.visible;
        };
    }];
</script>

</body>
</html>