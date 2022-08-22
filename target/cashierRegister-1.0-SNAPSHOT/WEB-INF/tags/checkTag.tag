<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="check" required="true" type="com.epam.cashierregister.services.entities.check.Check" %>
<h1>Check №${check.id}</h1>
<p>${check.time}</p>
<p>Cashier: ${check.employee.firstname} ${check.employee.secondname}</p>
<p>---------------------------</p>
<p>GOODS:</p>
<c:forEach var="goods" items="${check.goodsSet}">
    <p>${goods.producer.name} ${goods.model}  x${goods.numbers} - ${goods.totalCost} $</p>
</c:forEach>
<p>---------------------------</p>
<p>TOTAL: ${check.totalCost} $</p>