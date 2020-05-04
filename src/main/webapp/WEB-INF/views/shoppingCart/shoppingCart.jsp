<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping cart</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/static/header.jsp"/>
<div class="pl-5">
    <table class="table table-borderless, w-50 ml-1">
        <h1>${user.login} shopping cart:</h1>
        <thead>
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Price</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
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
        </tbody>
    </table>
    <a class="button" href="${pageContext.request.contextPath}/order/new?userId=${user.id}"><button class="btn btn-info btn-md">Order</button></a>
</div>
<jsp:include page="/WEB-INF/views/static/footer.jsp"/>
</body>
</html>
