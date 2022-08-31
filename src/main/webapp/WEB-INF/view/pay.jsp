<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>payment</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Oswald&display=swap');
    </style>
    <style>
        <%@include file="../../css/main.css" %>
    </style>
    <jsp:useBean id="type" scope="session" type="java.lang.String"/>
    <jsp:useBean id="remainder" scope="session" type="java.math.BigDecimal"/>
    <jsp:useBean id="error" scope="request" type="java.lang.String"/>
    <jsp:useBean id="activeCheck" scope="session" type="com.epam.cashierregister.services.entities.check.Check"/>
    <jsp:useBean id="sum" scope="session" type="java.math.BigDecimal"/>
</head>
<body>
<c:import url="elements/header.jsp"/>
<div class="pay_type">
    <form method="post" action="frontController">
        <input type="hidden" name="cmd" value="ChoosePaymentType">
        <button type="submit" value="cash" name="type">CASH</button>
        <button type="submit" value="card" name="type">CARD</button>
    </form>
</div>
<c:if test="${type == 'card'}">
    <h1>wait</h1>
    <img src="images/goodsPhotos/cat.jpg" width="200px" height="200px">
    <form method="post" action="frontController">
        <input type="hidden" name="cmd" value="CloseCheck">
        <input type="submit" value="print">
    </form>
</c:if>
<c:if test="${type == 'cash'}">
    <h1>enter</h1>
    <form method="post" action="frontController">
        <input type="hidden" name="cmd" value="Reminder">
        <input name="count" type="text">
        <input type="submit" value="ok">
    </form>
    <p>total: ${activeCheck.totalCost}</p>
    <p class="error">${error}</p>
    <p>remaining: ${remainder} $</p>
    <c:if test="${sum.doubleValue() >= activeCheck.totalCost}">
    <form method="post" action="frontController">
        <input type="hidden" name="cmd" value="CloseCheck">
        <input type="submit" value="print">
    </form>
    </c:if>
</c:if>
</body>
</html>
