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
    <script src="${ctx}/plugins/jQuery/jquery-2.2.3.min.js"></script>
</head>
<script>
    function deleteById() {
        var id = getCheckId()
        if(id) {
            if(confirm("你确认要删除此条记录吗？")) {
                location.href="${ctx}/cargo/contract/delete.do?id="+id;
            }
        }else{
            alert("请勾选待处理的记录，且每次只能勾选一个")
        }
    }

/*    function submit() {
        var id = getCheckId()
        if(id) {
            location.href="${ctx}/cargo/contract/submit.do?id="+id;
        }else{
            alert("请勾选待处理的记录，且每次只能勾选一个")
        }
    }

    function cancel() {
        var id = getCheckId()
        if(id) {
            location.href="${ctx}/cargo/contract/cancel.do?id="+id;
        }else{
            alert("请勾选待处理的记录，且每次只能勾选一个")
        }
    }*/

    function view() {
        var shippingOrderId = getCheckId()
        if(shippingOrderId) {
            location.href="${ctx}/cargo/contract/toView.do?shippingOrderId="+shippingOrderId;
        }else{
            alert("请勾选待处理的记录，且每次只能勾选一个")
        }
    }


</script>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
<section class="content-header">
    <h1>
        货运管理
        <small>购销合同</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
    </ol>
</section>
<!-- 内容头部 /-->

<!-- 正文区域 -->
<section class="content">

    <!-- .box-body -->
    <div class="box box-primary">
        <div class="box-header with-border">
            <h3 class="box-title">购销合同列表</h3>
        </div>

        <div class="box-body">

            <!-- 数据表格 -->
            <div class="table-box">

                <!--工具栏-->
                <div class="pull-left">
                    <div class="form-group form-inline">
                        <div class="btn-group">
                            <button type="button" class="btn btn-default" title="查看" onclick='view()'><i class="fa  fa-eye-slash"></i> 查看</button>
                            <button type="button" class="btn btn-default" title="删除" onclick='deleteById()'><i class="fa fa-trash-o"></i> 删除</button>
                            <button type="button" class="btn btn-default" title="刷新" onclick="window.location.reload();"><i class="fa fa-refresh"></i> 刷新</button>
                            <button type="button" class="btn btn-default" title="导出" onclick="window.location.reload();"><i class="fa fa-refresh"></i> 导出Excel/PDF</button>
                        </div>
                    </div>
                </div>
                <div class="box-tools pull-right">
                    <div class="has-feedback">
                        <input type="text" class="form-control input-sm" placeholder="搜索">
                        <span class="glyphicon glyphicon-search form-control-feedback"></span>
                    </div>
                </div>
                <!--工具栏/-->

                <!--数据列表-->
                <table id="dataList" class="table table-bordered table-striped table-hover dataTable">
                    <thead>
                    <tr>
                        <th class="" style="padding-right:0px;">

                        </th>
                        <th class="sorting">编号</th>
                        <th class="sorting">运输方式</th>
                        <th class="sorting">货主</th>
                        <th class="sorting">提单抬头</th>
                        <th class="sorting">正本通知人</th>
                        <th class="sorting">信用证</th>
                        <th class="sorting">装运港</th>
                        <th class="sorting">转运港</th>
                        <th class="sorting">卸货港</th>
                        <th class="sorting">是否分批</th>
                        <th class="sorting">是否转运</th>
                        <th class="sorting">扼要说明</th>
                        <th class="sorting">状态</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.list}" var="o" varStatus="status">
                        <tr>
                            <td><input type="checkbox" name="id" value="${o.shippingOrderId}"/></td>
                            <td>${o.orderType}</td>
                            <td>${o.shipper}</td>
                            <td>${o.consignee}</td>
                            <td>${o.notifyParty}</td>
                            <td>${o.lcNo}</td>
                            <td>${o.portOfLoading}</td>
                            <td>${o.portOfTrans}</td>
                            <td>${o.portOfDischar}</td>
                            <td>
                                <c:if test="${o.isBatch==0}">否</c:if>
                                <c:if test="${o.isBatch==1}"><font color="green">是</font></c:if>
                            </td>
                            <td>
                                <c:if test="${o.isTrans==0}">否</c:if>
                                <c:if test="${o.isTrans==1}"><font color="green">是</font></c:if>
                            </td>

                            <td>${o.remark}</td>

                            <td>
                                <c:if test="${o.state==0}">草稿</c:if>
                                <c:if test="${o.state==1}"><font color="green">已开票</font></c:if>
                            </td>


                         <%--   <td><a href="${ctx}/cargo/contract/toView.do?id=${o.id}">${o.contractNo}</a></td>
                            <td>${o.proNum}</td>
                            <td>${o.inputBy}</td>
                            <td>${o.inspector}</td>
                            <td><fmt:formatDate value="${o.deliveryPeriod}" pattern="yyyy-MM-dd"/></td>
                            <td><fmt:formatDate value="${o.shipTime}" pattern="yyyy-MM-dd"/></td>
                            <td>${o.tradeTerms}</td>
                            <td>${o.totalAmount}</td>
                            <td><c:if test="${o.state==0}">草稿</c:if>
                                <c:if test="${o.state==1}"><font color="green">已上报</font></c:if>
                                <c:if test="${o.state==2}"><font color="red">已报运</font></c:if>
                            </td>
                            <td>
                                &lt;%&ndash;<a href="${ctx }/cargo/contract/toView.do?id=${o.id}">[查看详情]</a>&ndash;%&gt;
                                <a href="${ctx }/cargo/contract/toUpdate.do?id=${o.id}">[编辑]</a>
                                <a href="${ctx }/cargo/contractProduct/list.do?contractId=${o.id}">[货物]</a>
                                <a href="${ctx }/cargo/contractProduct/toImport.do?contractId=${o.id}">[上传货物]</a>
                            </td>--%>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <!--
                <tfoot>
                <tr>
                <th>Rendering engine</th>
                <th>Browser</th>
                <th>Platform(s)</th>
                <th>Engine version</th>
                <th>CSS grade</th>
                </tr>
                </tfoot>-->
                </table>
                <!--数据列表/-->

                <!--工具栏/-->

            </div>
            <!-- 数据表格 /-->


        </div>
        <!-- /.box-body -->

        <!-- .box-footer-->
        <div class="box-footer">
            <jsp:include page="../../common/page.jsp">
                <jsp:param value="/cargo/contract/list.do" name="pageUrl"/>
            </jsp:include>
        </div>
        <!-- /.box-footer-->


    </div>

</section>
</div>
</body>

</html>