<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <%@include file="../../css/authorization.css" %>
    </style>
    <jsp:useBean id="error" scope="request" type="java.lang.String"/>
    <jsp:useBean id="loc" scope="session" type="java.lang.String"/>
    <fmt:setLocale value="${loc}"/>
    <fmt:setBundle basename="language"/>
</head>
<body>
<header class="main_header">
    <div class="logo_center">
        <img class="logo_img" alt="err" src="images/cashier_register.png">
    </div>
</header>
<main>
    <section class="auth_section">
        <div class="tittle_div">
            <h1><fmt:message key="auth.sign.in"/></h1>
        </div>
        <div class="auth_div">
            <form method="post" action="frontController">
                <div class="login_div">
                    <label class="inp_label"><fmt:message key="auth.login"/><input class="inputfield" name="login" type="text"></label>
                </div>
                <div class="login_div">
                    <label class="inp_label"><fmt:message key="auth.password"/><input class="inputfield" name="password" type="password"></label>
                </div>
                <div class="error_div"><fmt:message key="errors.${error}"/></div>
                <div class="inner_div">
                    <input type="hidden" name="cmd" value="SignIn">
                    <button class="submit_btn" type="submit"><fmt:message key="auth.submit"/></button>
                </div>
            </form>
        </div>
    </section>
</main>
</body>
</html>