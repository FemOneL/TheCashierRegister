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
    <jsp:useBean id="error" scope="session" type="java.lang.String"/>
    <jsp:useBean id="loc" scope="session" type="java.lang.String"/>
    <fmt:setLocale value="${loc}"/>
    <fmt:setBundle basename="language"/>
</head>
<body>
<c:import url="elements/header.jsp"/>
<main>
    <section class="main_section">
        <div class="title_div">
            <h1><fmt:message key="editCheck.header"/></h1>
        </div>
        <div class="main_form">
            <form method="post" action="frontController">
                <input type="hidden" name="cmd" value="DeleteCheck">
                <button type="submit"><fmt:message key="delete.btn"/></button>
            </form>
            <div class="edit_cart_div">
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
                        <div class="number_div">${good.numbers}
                            <form method="post" action="frontController">
                                <input type="number" name="editNumber" min="0" max="${good.numbers}" value="${good.numbers}">
                                <input type="hidden" name="cmd" value="DeleteGoodsFromCheck">
                                <button name="good" value="${good.id}" type="submit">OK</button>
                            </form>
                        <div class="model_div">${good.totalCost} $</div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="total_cost">${activeCheck.totalCost} $</div>
            <div class="buttons_div">
                <form action="checks">
                    <button class="submit_btn">OK</button>
                </form>
            </div>
            <div class="error"><fmt:message key="errors.${error}"/></div>
        </div>
    </section>
</main>
</body>
</html>