<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/static/header.jsp"/>
<div class="container">
    <h1>Select</h1>
    <br>
    <a href="${pageContext.request.contextPath}/users/all">All users</a>
    <br>
    <a href="${pageContext.request.contextPath}/products?get=all">All products</a>
    <br>
    <a href="${pageContext.request.contextPath}/products/allAdmin">All products for Admin</a>
    <br>
    <a href="${pageContext.request.contextPath}/shop/cart">View shopping cart</a>
    <br>
    <a href="${pageContext.request.contextPath}/order/all">View orders</a>
</div>
<jsp:include page="/WEB-INF/views/static/footer.jsp"/>
</body>
</html>
