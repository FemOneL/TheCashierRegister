<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <title>cashier</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Oswald&display=swap');
    </style>
    <style>
        <%@include file="../../css/main.css" %>
    </style>
    <style>
        <%@include file="../../css/createCheck.css" %>
    </style>
    <jsp:useBean id="activeCheck" scope="session" type="com.epam.cashierregister.services.entities.check.Check"/>
    <jsp:useBean id="error" scope="request" type="java.lang.String"/>
    <jsp:useBean id="loc" scope="session" type="java.lang.String"/>
    <fmt:setLocale value="${loc}"/>
    <fmt:setBundle basename="language"/>
</head>
<body>
<c:import url="elements/header.jsp"/>
<main>
    <section class="main_section">
        <div class="title_div">
            <h1><fmt:message key="createCheck.header.1"/></h1>
        </div>
        <div class="main_form">
            <h1><fmt:message key="createCheck.header.2"/></h1>
            <div class="search_div">
                <form method="post" action="frontController">
                    <input type="hidden" name="cmd" value="SearchGoods">
                    <input name="goods" class="my_text" type="text">
                    <input type="submit" value="<fmt:message key="createCheck.btn.add"/>">
                </form>
            </div>
            <div class="cart_div">
                <div class="goods_head">
                    <div><fmt:message key="createCheck.table.photo"/></div>
                    <div><fmt:message key="createCheck.table.model"/></div>
                    <div><fmt:message key="createCheck.table.number"/></div>
                    <div><fmt:message key="createCheck.table.cost"/></div>
                </div>
                <c:forEach var="good" items="${sessionScope.activeCheck.goodsSet}">
                    <div class="goods">
                        <div class="photo_div"><img src="images/${good.photo}" width="40px" height="40px"/></div>
                        <div class="model_div">${good.model}</div>
                        <div class="number_div">
                            <form method="post" action="frontController">
                                <input type="hidden" name="cmd" value="EditGoodsInCheck">
                                ${good.numbers}
                                <input class="number" name="edit_value" type="number" value="${good.numbers}" min="1" max="${good.totalNumber}">
                                <button type="submit" name="edit_goods_id" value="${good.id}">OK</button>
                            </form>
                        </div>
                        <div class="model_div">${good.totalCost} $</div>
                    </div>
                </c:forEach>
            </div>
            <div class="total_cost">${activeCheck.totalCost} $</div>
            <div class="buttons_div">
                <form method="get" action="pay">
                    <button class="submit_btn" name="command" value="cancel"><fmt:message key="createCheck.cancel"/></button>
                    <button class="submit_btn" name="command" value="pay"><fmt:message key="createCheck.pay"/></button>
                </form>
            </div>
            <div class="error"><fmt:message key="errors.${error}"/></div>
        </div>
    </section>
</main>
</body>
</html>
