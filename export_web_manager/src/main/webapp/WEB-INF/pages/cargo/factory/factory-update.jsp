<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../base.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>数据 - AdminLTE2定制版</title>
</head>
<body>
    <div id="frameContent" class="content-wrapper" style="margin-left:0px;">
        <section class="content-header">
            <h1>
                系统管理
                <small>工厂管理</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            </ol>
        </section>
        <section class="content">
            <div class="box-body">
                <div class="nav-tabs-custom">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="#tab-form" data-toggle="tab">编辑工厂</a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <form id="editForm" action="/cargo/factory/edit" method="post">
                            <input type="hidden" name="id" value="${factory.id}">
                            <div class="tab-pane active" id="tab-form">
                                <div class="row data-type">
                                    <div class="col-md-2 title">厂家名称</div>
                                    <div class="col-md-4 data">
                                        <input type="text" class="form-control" placeholder="厂家名称" name="factoryName" value="${factory.factoryName}">
                                    </div>
                                    <div class="col-md-2 title">生产类型</div>
                                    <div class="col-md-4 data">
                                        <select class="form-control" name="ctype" value="${factory.ctype}">
                                            <option value="">请选择</option>
                                            <option ${factory.ctype == '货物' ?'selected':''} value="货物">货物</option>
                                            <option ${factory.ctype == '附件' ?'selected':''} value="附件">附件</option>
                                        </select>
                                    </div>
                                    <div class="col-md-2 title">厂家全名</div>
                                    <div class="col-md-4 data">
                                        <input type="text" class="form-control" placeholder="厂家全名" name="fullName" value="${factory.fullName}">
                                    </div>
                                    <div class="col-md-2 title">工厂联系人</div>
                                    <div class="col-md-4 data">
                                        <input type="text" class="form-control" placeholder="工厂联系人" name="contacts" value="${factory.contacts}">
                                    </div>
                                    <div class="col-md-2 title">工厂电话</div>
                                    <div class="col-md-4 data">
                                        <input type="text" class="form-control" placeholder="工厂电话" name="phone" value="${factory.phone}">
                                    </div>
                                    <div class="col-md-2 title">联系人电话</div>
                                    <div class="col-md-4 data">
                                        <input type="text" class="form-control" placeholder="联系人电话" name="mobile" value="${factory.mobile}">
                                    </div>
                                    <div class="col-md-2 title">传真</div>
                                    <div class="col-md-4 data">
                                        <input type="text" class="form-control" placeholder="传真" name="fax" value="${factory.fax}">
                                    </div>
                                    <div class="col-md-2 title">工厂地址</div>
                                    <div class="col-md-4 data">
                                        <input type="text" class="form-control" placeholder="工厂地址" name="address" value="${factory.address}">
                                    </div>
                                    <div class="col-md-2 title">工厂检察员</div>
                                    <div class="col-md-4 data">
                                        <input type="text" class="form-control" placeholder="工厂检察员" name="inspector" value="${factory.inspector}">
                                    </div>
                                    <div class="col-md-2 title">订单编号</div>
                                    <div class="col-md-4 data">
                                        <input type="text" class="form-control" placeholder="订单编号" name="orderNo" value="${factory.orderNo}">
                                    </div>
                                    <div class="col-md-2 title">状态</div>
                                    <div class="col-md-4 data">
                                        <div class="form-group form-inline">
                                            <div class="radio"><label><input type="radio" ${factory.state==0?'checked':''} name="state" value="0">停用</label></div>
                                            <div class="radio"><label><input type="radio" ${factory.state==1?'checked':''} name="state" value="1">启用</label></div>
                                        </div>
                                    </div>
                                    <div class="col-md-2 title">备注</div>
                                    <div class="col-md-4 data">
                                        <input type="text" class="form-control" placeholder="备注" name="remark" value="${factory.remark}">
                                    </div>
                                    <div class="col-md-2 title"></div>
                                    <div class="col-md-10 data text-center">
                                        <button type="button" onclick='document.getElementById("editForm").submit()'  class="btn bg-maroon">保存</button>
                                        <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

        </section>
    </div>
</body>

</html>