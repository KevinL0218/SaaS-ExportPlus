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
    <!-- 内容头部 -->
    <section class="content-header">
        <h1>
            货运管理
            <small>新增出口报运单</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="all-order-manage-list.html">货运管理</a></li>
            <li class="active">新增出口报运单</li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!--订单信息-->
        <div class="panel panel-default">
            <div class="panel-heading">新增委托书</div>
            <form id="editForm" action="/cargo/shipping/add.do" method="post">
                <input type="hidden" id="id" name="id" value="${ShippingOrder.shippingOrderId}">
                <input type="hidden" name="packingListId" value="${packingListId}">
                <div class="row data-type" style="margin: 0px">
                    <div class="col-md-2 title">运输方式</div>
                    <div class="col-md-4 data">
                        <div class="form-group form-inline">
                            <div class="radio"><label><input type="radio" ${ShippingOrder.orderType=="海运"?'checked':''} name="orderType" value="海运">海运</label></div>
                            <div class="radio"><label><input type="radio" ${ShippingOrder.orderType=="空运"?'checked':''} name="orderType" value="空运">空运</label></div>
                        </div>
                    </div>

                    <div class="col-md-2 title">货主</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="货主" name="shipper" value="${ShippingOrder.shipper}">
                    </div>

                    <div class="col-md-2 title">提单抬头</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control"  placeholder="提单抬头" name="consignee" value="${ShippingOrder.consignee}">
                    </div>

                    <div class="col-md-2 title">正本通知人</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" readonly="readonly"  placeholder="正本通知人" name="notifyParty" value="${export.consignee}">
                    </div>

                    <div class="col-md-2 title">信用证号</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" readonly="readonly"  placeholder="信用证号" name="lcNo" value="${export.lcno}">
                    </div>

                    <div class="col-md-2 title">唛头</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" readonly="readonly"  placeholder="唛头" name="marks" value="${packing.marks}">
                    </div>

                    <div class="col-md-2 title">装运港</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" readonly="readonly"  placeholder="装运港" name="portOfLoading" value="${export.shipmentPort}">
                    </div>

                    <div class="col-md-2 title">转运港</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="转运港" name="portOfTrans" value="${ShippingOrder.portOfTrans}">
                    </div>

                    <div class="col-md-2 title">目的港</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" readonly="readonly"  placeholder="目的港" name="portOfDischarge" value="${export.destinationPort}">
                </div>

                    <div class="col-md-2 title">是否分批</div>
                    <div class="col-md-4 data">
                        <div class="form-group form-inline">
                            <div class="radio"><label><input type="radio" ${ShippingOrder.isBatch==1?'checked':''} name="isBatch" value="1">是</label></div>
                            <div class="radio"><label><input type="radio" ${ShippingOrder.isBatch==0?'checked':''} name="isBatch" value="0">否</label></div>
                        </div>
                    </div>

                    <div class="col-md-2 title">是否转运</div>
                    <div class="col-md-4 data">
                        <div class="form-group form-inline">
                            <div class="radio"><label><input type="radio" ${ShippingOrder.isTrans==1?'checked':''} name="isTrans" value="1">是</label></div>
                            <div class="radio"><label><input type="radio" ${ShippingOrder.isTrans==0?'checked':''} name="isTrans" value="0">否</label></div>
                        </div>
                    </div>

                    <div class="col-md-2 title">扼要说明</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="扼要说明" name="remark" value="${ShippingOrder.remark}">
                    </div>

                </div>
            </form>
        </div>
        <!--订单信息/-->


    </section>
    <!-- 正文区域 /-->

    <!--工具栏-->
    <div class="box-tools text-center">
        <button type="button" onclick='document.getElementById("editForm").submit()' class="btn bg-maroon">保存</button>
        <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
    </div>
    <!--工具栏/-->

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