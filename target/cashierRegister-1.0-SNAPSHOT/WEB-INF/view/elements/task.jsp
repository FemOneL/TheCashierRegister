<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="employee" scope="session" type="com.epam.cashierregister.services.entities.employee.Employee"/>
<jsp:useBean id="loc" scope="session" type="java.lang.String"/>
<fmt:setLocale value="${loc}"/>
<fmt:setBundle basename="language"/>
<section class="task_section">
    <c:if test="${employee.role == 'COMMODITY_EXPERT'}">
        <div class="task">
            <div class="todo">
                <h1 class="task_title"><fmt:message key="cabinet.add.goods"/></h1>
                <p><fmt:message key="cabinet.add.goods.title"/></p>
            </div>
            <div class="task_btn_div">
                <form action="addGoods">
                    <button class="task_btn" type="submit"><fmt:message key="cabinet.go.button"/></button>
                </form>
            </div>
        </div>
    </c:if>
    <c:if test="${employee.role == 'CASHIER'}">
        <div class="task">
            <div class="todo">
                <h1 class="task_title"><fmt:message key="cabinet.create.check"/></h1>
                <p><fmt:message key="cabinet.create.check.title"/></p>
            </div>
            <div class="task_btn_div">
                <form>
                    <button class="task_btn" type="submit"><fmt:message key="cabinet.go.button"/></button>
                </form>
            </div>
        </div>
    </c:if>
    <c:if test="${employee.role == 'SENIOR_CASHIER'}">
        <div class="task">
            <div class="todo">
                <h1 class="task_title"><fmt:message key="cabinet.cancel.check"/></h1>
                <p><fmt:message key="cabinet.cancel.check.title"/></p>
            </div>
            <div class="task_btn_div">
                <form>
                    <button class="task_btn" type="submit"><fmt:message key="cabinet.go.button"/></button>
                </form>
            </div>
        </div>
        <div class="task">
            <div class="todo">
                <h1 class="task_title"><fmt:message key="cabinet.cancel.goods"/></h1>
                <p><fmt:message key="cabinet.cancel.goods.tittle"/></p>
            </div>
            <div class="task_btn_div">
                <form>
                    <button class="task_btn" type="submit"><fmt:message key="cabinet.go.button"/></button>
                </form>
            </div>
        </div>
        <div class="task">
            <div class="todo">
                <h1 class="task_title"><fmt:message key="cabinet.create.x.and.z.reports"/></h1>
                <p><fmt:message key="cabinet.create.x.and.z.reports.title"/></p>
            </div>
            <div class="task_btn_div">
                <form>
                    <button class="task_btn" type="submit"><fmt:message key="cabinet.go.button"/></button>
                </form>
            </div>
        </div>
    </c:if>
</section>