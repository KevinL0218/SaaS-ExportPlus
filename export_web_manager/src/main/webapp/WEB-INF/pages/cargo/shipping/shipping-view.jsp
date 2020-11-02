<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../../base.jsp"%>
<!DOCTYPE html>
<html>

<head>
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>数据 - AdminLTE2定制版</title>
    <meta name="description" content="AdminLTE2定制版">
    <meta name="keywords" content="AdminLTE2定制版">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    <!-- 页面meta /-->
</head>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">

    <!--工具栏-->
    <section class="content-header">
        <h1>
            货运管理
            <small>委托管理</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
        </ol>
    </section>
    <!--工具栏/-->
    <!-- 内容头部 -->
    <section class="content">
        <!--订单信息-->
        <div class="panel panel-default">
            <div class="panel-heading">委托管理信息</div>
                <div class="row data-type" style="margin: 0px">

                    <div class="col-md-2 title">运输方式</div>
                    <div class="col-md-4 data" style="line-height:34px">
                        ${shippingOrder.orderType}
                    </div>

                    <div class="col-md-2 title">货主</div>
                    <div class="col-md-4 data" style="line-height:34px">
                        ${shippingOrder.shipper}
                    </div>

                    <div class="col-md-2 title">提单抬头</div>
                    <div class="col-md-4 data" style="line-height:34px">
                        ${shippingOrder.consignee}
                    </div>

                    <div class="col-md-2 title">正本通知人</div>
                    <div class="col-md-4 data" style="line-height:34px">
                        ${shippingOrder.notifyParty}
                    </div>

                    <div class="col-md-2 title">信用证号</div>
                    <div class="col-md-4 data" style="line-height:34px">
                        ${export.lcno}
                    </div>

                    <div class="col-md-2 title">唛头</div>
                    <div class="col-md-4 data" style="line-height:34px">
                        ${packing.marks}
                    </div>

                    <div class="col-md-2 title">装运港</div>
                    <div class="col-md-4 data" style="line-height:34px">
                        ${export.shipmentPort}
                    </div>

                    <div class="col-md-2 title">转运港</div>
                    <div class="col-md-4 data" style="line-height:34px">
                        ${shippingOrder.portOfTrans}
                    </div>

                    <div class="col-md-2 title">卸货港</div>
                    <div class="col-md-4 data" style="line-height:34px">
                        ${export.destinationPort}
                    </div>

                    <div class="col-md-2 title">是否分批</div>
                    <div class="col-md-4 data" style="line-height:34px">
                        ${shippingOrder.isBatch==1?'是':'否'}
                    </div>


                    <div class="col-md-2 title">是否转运</div>
                    <div class="col-md-4 data" style="line-height:34px">
                        ${shippingOrder.isTrans==1?'是':'否'}
                    </div>


                    <div class="col-md-2 title">扼要说明</div>
                    <div class="col-md-4 data" style="line-height:34px">
                        ${shippingOrder.remark}
                    </div>

                </div>
        </div>
        <!--订单信息/-->


    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <div class="box-tools text-center">
        <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
    </div>
    <!-- 正文区域 /-->

</div>
<!-- 内容区域 /-->
</body>
<script src="../../plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="../../plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<link rel="stylesheet" href="../../css/style.css">
<script>
    $('#datepicker').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
    $('#datepicker1').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
</script>
</html>