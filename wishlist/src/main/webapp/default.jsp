<%--@elvariable id="login" type="java.lang.String"--%>
<%--@elvariable id="randomNumber" type="java.lang.Integer"--%>
<%--
  Created by IntelliJ IDEA.
  User: Monopolis
  Date: 2/28/2018
  Time: 20:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello</title>
</head>
<body>
Hello ${login}
Your random number - ${randomNumber}
<a href="${pageContext.request.contextPath}/authentication.jsp">Login</a>
<a href="${pageContext.request.contextPath}/registration.jsp">Register</a>
</body>
</html>
