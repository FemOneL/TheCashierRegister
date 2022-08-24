<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>check</title>
    <jsp:useBean id="newCheck" scope="session" type="com.epam.cashierregister.services.entities.check.Check"/>
</head>
<body>
<h1>Check №${newCheck.id}</h1>
<p>${newCheck.time}</p>
<p>Cashier: ${newCheck.employee.firstname} ${newCheck.employee.secondname}</p>
<p>---------------------------</p>
<p>GOODS:</p>
<c:forEach var="goods" items="${newCheck.goodsSet}">
    <p>${goods.producer.name} ${goods.model} x${goods.numbers} - ${goods.totalCost} $</p>
</c:forEach>
<p>---------------------------</p>
<p>TOTAL: ${newCheck.totalCost} $</p>
<form action="createCheck">
    <input type="submit" value="exit">
</form>
</body>
</html>
