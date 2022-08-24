<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="employee" required="true" type="com.epam.cashierregister.services.entities.employee.Employee" %>
<%
    if (employee == null){
        out.print("employee not found");
    } else {
        out.print(employee.getFirstname() + " " + employee.getSecondname());
    }
%>