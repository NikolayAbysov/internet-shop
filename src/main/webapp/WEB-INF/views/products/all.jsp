<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All products</title>
</head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<body>
<jsp:include page="/WEB-INF/views/static/header.jsp"/>
<div class="pl-5">
    <table class="table table-borderless, w-50 ml-1">
        <h3>All products</h3>
        <thead>
        <tr>
            <th scope="col">Id</th>
            <th scope="col">Name</th>
            <th scope="col">Price</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <c:out value="${product.id}"/>
                </td>
                <td>
                    <c:out value="${product.name}"/>
                </td>
                <td>
                    <c:out value="${product.price}"/>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/shop?id=${product.id}">Add to cart</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<jsp:include page="/WEB-INF/views/static/footer.jsp"/>
</body>
</html>
