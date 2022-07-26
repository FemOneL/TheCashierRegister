<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <%@include file="../../css/addGoods.css" %>
    </style>
    <jsp:useBean id="loc" scope="session" type="java.lang.String"/>
    <jsp:useBean id="categories" scope="request" type="java.util.List"/>
    <jsp:useBean id="producers" scope="request" type="java.util.List"/>
    <jsp:useBean id="error" scope="request" type="java.lang.String"/>
    <fmt:setLocale value="${loc}"/>
    <fmt:setBundle basename="language"/>
</head>
<body>
<c:import url="elements/header.jsp"/>
<main>
    <section class="add_section">
        <div class="title_div">
            <h1><fmt:message key="addGoods.header"/></h1>
        </div>
       <form method="post" action="frontController" enctype="multipart/form-data">
            <div class="main_div">
                <div class="top_div">
                    <div class="model_div">
                        <h2><fmt:message key="addGoods.model"/></h2>
                        <input name="model" class="my_text" type="text">
                        <input type="file" name="photo">
                    </div>
                    <div class="category_and_producer_div">
                        <div class="select_div">
                            <h2><fmt:message key="addGoods.category"/></h2>
                            <select class="my_select" name="select_category">
                                <option>none</option>
                                <c:forEach var="category" items="${requestScope.categories}">
                                    <option>${category.category}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="manual_div">
                            <h2><fmt:message key="addGoods.category.new"/></h2>
                            <input name="new_category" class="my_text" type="text">
                        </div>
                    </div>
                    <div class="category_and_producer_div">
                        <div class="select_div">
                            <h2><fmt:message key="addGoods.producer"/></h2>
                            <select class="my_select" name="select_producer">
                                <option>none</option>
                                <c:forEach var="producer" items="${requestScope.producers}">
                                    <option>${producer.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="manual_div">
                            <h2><fmt:message key="addGoods.producer.new"/></h2>
                            <input name="new_producer" class="my_text" type="text">
                        </div>
                    </div>
                </div>
                <div class="midle_div">
                    <div class="number_div">
                        <label><fmt:message key="addGoods.number"/>: <input name="number" class="number_inp" type="number" value="1" min="1"></label>
                    </div>
                    <div class="cost_div">
                        <label><fmt:message key="addGoods.cost"/>: <input name="cost" class="number_inp" type="number" value="00" min="0"> . <input
                                name="cents" class="number_inp" type="number" value="00"></label>
                    </div>
                </div>
                <div class="bottom_div">
                    <input type="hidden" name="cmd" value="AddNewGoods">
                    <button type="submit" class="submit_btn"><fmt:message key="addGoods.add"/></button>
                </div>
            </div>
        </form>
        <div class="error_div">
            <p class="error_p"><fmt:message key="errors.${error}"/></p>
        </div>
    </section>
</main>
</body>
</html>
