<%@ page import="java.util.Properties" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>cashier</title>
    <style>
        <%@include file="../../css/main.css" %>
    </style>
    <style>
        <%@include file="../../css/index.css" %>
    </style>
    <fmt:setLocale value="${loc}"/>
    <fmt:setBundle basename="language"/>
</head>
<body>
<header class="main_header">
    <div class="logo">
        <img class="logo_img" src="https://i.ibb.co/5GhXHzQ/logo.png">
    </div>
    <div class="navigator">
        <input class="navigate_btn" type="button" value="<fmt:message key="cabinet.btn.text"/>">
        <input class="navigate_btn" type="button" value="<fmt:message key="checks.btn.text"/>">
        <input class="navigate_btn" type="button" value="<fmt:message key="goods.btn.text"/>">
    </div>
</header>
<main>
    <section class="user_info">
        <div class="upper_div">
            <button class="logout_btn"><fmt:message key="log.out"/></button>
            <div class="language_div">
                <form method="post">
                    <button class="lang" type="submit" value="eng" name="lang"><img class="flag_img" src="https://i.ibb.co/GHbGb1s/eng-flag.png"></button>
                    <button class="lang" type="submit" value="ua" name="lang"><img class="flag_img" src="https://i.ibb.co/cQwMrnF/ua-flag.png"></button>
                </form>
            </div>
        </div>
        <div class="welcome_div">
            <h1 class="welcome"><fmt:message key="welcome.text"/></h1>
        </div>
        <div class="role_div">
            <h2 class="role"><fmt:message key="role.text"/></h2>
        </div>
    </section>
</main>
</body>
</html>