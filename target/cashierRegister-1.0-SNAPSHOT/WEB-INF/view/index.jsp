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
</head>
<body>
<header class="main_header">
    <div class="logo">
        <img class="logo_img" src="../images/logo.png">
    </div>
    <div class="navigator">
        <%
            Properties lang = (Properties)request.getAttribute("language");
        %>
        <input class="navigate_btn" type="button" value="<%= lang.getProperty("checks_btn_text") %>">
        <input class="navigate_btn" type="button" value="<%= lang.getProperty("products_btn_text") %>">
        <form method="post" action="">
            <button class="btn" type="submit" value="eng" name="lang">ENG</button>
            <button class="btn" type="submit" value="ua" name="lang">UA</button>
        </form>
    </div>
</header>
<main>
    <section class="user_info">
        <div class="welcome_div">
            <h1 class="welcome"><%= lang.getProperty("welcome_text") %></h1>
        </div>
        <div class="role_div">
            <h2 class="role"><%= lang.getProperty("role_text") %></h2>
        </div>
    </section>
</main>
</body>
</html>