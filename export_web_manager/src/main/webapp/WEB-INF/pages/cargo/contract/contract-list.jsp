<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../../base.jsp" %>
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
        var id = getCheckId();
        if (id) {
            if (confirm("你确认要删除此条记录吗？")) {
                $.ajax({
                    url: "${ctx}/cargo/contract/delete?id=" + id,
                    dataType: "json",
                    success: function (result) {
                        if (result) {
                            //删除成功
                            window.location.reload();
                        } else {
                            //删除失败
                            alert("您当前选择的合同已经提交，无法删除！")
                        }
                    }
                });
            }
        } else {
            alert("请勾选待处理的记录，且每次只能勾选一个")
        }
    }

    function submit() {
        var id = getCheckId()
        if (id) {
            $.get({
                url: "${ctx}/cargo/contract/submit?id=" + id,
                dataType: "json",
                success: function (result) {
                    //result==0，已提交过了
                    //result==1  状态修改为已提交或者已审核
                    if (result == 0) {
                        location.reload();
                    } else if (result == 1) {
                        alert("已提交过，无需再次提交！")
                    } else if (result == 2) {
                        alert("请耐心等待审单人审核！")
                    }
                }
            });
        } else {
            alert("请勾选待处理的记录，且每次只能勾选一个")
        }
    }

    function cancel() {
        var id = getCheckId();
        if (id) {
            $.ajax({
                url: "${ctx}/cargo/contract/cancel.do?id=" + id,
                dataType: "json",
                success: function (result) {
                    //result==0，不是提交状态，无法提交
                    //result==1  已取消
                    if (result == 0) {
                        alert("当前所选合同状态不是已提交，无法进行取消！")
                    } else {
                        location.reload();
                    }
                }
            });

        } else {
            alert("请勾选待处理的记录，且每次只能勾选一个")
        }
    }


    function view() {
        var id = getCheckId();
        if (id) {
            location.href = "${ctx}/cargo/contract/toView.do?id=" + id;
        } else {
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
                                <button type="button" class="btn btn-default" title="新建"
                                        onclick='location.href="${ctx}/cargo/contract/toAdd.do"'><i
                                        class="fa fa-file-o"></i> 新建
                                </button>
                                <button type="button" class="btn btn-default" title="查看" onclick='view()'><i
                                        class="fa  fa-eye-slash"></i> 查看
                                </button>
                                <button type="button" class="btn btn-default" title="删除" onclick='deleteById()'><i
                                        class="fa fa-trash-o"></i> 删除
                                </button>
                                <button type="button" class="btn btn-default" title="刷新"
                                        onclick="window.location.reload();"><i class="fa fa-refresh"></i> 刷新
                                </button>
                                <button type="button" class="btn btn-default" title="提交" onclick="submit()"><i
                                        class="fa fa-retweet"></i> 提交
                                </button>
                                <button type="button" class="btn btn-default" title="取消" onclick="cancel()"><i
                                        class="fa fa-remove"></i> 取消
                                </button>
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
                            <th class="sorting">客户名称</th>
                            <th class="sorting">合同号</th>
                            <th class="sorting">货物/附件</th>
                            <th class="sorting">制单人</th>
                            <th class="sorting">验货员</th>
                            <th class="sorting">交货期限</th>
                            <th class="sorting">船期</th>
                            <th class="sorting">贸易条款</th>
                            <th class="sorting">总金额</th>
                            <th class="sorting">状态</th>
                            <th class="text-center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageInfo.list}" var="o" varStatus="status">
                            <tr>
                                <td><input type="checkbox" name="id" value="${o.id}"/></td>
                                <td>${o.customName}</td>
                                <td><a href="${ctx}/cargo/contract/toView.do?id=${o.id}">${o.contractNo}</a></td>
                                <td>
                                        ${o.proNum}/${o.extNum}
                                </td>
                                <td>${o.inputBy}</td>
                                <td>${o.inspector}</td>
                                <td><fmt:formatDate value="${o.deliveryPeriod}" pattern="yyyy-MM-dd"/></td>
                                <td><fmt:formatDate value="${o.shipTime}" pattern="yyyy-MM-dd"/></td>
                                <td>${o.tradeTerms}</td>
                                <td>${o.totalAmount}</td>
                                <td>
                                    <c:if test="${o.state==0}">草稿</c:if>
                                    <c:if test="${user.degree==2 || user.degree==3}">
                                        <c:if test="${o.state==7}"><font color="green">待审核</font></c:if>
                                    </c:if>
                                    <c:if test="${user.degree==4}">
                                        <c:if test="${o.state==7}"><font color="green">已提交</font></c:if>
                                    </c:if>
                                    <c:if test="${o.state==1}"><font color="blue">已审核</font></c:if>
                                    <c:if test="${o.state==2}"><font color="red">已报运</font></c:if>
                                    <c:if test="${o.state==3}"><font color="green">已装箱</font></c:if>
                                    <c:if test="${o.state==4}"><font color="green">已委托</font></c:if>
                                    <c:if test="${o.state==5}"><font color="green">已生产发票</font></c:if>
                                    <c:if test="${o.state==6}"><font color="green">已生产财务报运</font></c:if>
                                </td>
                                <td>
                                        <%--<a href="${ctx }/cargo/contract/toView.do?id=${o.id}">[查看详情]</a>--%>
                                    <a href="${ctx }/cargo/contract/toUpdate.do?id=${o.id}">[编辑]</a>
                                    <a href="${ctx }/cargo/contractProduct/list.do?contractId=${o.id}">[货物]</a>
                                    <a href="${ctx }/cargo/contractProduct/toImport.do?contractId=${o.id}">[上传货物]</a>
                                </td>
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