<%--
  Created by IntelliJ IDEA.
  User: proko
  Date: 3/1/2018
  Time: 17:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Authentication</title>
</head>
<body>
${message}
<br>
<form method="post" action="${pageContext.request.contextPath}/login">
    <input type="text" title="login" name="login">
    <input type="password" title="password" name="password">
    <input type="submit" value="Login">
</form>
<br>
<form method="post" action="${pageContext.request.contextPath}/register">
    <input type="text" title="login" name="login">
    <input type="password" title="password" name="password">
    <input type="submit" value="Register">
</form>
</body>
</html>
