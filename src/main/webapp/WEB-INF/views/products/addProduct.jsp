<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/static/header.jsp"/>
<div class="container">
    <h1>Add product</h1>
    <h1 style="color: red">${message}</h1>
    <br>
    <form method="post" action="${pageContext.request.contextPath}/products/add">
        Product name <input type="text" name="productName" placeholder="Enter product name" required>
        <br>
        Product price <input type="text" name="productPrice" placeholder="Enter product price" required>
        <br>
        <button type="submit">Add</button>
    </form>
    <br>
    <a href="${pageContext.request.contextPath}/">Go to the main page.</a>
</div>
<jsp:include page="/WEB-INF/views/static/footer.jsp"/>
</body>
</html>
