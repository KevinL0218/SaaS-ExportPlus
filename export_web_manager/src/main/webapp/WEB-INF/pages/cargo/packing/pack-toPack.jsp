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
</head>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <!-- 内容头部 -->
    <section class="content-header">
        <h1>
            货运管理
            <small>新增装箱单</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="all-order-manage-list.html">货运管理</a></li>
            <li class="active">新增装箱单</li>
        </ol>
    </section>
    <!-- 正文区域 -->
    <section class="content">
        <form action="${ctx}/cargo/packing/toAdd.do" method="post">
            <input type="hidden" name="exportIds" value="${packing.exportIds}">
        <div class="panel panel-default">
            <div class="panel-heading">对【${packing.exportNos}】进行装箱</div>
            <div class="row data-type" style="margin: 0px">
                <div class="col-md-2 title">报运合同号</div>
                <div class="col-md-4 data">
                    <input type="text" class="form-control" placeholder="报运合同号" name="exportNos" readonly="readonly" value="${packing.exportNos}"/>
                </div>

                <div class="col-md-2 title">装箱时间</div>
                <div class="col-md-4 data">
                    <div class="input-group date">
                        <div class="input-group-addon">
                            <i class="fa fa-calendar"></i>
                        </div>
                        <input type="text" placeholder="装箱时间"  name="packingTime" class="form-control pull-right"
                               value="<fmt:formatDate value="${packing.packingTime}" pattern="yyyy-MM-dd"/>" id="packingTime">
                    </div>
                </div>

                <div class="col-md-2 title">总体积</div>
                <div class="col-md-4 data">
                    <input type="text" readonly="readonly" name="totalVolume" class="form-control" placeholder="总体积" value="${packing.totalVolume}"/>
                </div>

                <div class="col-md-2 title">总净重</div>
                <div class="col-md-4 data">
                    <input type="text" readonly="readonly" name="netWeights" class="form-control" placeholder="总净重" value="${packing.netWeights}"/>
                </div>

                <div class="col-md-2 title">总毛重</div>
                <div class="col-md-4 data">
                    <input type="text" readonly="readonly" name="grossWeights" class="form-control" placeholder="总毛重" value="${packing.grossWeights}">
                </div>

                <div class="col-md-2 title">唛头</div>
                <div class="col-md-4 data">
                    <input type="text" class="form-control" placeholder="唛头" name="marks" value="${packing.marks}"/>
                </div>

                <div class="col-md-2 title">描述</div>
                <div class="col-md-4 data">
                    <input type="text" name="description" class="form-control" placeholder="描述" value="${packing.description}"/>
                </div>

                <div class="col-md-2 title">装箱费用</div>
                <div class="col-md-4 data">
                    <input type="text" readonly="readonly" name="packingMoney" class="form-control" placeholder="装箱费用" value="${packing.packingMoney}"/>
                </div>
            </div>
        </div>
        <!--工具栏-->
        <div class="box-tools text-center">
            <button type="submit"  class="btn bg-maroon">保存</button>
            <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
        </div>
        </form>
        <!--工具栏/-->
    </section>
</div>
<!-- 内容区域 /-->
</body>
<script src="../../plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="../../plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<script>
    $('#packingTime').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
</script>
</html>