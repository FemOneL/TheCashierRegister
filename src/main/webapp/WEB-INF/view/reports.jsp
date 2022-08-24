<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <%@include file="../../css/checks.css" %>
    </style>
    <style>
        <%@include file="../../css/reports.css" %>
    </style>
    <jsp:useBean id="reports" scope="request" type="java.util.Set"/>
    <jsp:useBean id="currentPage" scope="session" type="java.lang.Integer"/>
</head>
<body>
<c:import url="elements/header.jsp"/>
<main>
    <div class="title_div">
        <h1>Reports</h1>
        <form method="post" action="frontController">
            <input type="date" name="search">
            <input type="hidden" name="cmd" value="Search">
            <button type="submit" name="view" value="reports">search</button>
        </form>
    </div>
    <table class="table">
        <th>Date</th>
        <th>selling</th>
        <th>returned</th>
        <th>profit</th>
    </table>
    <section class="reports_section">
        <c:forEach var="report" items="${requestScope.reports}">
            <div class="report_div">
                <div class="inner_div">${report.date}</div>
                <div class="inner_div">${report.selling.sellingSum} $</div>
                <div class="inner_div">${report.returned.returnedSum} $</div>
                <div class="inner_div">${report.profit} $</div>
            </div>
        </c:forEach>

    </section>
    <div class="counter">${currentPage}</div>
    <div class="buttons">
        <form method="post" action="frontController?pageName=reports&size=5">
            <input type="hidden" name="cmd" value="ChangePage">
            <button class="button_dir" name="page" value="left" type="submit"><</button>
            <button class="button_first" name="page" value="first" type="submit">1</button>
            <button class="button_dir" name="page" value="right" type="submit">></button>
        </form>
    </div>
</main>
</body>
</html>

