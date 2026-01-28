<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.informatics.entity.Shipment" %>
<%@ page import="org.informatics.entity.Office" %>
<%@ page import="org.informatics.entity.Employee" %>
<%@ page import="org.informatics.entity.enums.Role" %>
<%@ page import="org.informatics.entity.enums.ShipmentStatus" %>

<%-- Extract session and request attributes --%>
<%
    String userEmail = (String) session.getAttribute("userEmail");
    String firstName = (String) session.getAttribute("firstName");
    String lastName = (String) session.getAttribute("lastName");
    Role userRole = (Role) session.getAttribute("userRole");

    List<Shipment> shipments = (List<Shipment>) request.getAttribute("shipments");
    List<Office> offices = (List<Office>) request.getAttribute("offices");
    List<Employee> employees = (List<Employee>) request.getAttribute("employees");
    String selectedEmployeeId = (String) request.getAttribute("selectedEmployeeId");
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

    <%-- HEADER / NAVIGATION --%>
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

        <%-- PAGE TITLE --%>
        <div class="page-header">
            <h1>📦 Всички пратки в системата</h1>
            <p>Като служител можете да виждате всички пратки регистрирани в системата</p>
        </div>

        <%-- SUCCESS MESSAGE --%>
        <% if (success != null) { %>
        <div class="alert alert-success"><%= success %></div>
        <% } %>

        <%-- ERROR MESSAGE --%>
        <% if (error != null) { %>
        <div class="alert alert-error"><%= error %></div>
        <% } %>

        <div class="card">

            <%-- FILTER SECTION (BY EMPLOYEE) --%>
            <div style="padding: 0.75rem 1rem; border-bottom: 2px solid #e0e0e0; background: #f8f9fa;">
                <form method="get"
                      action="${pageContext.request.contextPath}/employee-shipments"
                      style="display: grid; grid-template-columns: auto 1fr auto; gap: 0.75rem; align-items: center;">

                    <%-- Filter label --%>
                    <label for="filterEmployee" style="font-weight: 500; color: #333; display: flex; align-items: center; gap: 0.4rem; margin: 0; font-size: 0.95rem;">
                        🔍 <span>Филтрирай:</span>
                    </label>

                    <%-- Employee dropdown filter --%>
                    <select id="filterEmployee"
                            name="filterEmployeeId"
                            onchange="this.form.submit()"
                            style="width: 320px; justify-self: center; padding: 0.45rem 0.7rem; border: 1px solid #d0d0d0; border-radius: 5px; font-size: 0.9rem; background: white;">
                        <option value="all">Всички служители</option>
                        <% if (employees != null) {
                            for (Employee emp : employees) {
                                String empName = emp.getUser() != null ? emp.getUser().getFirstName() + " " + emp.getUser().getLastName() : "N/A";
                                String empEmoji = "";
                                if (emp.getEmployeeType() != null) {
                                    empEmoji = emp.getEmployeeType().toString().equals("COURIER") ? "🚚 " : "🏢 ";
                                }
                                boolean isSelected = selectedEmployeeId != null && selectedEmployeeId.equals(String.valueOf(emp.getId()));
                        %>
                        <option value="<%= emp.getId() %>" <%= isSelected ? "selected" : "" %>><%= empEmoji + empName %></option>
                        <%  }
                        } %>
                    </select>

                    <%-- Clear filter + shipment count --%>
                    <div style="display: flex; gap: 0.5rem; align-items: center; justify-self: end;">
                        <% if (selectedEmployeeId != null && !"all".equals(selectedEmployeeId)) { %>
                        <button type="button"
                                onclick="window.location.href='${pageContext.request.contextPath}/employee-shipments'"
                                class="btn btn-outline"
                                style="padding: 0.45rem 0.8rem; font-size: 0.85rem;">
                            ✕ Изчисти
                        </button>
                        <% } %>

                        <div style="padding: 0.35rem 0.7rem; background: white; border: 1px solid #d0d0d0; border-radius: 5px; color: #555; font-size: 0.9rem; white-space: nowrap;">
                            📊 <strong><%= shipments != null ? shipments.size() : 0 %></strong>
                            <%= (shipments != null && shipments.size() == 1) ? "пратка" : "пратки" %>
                        </div>
                    </div>
                </form>
            </div>

            <%-- SHIPMENTS TABLE --%>
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
                        <th>Статус</th>
                        <th>Дата</th>
                        <th>ДЕЙСТВИЯ</th>
                    </tr>
                    </thead>

                    <tbody>
                    <%-- Render shipments --%>
                    <% if (shipments != null && !shipments.isEmpty()) { %>
                    <% for (Shipment s : shipments) { %>
                    <tr>
                        <td><%= s.getId() %></td>

                        <%-- Sender --%>
                        <td>
                            <%= s.getSender() != null
                                    ? (s.getSender().getUser() != null
                                    ? s.getSender().getUser().getFirstName() + " " + s.getSender().getUser().getLastName()
                                    : "N/A")
                                    : "Изтрит клиент" %>
                        </td>

                        <%-- Receiver --%>
                        <td>
                            <%= s.getReceiver() != null
                                    ? (s.getReceiver().getUser() != null
                                    ? s.getReceiver().getUser().getFirstName() + " " + s.getReceiver().getUser().getLastName()
                                    : "N/A")
                                    : "Изтрит клиент" %>
                        </td>

                        <td><%= String.format("%.2f", s.getWeight()) %> kg</td>
                        <td><%= String.format("%.2f", s.getPrice()) %>€</td>

                        <%-- Delivery info --%>
                        <td>
                            <%= s.getDeliveryToOffice()
                                    ? (s.getDeliveryOffice() != null ? "📍 " + s.getDeliveryOffice().getAddress() : "Офис изтрит")
                                    : "🏠 " + (s.getDeliveryAddress() != null ? s.getDeliveryAddress() : "N/A") %>
                        </td>

                        <%-- Shipment status --%>
                        <td>
                            <% if (s.getStatus() == ShipmentStatus.SENT) { %>
                            <span class="status status-sent">📦 Изпратена</span>
                            <% } else if (s.getStatus() == ShipmentStatus.RECEIVED) { %>
                            <span class="status status-received">✅ Получена</span>
                            <% } %>
                        </td>

                        <td><%= s.getRegistrationDate().toString().substring(0, 16).replace("T", " ") %></td>

                        <%-- ACTION BUTTONS --%>
                        <td>
                            <div class="action-buttons">

                                <%-- Edit shipment (opens modal) --%>
                                <button onclick="openEditModal(<%= s.getId() %>, <%= s.getWeight() %>, '<%= s.getDeliveryToOffice() %>', <%= s.getDeliveryOffice() != null ? s.getDeliveryOffice().getId() : "null" %>, '<%= s.getDeliveryAddress() != null ? s.getDeliveryAddress().replace("'", "\\'") : "" %>')"
                                        class="btn btn-primary">
                                    🖊️ Редактирай
                                </button>

                                <%-- Mark as received (only if SENT) --%>
                                <% if (s.getStatus() == ShipmentStatus.SENT) { %>
                                <form action="${pageContext.request.contextPath}/employee-shipments"
                                      method="post"
                                      style="margin: 0;"
                                      onsubmit="return confirm('Маркирай пратката като получена?');">
                                    <input type="hidden" name="action" value="markReceived">
                                    <input type="hidden" name="id" value="<%= s.getId() %>">
                                    <button type="submit" class="btn btn-success">
                                        ✅ Получена
                                    </button>
                                </form>
                                <% } %>

                                <%-- Delete shipment --%>
                                <form action="${pageContext.request.contextPath}/employee-shipments"
                                      method="get"
                                      style="margin: 0;"
                                      onsubmit="return confirm('Сигурни ли сте, че искате да изтриете тази пратка?');">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="<%= s.getId() %>">
                                    <button type="submit" class="btn btn-danger">
                                        🗑️ Изтрий
                                    </button>
                                </form>

                            </div>
                        </td>
                    </tr>
                    <% } %>
                    <% } else { %>

                    <%-- No shipments case --%>
                    <tr>
                        <td colspan="9" class="text-center">
                            <p>Няма регистрирани пратки в системата.</p>
                            <a href="${pageContext.request.contextPath}/shipment-register"
                               class="btn btn-success"
                               style="margin-top: 1rem;">
                                ➕ Регистрирай първа пратка
                            </a>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>

        <%-- ACTION LINKS --%>
        <div style="margin-top: 1.5rem;">
            <a href="${pageContext.request.contextPath}/shipment-register" class="btn btn-success">➕ Регистрирай нова пратка</a>
            <a href="${pageContext.request.contextPath}/undelivered-shipments" class="btn" style="background: #ff9800; color: white; border: 2px solid #f57c00;">⚠️ Неполучени пратки</a>
            <a href="${pageContext.request.contextPath}/revenue-report" class="btn" style="background: #28a745; color: white; border: 2px solid #1e7e34;">💰 Справка за приходи</a>
            <a href="${pageContext.request.contextPath}/" class="btn btn-outline">← Обратно към началото</a>
        </div>
    </main>

    <%-- FOOTER --%>
    <footer>
        <p>&copy; 2025 ALVAS Logistics. Всички права запазени.</p>
    </footer>
</div>

<!-- EDIT SHIPMENT MODAL -->
<div id="shipmentModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2>Редактирай пратка</h2>
            <span class="close" onclick="closeModal()">&times;</span>
        </div>

        <%-- Edit shipment form --%>
        <form action="${pageContext.request.contextPath}/employee-shipments" method="post">
            <input type="hidden" name="action" value="edit">
            <input type="hidden" name="id" id="shipmentId">

            <div class="form-group">
                <label for="weight">Тегло (kg) *</label>
                <input type="number" id="weight" name="weight" step="0.01" min="0.01" required>
            </div>

            <div class="form-group">
                <label for="deliveryType">Начин на доставка *</label>
                <select id="deliveryType" name="deliveryType" onchange="toggleDeliveryFields()" required>
                    <option value="office">До офис</option>
                    <option value="address">До адрес</option>
                </select>
            </div>

            <%-- Office delivery fields --%>
            <div class="form-group" id="officeGroup">
                <label for="officeId">Офис за доставка *</label>
                <select id="officeId" name="officeId">
                    <option value="">-- Изберете офис --</option>
                    <% if (offices != null) {
                        for (Office off : offices) { %>
                    <option value="<%= off.getId() %>"><%= off.getAddress() %></option>
                    <%  }
                    } %>
                </select>
            </div>

            <%-- Address delivery fields --%>
            <div class="form-group" id="addressGroup" style="display: none;">
                <label for="deliveryAddress">Адрес за доставка *</label>
                <input type="text" id="deliveryAddress" name="deliveryAddress" maxlength="500">
            </div>

            <div class="modal-actions">
                <button type="button" onclick="closeModal()" class="btn btn-outline">Откажи</button>
                <button type="submit" class="btn btn-success">Запази</button>
            </div>
        </form>
    </div>
</div>

<script>
    // Populate modal with shipment data
    function openEditModal(id, weight, deliveryToOffice, officeId, deliveryAddress) {
        document.getElementById('shipmentId').value = id;
        document.getElementById('weight').value = weight;

        const deliveryType = deliveryToOffice === 'true' ? 'office' : 'address';
        document.getElementById('deliveryType').value = deliveryType;

        if (deliveryType === 'office') {
            document.getElementById('officeGroup').style.display = 'block';
            document.getElementById('addressGroup').style.display = 'none';
            document.getElementById('officeId').value = officeId || '';
            document.getElementById('officeId').required = true;
            document.getElementById('deliveryAddress').required = false;
        } else {
            document.getElementById('officeGroup').style.display = 'none';
            document.getElementById('addressGroup').style.display = 'block';
            document.getElementById('deliveryAddress').value = deliveryAddress || '';
            document.getElementById('officeId').required = false;
            document.getElementById('deliveryAddress').required = true;
        }

        document.getElementById('shipmentModal').style.display = 'flex';
    }

    // Toggle delivery fields when delivery type changes
    function toggleDeliveryFields() {
        const deliveryType = document.getElementById('deliveryType').value;
        const officeGroup = document.getElementById('officeGroup');
        const addressGroup = document.getElementById('addressGroup');
        const officeSelect = document.getElementById('officeId');
        const addressInput = document.getElementById('deliveryAddress');

        if (deliveryType === 'office') {
            officeGroup.style.display = 'block';
            addressGroup.style.display = 'none';
            officeSelect.required = true;
            addressInput.required = false;
            addressInput.value = '';
        } else {
            officeGroup.style.display = 'none';
            addressGroup.style.display = 'block';
            officeSelect.required = false;
            addressInput.required = true;
            officeSelect.value = '';
        }
    }

    // Close modal
    function closeModal() {
        document.getElementById('shipmentModal').style.display = 'none';
    }

    // Close modal when clicking outside
    window.onclick = function(event) {
        const modal = document.getElementById('shipmentModal');
        if (event.target == modal) {
            closeModal();
        }
    }
</script>

</body>
</html>
