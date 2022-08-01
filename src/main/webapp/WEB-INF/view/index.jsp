<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
        <%@include file="../../css/index.css" %>
    </style>
    <jsp:useBean id="loc" scope="session" type="java.lang.String"/>
    <jsp:useBean id="employee" scope="session" type="com.epam.cashierregister.model.entities.employee.Employee"/>
    <fmt:setLocale value="${loc}"/>
    <fmt:setBundle basename="language"/>
</head>
<body>
<header class="main_header">
    <div class="logo">
        <img class="logo_img" src="https://i.ibb.co/5GhXHzQ/logo.png">
    </div>
    <div class="navigator">
        <a href="#"><div class="navigate_btn"><fmt:message key="indx.cabinet.btn.text"/></div></a>
        <a href="#"><div class="navigate_btn"><fmt:message key="indx.checks.btn.text"/></div></a>
        <a href="#"><div class="navigate_btn"><fmt:message key="indx.goods.btn.text"/></div></a>
    </div>
</header>
<main>
    <section class="user_info">
        <div class="upper_div">
            <form method="get" action="/cashier/">
                <button type="submit" class="logout_btn"><fmt:message key="indx.log.out"/></button>
            </form>
            <div class="language_div">
                <form method="post" action="cabinet">
                    <button class="lang" type="submit" value="eng" name="lang"><img class="flag_img" src="https://i.ibb.co/GHbGb1s/eng-flag.png"></button>
                    <button class="lang" type="submit" value="ua" name="lang"><img class="flag_img" src="https://i.ibb.co/cQwMrnF/ua-flag.png"></button>
                </form>
            </div>
        </div>
        <div class="welcome_div">
            <h1 class="welcome"><fmt:message key="indx.welcome.text"/> ${employee.firstname} ${employee.secondname}</h1>
        </div>
        <div class="role_div">
            <%--todo localize roles --%>
            <h2 class="role">[${employee.role}]</h2>
        </div>
    </section>
</main>
</body>
</html>