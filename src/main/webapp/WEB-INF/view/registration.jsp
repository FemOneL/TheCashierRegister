<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>cashier</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Oswald&display=swap');
    </style>
    <style>
        <%@include file="../../css/main.css" %>
    </style>
    <style>
        <%@include file="../../css/registration.css" %>
    </style>
    <jsp:useBean id="roles" scope="request" type="java.util.Set"/>
    <jsp:useBean id="error" scope="request" type="java.lang.String"/>
    <jsp:useBean id="loc" scope="session" type="java.lang.String"/>
    <fmt:setLocale value="${loc}"/>
    <fmt:setBundle basename="language"/>
</head>
<body>
<c:import url="elements/header.jsp"/>
<main>
    <section class="register_section">
        <form method="post" action="frontController" enctype="multipart/form-data">
            <div class="title_div">
                <h1><fmt:message key="signup.title"/></h1>
            </div>
            <div class="main_form">
                <div class="name">
                    <label><fmt:message key="signup.firstname"/>: <input name="firstname" type="text"></label>
                    <label><fmt:message key="signup.secondname"/>: <input name="secondname" type="text"></label>
                </div>
                <div class="other_fields">
                    <div class="text_div">
                        <input type="file" name="photo">
                    </div>
                    <div class="text_div">
                        <label><fmt:message key="signup.email"/>: <input name="email" type="text"></label>
                    </div>
                    <div class="text_div">
                        <label><fmt:message key="signup.password"/>: <input name="password" type="text"></label>
                    </div>
                    <div class="text_div">
                        <label><fmt:message key="signup.password.repeat"/>: <input name="secondPassword" type="text"></label>
                    </div>
                    <div class="text_div">
                        <label><fmt:message key="signup.role"/>:
                            <select name="role">
                                <c:forEach var="role" items="${requestScope.roles}">
                                    <option>${role}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                    <p class="error"><fmt:message key="errors.${error}"/></p>
                    <button name="cmd" value="SignUp" class="submit_btn" type="submit"><fmt:message key="signup.signup"/></button>
                </div>
            </div>
        </form>
</main>
</body>
</html>