<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
        <%@include file="../../css/cabinet.css" %>
    </style>
    <jsp:useBean id="loc" scope="session" type="java.lang.String"/>
    <jsp:useBean id="employee" scope="session" type="com.epam.cashierregister.services.entities.employee.Employee"/>
    <fmt:setLocale value="${loc}"/>
    <fmt:setBundle basename="language"/>
</head>
<body>
    <c:import url="elements/header.jsp"/>
<main>
    <section class="user_info">
        <div class="upper_div">
            <form method="get" action="authorize">
                <button type="submit" class="logout_btn"><fmt:message key="cabinet.log.out"/></button>
            </form>
            <div class="language_div">
                <form method="post" action="frontController">
                    <input type="hidden" name="cmd" value="ChangeLang">
                    <button class="lang" type="submit" value="eng" name="lang"><img class="flag_img" alt="eng"
                                                                                    src="images/eng_flag.png">
                    </button>
                    <button class="lang" type="submit" value="ua" name="lang"><img class="flag_img" alt="ua"
                                                                                   src="images/ua_flag.png">
                    </button>
                </form>
            </div>
        </div>
        <div class="welcome_div">
            <img class="emp_image" src="images/${employee.photo}">
            <h1 class="welcome"><fmt:message key="cabinet.welcome.text"/> ${employee.firstname} ${employee.secondname}</h1>
        </div>
        <div class="role_div">
            <h2 class="role">[<fmt:message key="cabinet.role.${employee.role.roleForTranslate}"/>]</h2>
        </div>
            <jsp:include page="elements/task.jsp"/>
    </section>
</main>
</body>
</html>