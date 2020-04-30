<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/static/header.jsp"/>
<div class="container">
    <h4 style="color: red">${message}</h4>
    <h1>Hello, please register!</h1>
    <form method="post" action="${pageContext.request.contextPath}/registration">
        Enter your login: <input type="text" name="login">
        <br>
        Enter your password: <input type="password" name="password">
        <br>
        Please, repeat your password: <input type="password" name="password-repeat">
        <br>
        <button type="submit">Register</button>
        <br>
        <a href="${pageContext.request.contextPath}/">Go to the main page.</a>
    </form>
</div>
<jsp:include page="/WEB-INF/views/static/footer.jsp"/>
</body>
</html>
