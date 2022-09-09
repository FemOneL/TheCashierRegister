<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <style>
        <%@include file="../../css/pay.css" %>
    </style>
    <jsp:useBean id="type" scope="session" type="java.lang.String"/>
    <jsp:useBean id="remainder" scope="session" type="java.math.BigDecimal"/>
    <jsp:useBean id="error" scope="request" type="java.lang.String"/>
    <jsp:useBean id="activeCheck" scope="session" type="com.epam.cashierregister.services.entities.check.Check"/>
    <jsp:useBean id="sum" scope="session" type="java.math.BigDecimal"/>
    <jsp:useBean id="loc" scope="session" type="java.lang.String"/>
    <fmt:setLocale value="${loc}"/>
    <fmt:setBundle basename="language"/>
    <jsp:useBean id="payAnother" scope="session" type="java.lang.String"/>
</head>
<body>
<c:import url="elements/header.jsp"/>
<div class="pay_type">
    <form method="post" action="frontController">
        <input type="hidden" name="cmd" value="ChoosePaymentType">
        <button type="submit" class="submit_btn" value="cash" name="type"><fmt:message key="pay.cash"/></button>
        <button type="submit" class="submit_btn" value="card" name="type"><fmt:message key="pay.card"/></button>
    </form>
</div>
<c:if test="${type == 'card'}">
    <div class="card_div">
        <h1><fmt:message key="pay.card.title"/></h1>
        <img class="image" src="images/term.png">
        <form method="post" action="frontController">
            <input type="hidden" name="cmd" value="CloseCheck">
            <input class="submit_btn" type="submit" value="<fmt:message key="print.btn"/>">
        </form>
    </div>
</c:if>
<c:if test="${type == 'cash'}">
    <div class="cash_div">
        <h1><fmt:message key="pay.cash.title"/></h1>
        <form method="post" action="frontController">
            <input type="hidden" name="cmd" value="Reminder">
            <input class="cost_field" name="count" type="text">
            <input class="ok_btn" type="submit" value="OK">
        </form>
        <p class="error"><fmt:message key="errors.${error}"/></p>
        <div class="title">
            <p><fmt:message key="pay.cash.total"/>: ${activeCheck.totalCost} $</p>
            <p><fmt:message key="pay.cash.another"/>: ${payAnother} $</p>
            <p><fmt:message key="pay.cash.remaining"/>: ${remainder} $</p>
        </div>
        <c:if test="${sum.doubleValue() >= activeCheck.totalCost}">
            <form method="post" action="frontController">
                <input type="hidden" name="cmd" value="CloseCheck">
                <input class="submit_btn" type="submit" value="<fmt:message key="print.btn"/>">
            </form>
        </c:if>
    </div>
</c:if>
</body>
</html>
