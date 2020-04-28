<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h1>Hello, World! Current time is: ${time}</h1>
<br>
<a href="${pageContext.request.contextPath}/users/all">All users</a>
<br>
<a href="${pageContext.request.contextPath}/injectData">Inject test data</a>
<br>
<a href="${pageContext.request.contextPath}/registration">Registration</a>
<br>
<a href="${pageContext.request.contextPath}/products/all">All products</a>
<br>
<a href="${pageContext.request.contextPath}/products/add">Add product</a>
<br>
<a href="${pageContext.request.contextPath}/shop/cart">View shopping cart</a>
</body>
</html>
