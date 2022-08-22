<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="ct"%>
<html>
<head>
    <title>check</title>
    <jsp:useBean id="newCheck" scope="session" type="com.epam.cashierregister.services.entities.check.Check"/>
</head>
<body>
    <ct:checkTag check="${newCheck}"/>
    <form action="createCheck">
        <input type="submit" value="exit">
    </form>
</body>
</html>
