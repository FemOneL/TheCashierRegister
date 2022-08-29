<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <jsp:useBean id="error" scope="session" type="java.lang.String"/>
</head>
<body>
<c:import url="elements/header.jsp"/>
<main>
    <section class="register_section">
        <form method="post" action="frontController" enctype="multipart/form-data">
            <div class="title_div">
                <h1>Sign up</h1>
            </div>
            <div class="main_form">
                <div class="name">
                    <input type="file" name="photo">
                    <label>FIRSTNAME: <input name="firstname" type="text"></label>
                    <label>SECONDNAME: <input name="secondname" type="text"></label>
                </div>
                <div class="other_fields">
                    <div class="text_div">
                        <label>EMAIL: <input name="email" type="text"></label>
                    </div>
                    <div class="text_div">
                        <label>PASSWORD: <input name="password" type="text"></label>
                    </div>
                    <div class="text_div">
                        <label>PASSWORD: <input name="secondPassword" type="text"></label>
                    </div>
                    <div class="text_div">
                        <label>ROLE:
                            <select name="role">
                                <c:forEach var="role" items="${requestScope.roles}">
                                    <option>${role}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                    <p class="error">${error}</p>
                    <button name="cmd" value="SignUp" type="submit">SIGN UP</button>
                </div>
            </div>
        </form>
</main>
</body>
</html>