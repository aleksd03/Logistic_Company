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
  <title>Неполучени пратки - ALVAS Logistics</title>
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
      <h1>📦 Неполучени пратки</h1>
      <p>Списък на всички пратки, които са изпратени, но все още не са получени от клиентите</p>
    </div>

    <% if (success != null) { %>
    <div class="alert alert-success"><%= success %></div>
    <% } %>

    <% if (error != null) { %>
    <div class="alert alert-error"><%= error %></div>
    <% } %>

    <div class="card">
      <!-- STATISTICS HEADER -->
      <div style="padding: 0.75rem 1rem; border-bottom: 2px solid #e0e0e0; background: #fff3cd;">
        <div style="display: flex; align-items: center; gap: 0.5rem; color: #856404;">
          <span style="font-size: 1.5rem;">⚠️</span>
          <strong style="font-size: 1rem;">
            <%= shipments != null ? shipments.size() : 0 %> <%= (shipments != null && shipments.size() == 1) ? "пратка чака" : "пратки чакат" %> доставка
          </strong>
        </div>
      </div>

      <div class="table-container">
        <table>
          <thead>
          <tr>
            <th>ID</th>
            <th>Подател</th>
            <th>Получател</th>
            <th>Тегло</th>
            <th>Цена</th>
            <th>Доставка</th>
            <th>Дата на регистрация</th>
            <th>Дни в транзит</th>
          </tr>
          </thead>
          <tbody>
          <% if (shipments != null && !shipments.isEmpty()) { %>
          <%
            java.time.LocalDateTime now = java.time.LocalDateTime.now();
            for (Shipment s : shipments) {
              long daysInTransit = java.time.temporal.ChronoUnit.DAYS.between(s.getRegistrationDate(), now);
          %>
          <tr>
            <td><%= s.getId() %></td>
            <td>
              <%= s.getSender() != null
                      ? (s.getSender().getUser() != null
                      ? s.getSender().getUser().getFirstName() + " " + s.getSender().getUser().getLastName()
                      : "N/A")
                      : "Изтрит клиент" %>
            </td>
            <td>
              <%= s.getReceiver() != null
                      ? (s.getReceiver().getUser() != null
                      ? s.getReceiver().getUser().getFirstName() + " " + s.getReceiver().getUser().getLastName()
                      : "N/A")
                      : "Изтрит клиент" %>
            </td>
            <td><%= String.format("%.2f", s.getWeight()) %> kg</td>
            <td><%= String.format("%.2f", s.getPrice()) %>€</td>
            <td>
              <%= s.getDeliveryToOffice()
                      ? (s.getDeliveryOffice() != null ? "📍 " + s.getDeliveryOffice().getAddress() : "Офис изтрит")
                      : "🏠 " + (s.getDeliveryAddress() != null ? s.getDeliveryAddress() : "N/A") %>
            </td>
            <td><%= s.getRegistrationDate().toString().substring(0, 16).replace("T", " ") %></td>
            <td>
              <% if (daysInTransit > 5) { %>
              <span style="color: #dc3545; font-weight: bold;">🔴 <%= daysInTransit %> дни</span>
              <% } else if (daysInTransit > 2) { %>
              <span style="color: #ff9800; font-weight: bold;">🟠 <%= daysInTransit %> дни</span>
              <% } else { %>
              <span style="color: #28a745; font-weight: bold;">🟢 <%= daysInTransit %> дни</span>
              <% } %>
            </td>
          </tr>
          <% } %>
          <% } else { %>
          <tr>
            <td colspan="8" class="text-center">
              <div style="padding: 2rem;">
                <p style="font-size: 1.1rem; color: #28a745; margin-bottom: 0.5rem;">✅ Отлично!</p>
                <p style="color: #666;">Няма неполучени пратки в системата. Всички пратки са доставени успешно!</p>
              </div>
            </td>
          </tr>
          <% } %>
          </tbody>
        </table>
      </div>
    </div>

    <div style="margin-top: 1.5rem;">
      <a href="${pageContext.request.contextPath}/employee-shipments" class="btn btn-primary">← Обратно към всички пратки</a>
      <a href="${pageContext.request.contextPath}/" class="btn btn-outline">← Обратно към началото</a>
    </div>
  </main>

  <footer>
    <p>&copy; 2025 ALVAS Logistics. Всички права запазени.</p>
  </footer>
</div>
</body>
</html>
