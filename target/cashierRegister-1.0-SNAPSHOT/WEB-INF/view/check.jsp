<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>check</title>
    <style>
        <%@include file="../../css/main.css" %>
    </style>
    <style>
        <%@include file="../../css/check.css" %>
    </style>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Oswald&display=swap');
    </style>
    <jsp:useBean id="newCheck" scope="session" type="com.epam.cashierregister.services.entities.check.Check"/>
</head>
<body>
<section class="check_section">
    <hr>
    <div class="check">
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
    </div>
    <hr>
    <form action="createCheck">
        <input class="submit_btn" type="submit" value="exit">
    </form>
</section>
</body>
</html>
