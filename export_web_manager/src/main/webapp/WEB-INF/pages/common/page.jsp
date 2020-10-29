<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>
<div class="pull-left">
    <div class="form-group form-inline">
        总共${pageInfo.pages} 页，共${pageInfo.total} 条数据。
        每页显示
            <select onchange="goPage(1,this.value)">
                <c:forEach var="n" begin="1" end="10">
                    <option value="${n}" ${pageInfo.pageSize == n ? 'selected' : ''}>${n}</option>
                </c:forEach>
            </select>
        条
    </div>
</div>

<div class="box-tools pull-right">
    <ul class="pagination" style="margin: 0px;">
        <li >
            <a href="javascript:goPage(1,${pageInfo.pageSize})" aria-label="Previous">首页</a>
        </li>
        <li><a href="javascript:goPage(${pageInfo.prePage},${pageInfo.pageSize})">上一页</a></li>


        <c:forEach
                begin="${pageInfo.pageNum-5>0 ? pageInfo.pageNum-5 : 1}"
                end="${pageInfo.pageNum+5 < pageInfo.pages ? pageInfo.pageNum+5 : pageInfo.pages}"
                var="i">
            <li class="paginate_button ${pageInfo.pageNum == i ? 'active' : ''} ">
                <a href="javascript:goPage(${i},${pageInfo.pageSize})">${i}</a>
            </li>
        </c:forEach>


        <li><a href="javascript:goPage(${pageInfo.nextPage},${pageInfo.pageSize})">下一页</a></li>
        <li>
            <a href="javascript:goPage('${pageInfo.pages}','${pageInfo.pageSize}')" aria-label="Next">尾页</a>
        </li>
    </ul>
</div>
<form id="pageForm" action="${param.pageUrl}" method="post">
    <%--修改隐藏域，提交的分页参数是：pageNum--%>
    <input type="hidden" name="pageNum" id="pageNum">
    <input type="hidden" name="pageSize" id="pageSize">
</form>
<script>
    // 参数：当前页
    function goPage(pageNum,pageSize) {
        $("#pageNum").val(pageNum);
        $("#pageSize").val(pageSize);
        $("#pageForm").submit();
    }
</script>
</body>
</html>
