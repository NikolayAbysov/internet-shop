<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All products</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/static/header.jsp"/>
<div class="container">
    <table border="1">
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Price</th>
        </tr>
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
    </table>
    <br>
    <a class="button" href="${pageContext.request.contextPath}/products/add"><button>Add product</button></a>
    <br>
    <a href="${pageContext.request.contextPath}/">Go to the main page.</a>
</div>
<jsp:include page="/WEB-INF/views/static/footer.jsp"/>
</body>
</html>
