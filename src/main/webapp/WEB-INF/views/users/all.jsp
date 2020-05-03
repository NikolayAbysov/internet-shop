<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All users</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/static/header.jsp"/>
<table class="table table-borderless">
    <h1>All users</h1>
    <thead>
    <tr>
        <th scope="col">Id</th>
        <th scope="col">Login</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>
                <c:out value="${user.id}"/>
            </td>
            <td>
                <c:out value="${user.login}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/users/delete?id=${user.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<jsp:include page="/WEB-INF/views/static/footer.jsp"/>
</body>
</html>
