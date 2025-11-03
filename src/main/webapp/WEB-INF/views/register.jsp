<%--
  Created by IntelliJ IDEA.
  User: alexd
  Date: 11/3/2025
  Time: 9:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Register</title></head>
<body>
<h2>Register</h2>

<% String err = (String) request.getAttribute("error");
    String ok = (String) request.getAttribute("success");
    if (err != null) { %><p style="color:red;"><%= err %></p><% }
    if (ok != null)  { %><p style="color:green;"><%= ok %></p><% } %>

<form method="post" action="${pageContext.request.contextPath}/register">
    <label>First Name: <input name="firstName" required></label><br>
    <label>Last Name:  <input name="lastName"  required></label><br>
    <label>Email:      <input name="email" type="email" required></label><br>
    <label>Password:   <input name="password" type="password" required></label><br>
    <label>Confirm Password:    <input name="confirm"  type="password" required></label><br>
    <label>Role:
        <select name="role" required>
            <option value="CLIENT">Client</option>
            <option value="EMPLOYEE">Employee</option>
        </select>
    </label><br>
    <button type="submit">Create account</button>
</form>
</body>
</html>
