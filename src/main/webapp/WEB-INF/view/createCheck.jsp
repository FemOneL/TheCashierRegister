<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
</head>
<body>
<c:import url="elements/header.jsp"/>
<main>
    <section class="main_section">
        <div class="title_div">
            <h1>Create check</h1>
        </div>
        <div class="main_form">
            <h1>add goods</h1>
            <div class="search_div">
                <form method="post" action="search">
                    <input name="goods" class="my_text" type="text">
                    <input type="submit" value="add">
                </form>
            </div>
            <div class="cart_div">
                <div class="goods_head">
                    <div>model</div>
                    <div>number</div>
                    <div>cost</div>
                </div>
                <c:forEach var="good" items="${sessionScope.activeCheck.goodsSet}">
                    <div class="goods">
                        <div class="model_div">${good.model}</div>
                        <div class="number_div">
                            <form method="post" action="editGoodsInCheck">
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
                <form>
                    <button class="submit_btn">Create</button>
                    <button class="submit_btn">Create and close</button>
                </form>
            </div>
            <div class="error">${error}</div>
        </div>
    </section>
</main>
</body>
</html>
