<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.informatics.entity.Shipment" %>
<%@ page import="org.informatics.entity.enums.Role" %>
<%@ page import="org.informatics.entity.enums.ShipmentStatus" %>

<%
  // ===== Logged-in user data from session =====
  String userEmail = (String) session.getAttribute("userEmail");
  String firstName = (String) session.getAttribute("firstName");
  String lastName = (String) session.getAttribute("lastName");
  Role userRole = (Role) session.getAttribute("userRole");

  // ===== Revenue report data provided by servlet =====
  List<Shipment> shipments = (List<Shipment>) request.getAttribute("shipments");
  Double totalRevenue = (Double) request.getAttribute("totalRevenue");
  String startDate = (String) request.getAttribute("startDate");
  String endDate = (String) request.getAttribute("endDate");

  // Optional feedback messages
  String success = request.getParameter("success");
  String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html lang="bg">
<head>
  <!-- Page metadata -->
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Справка за приходи - ALVAS Logistics</title>

  <!-- Global stylesheet -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<div class="container">

  <!-- ================= HEADER ================= -->
  <header>
    <div class="header-content">
      <!-- Logo / Home link -->
      <a href="${pageContext.request.contextPath}/" class="logo">ALVAS Logistics</a>

      <!-- Navigation menu -->
      <nav>
        <ul>
          <li><a href="${pageContext.request.contextPath}/">Начало</a></li>
          <li><a href="${pageContext.request.contextPath}/employee-shipments">Пратки</a></li>
          <li>
            <!-- Logged-in employee info -->
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

  <!-- ================= MAIN CONTENT ================= -->
  <main>
    <div class="page-header">
      <h1>💰 Справка за приходи</h1>
      <p>Изчисляване на приходи на компанията за определен период от време</p>
    </div>

    <!-- Success message (if present) -->
    <% if (success != null) { %>
    <div class="alert alert-success"><%= success %></div>
    <% } %>

    <!-- Error message (if present) -->
    <% if (error != null) { %>
    <div class="alert alert-error"><%= error %></div>
    <% } %>

    <!-- ================= DATE FILTER ================= -->
    <!-- Allows the user to select a date range for the report -->
    <div class="card" style="margin-bottom: 1.5rem;">
      <div style="padding: 1rem 1.25rem; border-bottom: 2px solid #e0e0e0; background: #f8f9fa;">
        <h3 style="margin: 0 0 0.75rem 0; font-size: 1rem; color: #333; font-weight: 600;">
          📅 Изберете период
        </h3>

        <form method="get" action="${pageContext.request.contextPath}/revenue-report">
          <div style="display: flex; gap: 1rem;">

            <!-- Start date -->
            <div style="flex: 1; display: flex; flex-direction: column;">
              <label for="startDate">Начална дата *</label>
              <input type="date"
                     id="startDate"
                     name="startDate"
                     value="<%= startDate != null ? startDate : "" %>"
                     required>
            </div>

            <!-- End date -->
            <div style="flex: 1; display: flex; flex-direction: column;">
              <label for="endDate">Крайна дата *</label>
              <input type="date"
                     id="endDate"
                     name="endDate"
                     value="<%= endDate != null ? endDate : "" %>"
                     required>
            </div>

            <!-- Submit button -->
            <div style="display: flex; flex-direction: column; justify-content: flex-end;">
              <button type="submit" class="btn btn-primary">
                📊 Генерирай справка
              </button>
            </div>

          </div>
        </form>
      </div>
    </div>

    <!-- ================= RESULTS ================= -->
    <!-- Render results only after a report has been generated -->
    <% if (totalRevenue != null) { %>

    <!-- Revenue summary -->
    <div class="card revenue-summary-card">
      <div class="revenue-summary-grid">

        <!-- Total revenue -->
        <div class="revenue-summary-item">
          <div class="revenue-summary-label">Общо приходи</div>
          <div class="revenue-summary-value green">
            <%= String.format("%.2f", totalRevenue) %>€
          </div>
        </div>

        <!-- Number of shipments -->
        <div class="revenue-summary-item">
          <div class="revenue-summary-label">Брой пратки</div>
          <div class="revenue-summary-value dark">
            <%= shipments != null ? shipments.size() : 0 %>
          </div>
        </div>

        <!-- Average price per shipment -->
        <div class="revenue-summary-item">
          <div class="revenue-summary-label">Средна цена</div>
          <div class="revenue-summary-value blue">
            <% if (shipments != null && !shipments.isEmpty()) { %>
            <%= String.format("%.2f", totalRevenue / shipments.size()) %>€
            <% } else { %>
            0.00€
            <% } %>
          </div>
        </div>

      </div>
    </div>

    <!-- ================= SHIPMENTS TABLE ================= -->
    <% if (shipments != null && !shipments.isEmpty()) { %>
    <div class="card">
      <div style="padding: 0.75rem 1rem; border-bottom: 2px solid #e0e0e0; background: #f8f9fa;">
        <strong>📋 Детайли на пратките за периода</strong>
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
            <th>Статус</th>
            <th>Дата</th>
          </tr>
          </thead>
          <tbody>

          <!-- Iterate over shipments -->
          <% for (Shipment s : shipments) { %>
          <tr>
            <td><%= s.getId() %></td>

            <!-- Sender -->
            <td>
              <%= s.getSender() != null && s.getSender().getUser() != null
                      ? s.getSender().getUser().getFirstName() + " " + s.getSender().getUser().getLastName()
                      : "N/A" %>
            </td>

            <!-- Receiver -->
            <td>
              <%= s.getReceiver() != null && s.getReceiver().getUser() != null
                      ? s.getReceiver().getUser().getFirstName() + " " + s.getReceiver().getUser().getLastName()
                      : "N/A" %>
            </td>

            <td><%= String.format("%.2f", s.getWeight()) %> kg</td>
            <td style="font-weight: bold;"><%= String.format("%.2f", s.getPrice()) %>€</td>

            <!-- Shipment status -->
            <td>
              <% if (s.getStatus() == ShipmentStatus.SENT) { %>
              <span class="status status-sent">📦 Изпратена</span>
              <% } else if (s.getStatus() == ShipmentStatus.RECEIVED) { %>
              <span class="status status-received">✅ Получена</span>
              <% } %>
            </td>

            <!-- Registration date -->
            <td><%= s.getRegistrationDate().toString().substring(0, 16).replace("T", " ") %></td>
          </tr>
          <% } %>

          </tbody>
        </table>
      </div>
    </div>

    <% } else { %>
    <!-- No shipments in selected period -->
    <div class="card">
      <div style="padding: 2rem; text-align: center;">
        <p>📭 Няма пратки за избрания период</p>
        <p>Изберете друг период за да видите резултати.</p>
      </div>
    </div>
    <% } %>

    <% } else { %>
    <!-- Initial state: no report generated yet -->
    <div class="card">
      <div style="padding: 3rem; text-align: center;">
        <h3>Изберете период за генериране на справка</h3>
        <p>Въведете начална и крайна дата за да видите приходите</p>
      </div>
    </div>
    <% } %>

    <!-- Back button -->
    <div style="margin-top: 1.5rem;">
      <a href="${pageContext.request.contextPath}/"
         class="btn btn-outline">← Обратно към началото</a>
    </div>
  </main>

  <footer>
    <p>&copy; 2025 ALVAS Logistics. Всички права запазени.</p>
  </footer>
</div>
</body>
</html>

