<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="st" uri="selectOptionTag" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <%@include file="../../css/checks.css" %>
    </style>
    <style>
        <%@include file="../../css/employees.css" %>
    </style>
    <jsp:useBean id="employees" scope="request" type="java.util.Set"/>
    <jsp:useBean id="roles" scope="request" type="java.util.Set"/>
    <jsp:useBean id="error" scope="request" type="java.lang.String"/>
    <jsp:useBean id="currentPage" scope="session" type="java.lang.Integer"/>
    <jsp:useBean id="loc" scope="session" type="java.lang.String"/>
    <fmt:setLocale value="${loc}"/>
    <fmt:setBundle basename="language"/>
</head>
<body>
<c:import url="elements/header.jsp"/>
<main>
    <div class="title_div">
        <h1 style="margin-bottom: 10px"><fmt:message key="employees.title"/></h1>
        <form method="post" action="frontController">
            <input type="hidden" name="cmd" value="Search">
            <input type="text" name="search" placeholder="search...">
            <button type="submit" name="view" value="employees"><fmt:message key="search.btn"/></button>
        </form>
    </div>
    <section class="user_section">
        <c:forEach var="employee" items="${requestScope.employees}">
            <div class="emp_form">
                <div class="photo_div">
                    <img class="emp_img" src="images/${employee.photo}" alt="images/usersPhotos/nonuser.jpg">
                </div>
                <div class="emp_title">
                    <form method="post" action="frontController">
                        <input type="hidden" name="cmd" value="DeleteEmployee">
                        <button type="submit" name="empId" value="${employee.id}"><fmt:message key="delete.btn"/></button>
                    </form>
                    <h1>${employee.firstname} ${employee.secondname}</h1>
                    <h2>[${employee.role}]</h2>
                    <form method="post" action="frontController">
                        <select name="role">
                            <c:forEach var="role" items="${requestScope.roles}">
                                <st:SelectTag role="${role.name()}" emp="${employee.role.name()}"/>
                            </c:forEach>
                        </select>
                        <input type="hidden" name="cmd" value="ChangeRole">
                        <button name="empId" value="${employee.id}" type="submit">ok</button>
                    </form>
                    <h2>${employee.authorize.email}</h2>
                    <p class="error"><fmt:message key="errors.${error}"/></p>
                    <form method="post" action="frontController">
                        <label><fmt:message key="employees.changePassword"/>:
                            <input type="hidden" name="cmd" value="ChangePassword">
                            <input type="text" name="newPassword">
                            <button type="submit" name="gmail" value="${employee.authorize.email}"><fmt:message key="change.btn"/></button>
                        </label>
                    </form>
                </div>
            </div>
        </c:forEach>
        <div class="counter">${currentPage}</div>
        <div class="buttons">
            <form method="post" action="frontController?pageName=employees&size=1">
                <input type="hidden" name="cmd" value="ChangePage">
                <button class="button_dir" name="page" value="left" type="submit"><</button>
                <button class="button_first" name="page" value="first" type="submit">1</button>
                <button class="button_dir" name="page" value="right" type="submit">></button>
            </form>
        </div>
    </section>
</main>
</body>
</html>
