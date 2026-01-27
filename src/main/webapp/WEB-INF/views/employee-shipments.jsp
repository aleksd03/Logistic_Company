<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.informatics.entity.Shipment" %>
<%@ page import="org.informatics.entity.enums.Role" %>
<%@ page import="org.informatics.entity.enums.ShipmentStatus" %>
<%
    String userEmail = (String) session.getAttribute("userEmail");
    String firstName = (String) session.getAttribute("firstName");
    String lastName = (String) session.getAttribute("lastName");
    Role userRole = (Role) session.getAttribute("userRole");

    List<Shipment> shipments = (List<Shipment>) request.getAttribute("shipments");
    String success = request.getParameter("success");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="bg">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Пратки - ALVAS Logistics</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<div class="container">
    <header>
        <div class="header-content">
            <a href="${pageContext.request.contextPath}/" class="logo">ALVAS Logistics</a>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/">Начало</a></li>
                    <li><a href="${pageContext.request.contextPath}/employee-shipments">Пратки</a></li>
                    <li>
                        <div class="user-info">
                            👤 <%= firstName + " " + lastName %>
                            <span class="user-role">СЛУЖИТЕЛ</span>
                        </div>
                    </li>
                    <li><a href="${pageContext.request.contextPath}/logout">Изход</a></li>
                </ul>
            </nav>
        </div>
    </header>

    <main>
        <div class="page-header">
            <h1>📦 Всички пратки в системата</h1>
            <p>Като служител можете да виждате всички пратки регистрирани в системата</p>
        </div>

        <% if (success != null) { %>
        <div class="alert alert-success"><%= success %></div>
        <% } %>

        <% if (error != null) { %>
        <div class="alert alert-error"><%= error %></div>
        <% } %>

        <div class="card">
            <div class="table-container">
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Подател</th>
                        <th>Получател</th>
                        <th>Тегло (кг)</th>
                        <th>Цена (€)</th>
                        <th>Доставка</th>
                        <th>Статус</th>
                        <th>Регистрирана от</th>
                        <th>Дата</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% if (shipments != null && !shipments.isEmpty()) { %>
                    <% for (Shipment s : shipments) { %>
                    <tr>
                        <td><%= s.getId() %></td>
                        <td>
                            <%= s.getSender() != null && s.getSender().getUser() != null
                                    ? s.getSender().getUser().getFirstName() + " " + s.getSender().getUser().getLastName()
                                    : "N/A" %>
                        </td>
                        <td>
                            <%= s.getReceiver() != null && s.getReceiver().getUser() != null
                                    ? s.getReceiver().getUser().getFirstName() + " " + s.getReceiver().getUser().getLastName()
                                    : "N/A" %>
                        </td>
                        <td><%= String.format("%.2f", s.getWeight()) %></td>
                        <td><%= String.format("%.2f", s.getPrice()) %></td>
                        <td>
                            <% if (s.getDeliveryToOffice() != null && s.getDeliveryToOffice()) { %>
                            📍 <%= s.getDeliveryOffice() != null ? s.getDeliveryOffice().getAddress() : "Офис" %>
                            <% } else { %>
                            🏠 <%= s.getDeliveryAddress() != null ? s.getDeliveryAddress() : "Адрес" %>
                            <% } %>
                        </td>
                        <td>
                            <% if (s.getStatus() == ShipmentStatus.SENT) { %>
                            <span class="status status-sent">ИЗПРАТЕНА</span>
                            <% } else if (s.getStatus() == ShipmentStatus.RECEIVED) { %>
                            <span class="status status-received">ПОЛУЧЕНА</span>
                            <% } %>
                        </td>
                        <td>
                            <%= s.getRegisteredBy() != null && s.getRegisteredBy().getUser() != null
                                    ? s.getRegisteredBy().getUser().getFirstName() + " " + s.getRegisteredBy().getUser().getLastName()
                                    : "N/A" %>
                        </td>
                        <td><%= s.getRegistrationDate() != null ? s.getRegistrationDate().toString().substring(0, 16).replace("T", " ") : "N/A" %></td>
                    </tr>
                    <% } %>
                    <% } else { %>
                    <tr>
                        <td colspan="9" class="text-center">
                            <p>Няма регистрирани пратки в системата.</p>
                            <a href="${pageContext.request.contextPath}/shipment-register" class="btn btn-success" style="margin-top: 1rem;">
                                ➕ Регистрирай първа пратка
                            </a>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>

        <div style="margin-top: 1.5rem;">
            <a href="${pageContext.request.contextPath}/shipment-register" class="btn btn-success">➕ Регистрирай нова пратка</a>
            <a href="${pageContext.request.contextPath}/" class="btn btn-outline">← Обратно към началото</a>
        </div>
    </main>

    <footer>
        <p>&copy; 2025 ALVAS Logistics. Всички права запазени.</p>
    </footer>
</div>
</body>
</html>