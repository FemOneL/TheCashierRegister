<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="report" scope="session" type="com.epam.cashierregister.services.entities.report.Report"/>
<jsp:useBean id="type" scope="session" type="java.lang.String"/>
<html>
<head>
    <title>REPORT</title>
    <style>
        <%@include file="../../css/main.css" %>
    </style>
    <style>
        <%@include file="../../css/report.css" %>
    </style>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Oswald&display=swap');
    </style>
</head>
<body>
<section class="report_section">
    <div class="report">
        <h1>${type} - report</h1>
        <h2>SELLING</h2>
        <p>Number of checks: ${report.selling.numberOfSellingChecks}</p>
        <p>Total sum: ${report.selling.sellingSum} $</p>
        <p>===========================</p>
        <h2>RETURN</h2>
        <p>Number of returned goods: ${report.returned.numberOfReturningGoods}</p>
        <p>Total sum: ${report.returned.returnedSum} $</p>
        <p>===========================</p>
        <p>${report.seniorCashier.firstname} ${report.seniorCashier.secondname} - ${report.seniorCashier.role}</p>
        <p>${report.createdDate} - ${report.date}</p>
        <p>profit: ${report.profit} $</p>
    </div>
</section>
<form action="cabinet">
    <input class="submit_btn" type="submit" value="exit">
</form>
</body>
</html>
