<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="loc" scope="session" type="java.lang.String"/>
<jsp:useBean id="employee" scope="session" type="com.epam.cashierregister.services.entities.employee.Employee"/>
<fmt:setLocale value="${loc}"/>
<fmt:setBundle basename="language"/>
<header class="main_header">
    <div class="logo">
        <img class="logo_img" src="https://i.ibb.co/5GhXHzQ/logo.png">
    </div>
    <div class="navigator">
        <a href="cabinet">
            <div class="navigate_btn"><fmt:message key="cabinet.cabinet.btn.text"/></div>
        </a>
        <c:if test="${employee.role == 'SENIOR_CASHIER'}">
            <a href="checks">
                <div class="navigate_btn"><fmt:message key="cabinet.checks.btn.text"/></div>
            </a>
        </c:if>
        <c:if test="${employee.role == 'COMMODITY_EXPERT'}">
            <a href="goods">
                <div class="navigate_btn"><fmt:message key="cabinet.goods.btn.text"/></div>
            </a>
        </c:if>
        <c:if test="${employee.role == 'ADMIN'}">
            <a href="employees">
                <div class="navigate_btn">Employees</div>
            </a>
            <a href="#">
                <div class="navigate_btn">Reports</div>
            </a>
        </c:if>
    </div>
</header>
