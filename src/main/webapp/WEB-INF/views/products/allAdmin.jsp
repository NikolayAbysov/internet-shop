<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All products</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/static/header.jsp"/>
<table class="table table-borderless">
    <h1>All products for Admin</h1>
    <thead>
    <tr>
        <th scope="col">Id</th>
        <th scope="col">Name</th>
        <th scope="col">Price</th>
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
                <a href="${pageContext.request.contextPath}/products?remove=true&id=${product.id}">Remove</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a class="button" href="${pageContext.request.contextPath}/products/add"><button class="btn btn-info btn-md">Add product</button></a>
<jsp:include page="/WEB-INF/views/static/footer.jsp"/>
</body>
</html>
