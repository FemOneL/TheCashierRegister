<%@ page import="java.util.Properties" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
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
    <% Properties language = (Properties)request.getAttribute("language"); %>
</head>
<body>
<header class="main_header">
    <div class="logo">
        <img class="logo_img" src="https://i.ibb.co/5GhXHzQ/logo.png">
    </div>
    <div class="navigator">
        <input class="navigate_btn" type="button" value="<%= language.getProperty("cabinet.btn.text") %>">
        <input class="navigate_btn" type="button" value="<%= language.getProperty("checks.btn.text") %>">
        <input class="navigate_btn" type="button" value="<%= language.getProperty("goods.btn.text") %>">
    </div>
</header>
<main>
    <section class="user_info">
        <div class="upper_div">
            <button class="logout_btn"><%= language.getProperty("log.out") %></button>
            <div class="language_div">
                <form method="post">
                    <button class="lang" type="submit" value="eng" name="lang"><img class="flag_img" src="https://i.ibb.co/GHbGb1s/eng-flag.png"></button>
                    <button class="lang" type="submit" value="ua" name="lang"><img class="flag_img" src="https://i.ibb.co/cQwMrnF/ua-flag.png"></button>
                </form>
            </div>
        </div>
        <div class="welcome_div">
            <h1 class="welcome"><%= language.getProperty("welcome.text") %></h1>
        </div>
        <div class="role_div">
            <h2 class="role"><%= language.getProperty("role.text") %></h2>
        </div>
    </section>
</main>
</body>
</html>