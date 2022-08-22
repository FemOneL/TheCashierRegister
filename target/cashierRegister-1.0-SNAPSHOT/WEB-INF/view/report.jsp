<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="report" scope="session" type="com.epam.cashierregister.services.entities.report.Report"/>
<html>
<head>
    <title>REPORT</title>
</head>
<body>
    <h1>X - report</h1>
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
    <form action="cabinet">
        <input type="submit" value="exit">
    </form>
</body>
</html>
