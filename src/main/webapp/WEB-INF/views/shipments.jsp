<%@ page contentType="text/html; charset=UTF-8" %>

<!-- JSTL core tag library (forEach, if, expressions, etc.) -->
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<html>
<head>
    <title>Shipments</title>
</head>
<body>

<!-- ================= REGISTER SHIPMENT FORM ================= -->
<h2>Register Shipment</h2>

<!-- 
     Form submits via POST to the same servlet/controller.
     Used to register a new shipment.
-->
<form method="post">

    <!-- Sender selection -->
    Sender:
    <select name="sender">
        <!-- Iterate over all clients passed from the servlet -->
        <c:forEach items="${clients}" var="c">
            <!-- Client ID is sent, email is shown -->
            <option value="${c.id}">
                ${c.user.email}
            </option>
        </c:forEach>
    </select>

    <!-- Receiver selection -->
    Receiver:
    <select name="receiver">
        <!-- Reuse same clients list for receiver -->
        <c:forEach items="${clients}" var="c">
            <option value="${c.id}">
                ${c.user.email}
            </option>
        </c:forEach>
    </select>

    <!-- Shipment price -->
    Price:
    <input type="number"
           step="0.01"
           name="price"
           required>

    <!-- Submit shipment registration -->
    <button type="submit">Register</button>
</form>

<hr>

<!-- ================= SHIPMENTS LIST ================= -->
<h2>All Shipments</h2>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Status</th>
        <th>Action</th>
    </tr>

    <!-- Iterate over all shipments -->
    <c:forEach items="${shipments}" var="s">
        <tr>
            <!-- Shipment ID -->
            <td>${s.id}</td>

            <!-- Shipment status -->
            <td>${s.status}</td>

            <!-- Conditional action column -->
            <td>
                <!-- Show action only if shipment is still SENT -->
                <c:if test="${s.status == 'SENT'}">
                    <!-- POST request to mark shipment as received -->
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

