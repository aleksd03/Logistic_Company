<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<html>
<head>
    <title>Shipments</title>
</head>
<body>

<h2>Register Shipment</h2>

<form method="post">
    Sender:
    <select name="sender">
        <c:forEach items="${clients}" var="c">
            <option value="${c.id}">${c.user.email}</option>
        </c:forEach>
    </select>

    Receiver:
    <select name="receiver">
        <c:forEach items="${clients}" var="c">
            <option value="${c.id}">${c.user.email}</option>
        </c:forEach>
    </select>

    Price:
    <input type="number" step="0.01" name="price" required>

    <button type="submit">Register</button>
</form>

<hr>

<h2>All Shipments</h2>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Status</th>
        <th>Action</th>
    </tr>

    <c:forEach items="${shipments}" var="s">
        <tr>
            <td>${s.id}</td>
            <td>${s.status}</td>
            <td>
                <c:if test="${s.status == 'SENT'}">
                    <form method="post">
                        <button name="receive" value="${s.id}">
                            Mark Received
                        </button>
                    </form>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
