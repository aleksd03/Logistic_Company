<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Logistic Company</title>
</head>
<body>

<h2>Welcome to ALVAS Logistics</h2>

<p>This is our main page.</p>

<p>
    <a href="${pageContext.request.contextPath}/register">Register</a> |
    <a href="${pageContext.request.contextPath}/login">Login</a> |
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
</p>

<%
    Object email = session.getAttribute("email");
    Object role  = session.getAttribute("role");
    if (email != null) {
        String r = (role != null) ? role.toString() : "UNKNOWN";
        String roleStr = r.charAt(0) + r.substring(1).toLowerCase();
%>
<p>You are signed in as <b><%= email %></b> (role: <%= roleStr %>)</p>
<%
} else {
%>
<p>You are not signed in.</p>
<%
    }
%>

</body>
</html>
