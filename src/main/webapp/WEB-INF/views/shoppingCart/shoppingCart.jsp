<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping cart</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/static/header.jsp"/>
<div class="container">
    <h1>${user.login} shopping cart:</h1>
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Price</th>
        </tr>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <c:out value="${product.name}"/>
                </td>
                <td>
                    <c:out value="${product.price}"/>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/shop/cart/delete?id=${product.id}&userId=${user.id}">Remove</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <a class="button" href="${pageContext.request.contextPath}/order?userId=${user.id}"><button>Order</button></a>
    <br>
    <a href="${pageContext.request.contextPath}/">Go to the main page.</a>
</div>
<jsp:include page="/WEB-INF/views/static/footer.jsp"/>
</body>
</html>
