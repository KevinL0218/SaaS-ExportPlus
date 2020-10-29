<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%--权限校验标签：当前用户有日志管理的权限，才可以显示标签内容--%>
    <shiro:hasPermission name="日志管理">
        <a href="#">日志管理</a>
    </shiro:hasPermission>

    <shiro:hasPermission name="用户管理">
        <a href="#">用户管理</a>
    </shiro:hasPermission>

    <shiro:hasPermission name="角色管理">
        <a href="#">角色管理</a>
    </shiro:hasPermission>
</body>
</html>
