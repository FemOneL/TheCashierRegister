<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>ERROR PAGE</title>
  <jsp:useBean id="errorCode" scope="request" type="java.lang.Integer"/>
  <jsp:useBean id="errorMessage" scope="request" type="java.lang.String"/>
</head>
<body>
    <h1>ERROR</h1>
    <h1>${errorCode}</h1>
    <p>${errorMessage}</p>
    <a href="${pageContext.request.contextPath}/">RETURN</a>
</body>
</html>
