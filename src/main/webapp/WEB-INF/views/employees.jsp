<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.informatics.entity.Employee" %>
<%@ page import="org.informatics.entity.Company" %>
<%@ page import="org.informatics.entity.Office" %>
<%@ page import="org.informatics.entity.enums.Role" %>
<%
    String userEmail = (String) session.getAttribute("userEmail");
    String firstName = (String) session.getAttribute("firstName");
    String lastName = (String) session.getAttribute("lastName");
    Role userRole = (Role) session.getAttribute("userRole");

    List<Employee> employees = (List<Employee>) request.getAttribute("employees");
    List<Company> companies = (List<Company>) request.getAttribute("companies");
    List<Office> offices = (List<Office>) request.getAttribute("offices");
    String success = request.getParameter("success");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="bg">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Служители - ALVAS Logistics</title>
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
            <h1>👔 Управление на служители</h1>
            <p>Преглед на всички служители в системата</p>
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
                        <th>ИМЕ</th>
                        <th>EMAIL</th>
                        <th>ТИП</th>
                        <th>КОМПАНИЯ</th>
                        <th>ОФИС</th>
                        <th>ДАТА НА РЕГИСТРАЦИЯ</th>
                        <th>ДЕЙСТВИЯ</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% if (employees != null && !employees.isEmpty()) { %>
                    <% for (Employee e : employees) { %>
                    <tr>
                        <td><%= e.getId() %></td>
                        <td>
                            <%= e.getUser() != null
                                    ? e.getUser().getFirstName() + " " + e.getUser().getLastName()
                                    : "N/A" %>
                        </td>
                        <td><%= e.getUser() != null ? e.getUser().getEmail() : "N/A" %></td>
                        <td>
                            <% if (e.getEmployeeType() != null) { %>
                            <% if (e.getEmployeeType().toString().equals("COURIER")) { %>
                            <span class="badge badge-courier">🚚 Куриер</span>
                            <% } else if (e.getEmployeeType().toString().equals("OFFICE_EMPLOYEE")) { %>
                            <span class="badge badge-office">🏢 Офис служител</span>
                            <% } else { %>
                            <%= e.getEmployeeType() %>
                            <% } %>
                            <% } else { %>
                            <span class="badge badge-unknown">❓ Неизвестен</span>
                            <% } %>
                        </td>
                        <td><%= e.getCompany() != null ? e.getCompany().getName() : "Без компания" %></td>
                        <td><%= e.getOffice() != null ? e.getOffice().getAddress() : "Без офис" %></td>
                        <td><%= e.getUser() != null ? e.getUser().getCreatedAt().toString().substring(0, 16).replace("T", " ") : "N/A" %></td>
                        <td>
                            <div class="action-buttons">
                                <button onclick="openEditModal(<%= e.getId() %>, <%= e.getCompany() != null ? e.getCompany().getId() : "null" %>, <%= e.getOffice() != null ? e.getOffice().getId() : "null" %>, '<%= e.getEmployeeType() != null ? e.getEmployeeType() : "" %>')"
                                        class="btn btn-primary">
                                    🖊️ Редактирай
                                </button>

                                <form action="${pageContext.request.contextPath}/employees"
                                      method="get"
                                      onsubmit="return confirm('Сигурни ли сте, че искате да изтриете служителя <%= e.getUser() != null ? e.getUser().getFirstName() + " " + e.getUser().getLastName() : "" %>?\n\nВНИМАНИЕ: Това може да повлияе на пратките регистрирани от този служител!');">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="<%= e.getId() %>">
                                    <button type="submit" class="btn btn-danger">
                                        🗑️ Изтрий
                                    </button>
                                </form>
                            </div>
                        </td>
                    </tr>
                    <% } %>
                    <% } else { %>
                    <tr>
                        <td colspan="8" class="text-center">Няма регистрирани служители.</td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>

        <a href="${pageContext.request.contextPath}/" class="btn btn-outline">← Обратно към началото</a>
    </main>

    <footer>
        <p>&copy; 2025 ALVAS Logistics. Всички права запазени.</p>
    </footer>

    <!-- EDIT MODAL -->
    <div id="employeeModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h2>Редактирай служител</h2>
                <span class="close" onclick="closeModal()">&times;</span>
            </div>
            <form action="${pageContext.request.contextPath}/employees" method="post">
                <input type="hidden" name="id" id="employeeId">

                <div class="form-group">
                    <label for="employeeType">Тип служител *</label>
                    <select id="employeeType" name="employeeType" required>
                        <option value="">-- Изберете тип --</option>
                        <option value="OFFICE_EMPLOYEE">🏢 Офис служител</option>
                        <option value="COURIER">🚚 Куриер</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="companyId">Компания</label>
                    <select id="companyId" name="companyId" onchange="loadOffices()">
                        <option value="">Без компания</option>
                        <% if (companies != null) {
                            for (Company comp : companies) { %>
                        <option value="<%= comp.getId() %>"><%= comp.getName() %></option>
                        <%  }
                        } %>
                    </select>
                </div>

                <div class="form-group">
                    <label for="officeId">Офис</label>
                    <select id="officeId" name="officeId">
                        <option value="">Без офис</option>
                        <% if (offices != null) {
                            for (Office off : offices) { %>
                        <option value="<%= off.getId() %>" data-company="<%= off.getCompany() != null ? off.getCompany().getId() : "" %>">
                            <%= off.getAddress() %>
                        </option>
                        <%  }
                        } %>
                    </select>
                </div>

                <div class="modal-actions">
                    <button type="button" onclick="closeModal()" class="btn btn-outline">Откажи</button>
                    <button type="submit" class="btn btn-success">Запази</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        function openEditModal(employeeId, companyId, officeId, employeeType) {
            document.getElementById('employeeId').value = employeeId;
            document.getElementById('companyId').value = companyId || '';
            document.getElementById('employeeType').value = employeeType || '';

            // Filter offices by company
            loadOffices();

            document.getElementById('officeId').value = officeId || '';
            document.getElementById('employeeModal').style.display = 'flex';
        }

        function loadOffices() {
            const companyId = document.getElementById('companyId').value;
            const officeSelect = document.getElementById('officeId');
            const options = officeSelect.getElementsByTagName('option');

            for (let i = 0; i < options.length; i++) {
                const option = options[i];
                if (option.value === '') {
                    option.style.display = 'block';
                } else {
                    const optionCompany = option.getAttribute('data-company');
                    if (!companyId || optionCompany === companyId) {
                        option.style.display = 'block';
                    } else {
                        option.style.display = 'none';
                    }
                }
            }

            // Reset selection if current office doesn't match company
            const selectedOption = officeSelect.options[officeSelect.selectedIndex];
            if (selectedOption && selectedOption.getAttribute('data-company') !== companyId && companyId !== '') {
                officeSelect.value = '';
            }
        }

        function closeModal() {
            document.getElementById('employeeModal').style.display = 'none';
        }

        // Close modal when clicking outside
        window.onclick = function(event) {
            const modal = document.getElementById('employeeModal');
            if (event.target == modal) {
                closeModal();
            }
        }
    </script>
</div>
</body>
</html>