<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All orders</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/static/header.jsp"/>
<table class="table table-borderless">
    <h1>All orders</h1>
    <thead>
    <tr>
        <th scope="col">Id</th>
        <th scope="col">Price</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="order" items="${userOrders}">
        <tr>
            <td>
                <c:out value="${order.id}"/>
            </td>
            <td>
                <c:out value="${order.totalPrice}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/order?orderId=${order.id}">Details</a>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/order/delete?orderId=${order.id}">Remove</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<jsp:include page="/WEB-INF/views/static/footer.jsp"/>
</body>
</html>
