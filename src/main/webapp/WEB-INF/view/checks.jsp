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
        <%@include file="../../css/checks.css" %>
    </style>
    <jsp:useBean id="checks" scope="request" type="java.util.List"/>
    <jsp:useBean id="currentPage" scope="session" type="java.lang.Integer"/>
</head>
<body>
<c:import url="elements/header.jsp"/>
<main>
    <section class="checks_section">
        <c:forEach var="check" items="${requestScope.checks}">
            <div class="check">
                <div>Check №${check.id}</div>
                <div>${check.time}</div>
                <div>${check.employee.firstname} ${check.employee.secondname}</div>
                <div>${check.totalCost} $</div>
                <form method="get" action="editExisting">
                    <button name="edit" value="${check.id}">Edit</button>
                </form>
            </div>
        </c:forEach>
    </section>
    <div class="counter">${currentPage}</div>
    <div class="buttons">
        <form method="post" action="frontController?pageName=checks&size=21">
            <input type="hidden" name="cmd" value="ChangePage">
            <button class="button_dir" name="page" value="left" type="submit"><</button>
            <button class="button_first" name="page" value="first" type="submit">1</button>
            <button class="button_dir" name="page" value="right" type="submit">></button>
        </form>
    </div>
</main>
</body>
</html>
