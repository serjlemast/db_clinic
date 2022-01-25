<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login</title>
</head>
<body>
<div>
    <form action="<%= request.getRequestURL() %>" method="post">
        <h1>User name</h1>
        <input id="loginUserName" name="username">
        <h1>Password</h1>
        <input id="loginPassword" name="password" type="password">
        <button type="submit" id="loginButton">Login</button>
    </form>
    <h1 style="background-color: red">
        <%= request.getAttribute("error") %>
    </h1>
    <c:if test="${not empty request.getAttribute('error')}">
        <p style="color: red">${request.getAttribute('error')}</p>
    </c:if>
</div>
</body>
</html>
