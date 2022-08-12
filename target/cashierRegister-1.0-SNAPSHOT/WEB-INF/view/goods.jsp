<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>cashier</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Oswald&display=swap');
    </style>
    <style>
        <%@include file="../../css/main.css" %>
    </style>
    <style>
        <%@include file="../../css/goods.css" %>
    </style>
    <jsp:useBean id="goods" scope="request" type="java.util.List"/>
    <jsp:useBean id="currentPage" scope="session" type="java.lang.Integer"/>
    <jsp:useBean id="edit" scope="request" type="java.lang.Boolean"/>
    <jsp:useBean id="loc" scope="session" type="java.lang.String"/>
    <fmt:setLocale value="${loc}"/>
    <fmt:setBundle basename="language"/>
</head>
<body>
<%-- todo Barbecue API --%>
<c:import url="elements/header.jsp"/>
<main>
    <div class="title_div">
        <h1><fmt:message key="goods.goods.header"/></h1>
    </div>
    <table class="table">
        <th>id</th>
        <th><fmt:message key="goods.goods.table.model"/></th>
        <th><fmt:message key="goods.goods.table.category"/></th>
        <th><fmt:message key="goods.goods.table.producer"/></th>
        <th><fmt:message key="goods.goods.table.number"/></th>
        <th><fmt:message key="goods.goods.table.cost"/></th>
    </table>
    <section class="goods_section">
        <c:forEach var="good" items="${requestScope.goods}">
            <div class="goods_div">
                <c:if test="${edit}">
                    <form method="post" action="delete">
                        <button class="delete_btn" name="delete" value="${good.id}" type="submit">X</button>
                    </form>
                </c:if>
                <div class="id_div">
                        <img src="images/goodsPhotos/${good.photo}" width="30px" height="30px">
                        ${good.id}
                </div>
                <div class="model_div">${good.model}</div>
                <div class="category_div">${good.category.category}</div>
                <div class="producer_div">${good.producer.name}</div>
                <div class="number_div">${good.numbers}
                    <c:if test="${edit}">
                        <form method="post" action="edit">
                            <input class="edit_numb" name="numb" value="${good.numbers}" type="number" min="1" max="999">
                            <button class="edit_submit" name="id" value="${good.id}" type="submit">OK</button>
                        </form>
                    </c:if>
                </div>
                <div class="cost_div">${good.cost}</div>
            </div>
        </c:forEach>
    </section>
    <section class="pagination_section">
        <div class="counter">${currentPage}</div>
        <div class="all_buttons">
            <div class="create_btns">
                <form action="addGoods">
                    <button type="submit" class="edit_btn">add</button>
                </form>
            </div>
            <div class="buttons">
                <form method="post" action="changePage">
                    <button class="button_dir" name="page" value="left" type="submit"><</button>
                    <button class="button_first" name="page" value="first" type="submit">1</button>
                    <button class="button_dir" name="page" value="right" type="submit">></button>
                </form>
            </div>
            <div class="edit_btns">
                <form>
                    <button class="edit_btn" name="edit" value="true">edit</button>
                    <c:if test="${edit}">
                        <button class="cancel_btn" name="edit" value="false">cancel</button>
                    </c:if>
                </form>
            </div>
        </div>
    </section>
</main>
</body>
</html>