<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.informatics.entity.Client" %>
<%@ page import="org.informatics.entity.Company" %>
<%@ page import="org.informatics.entity.enums.Role" %>
<%
    String userEmail = (String) session.getAttribute("userEmail");
    String firstName = (String) session.getAttribute("firstName");
    String lastName = (String) session.getAttribute("lastName");
    Role userRole = (Role) session.getAttribute("userRole");

    List<Client> clients = (List<Client>) request.getAttribute("clients");
    List<Company> companies = (List<Company>) request.getAttribute("companies");
    String success = request.getParameter("success");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="bg">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Клиенти - ALVAS Logistics</title>
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
            <h1>👥 Управление на клиенти</h1>
            <p>Преглед на всички регистрирани клиенти в системата</p>
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
                        <th>Име</th>
                        <th>Имейл</th>
                        <th>Компания</th>
                        <th>Дата на регистрация</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% if (clients != null && !clients.isEmpty()) { %>
                    <% for (Client c : clients) { %>
                    <tr>
                        <td><%= c.getId() %></td>
                        <td>
                            <%= c.getUser() != null
                                    ? c.getUser().getFirstName() + " " + c.getUser().getLastName()
                                    : "N/A" %>
                        </td>
                        <td><%= c.getUser() != null ? c.getUser().getEmail() : "N/A" %></td>
                        <td><%= c.getCompany() != null ? c.getCompany().getName() : "Без компания" %></td>
                        <td>
                            <div class="action-buttons">
                                <button onclick="openEditModal(<%= c.getId() %>, <%= c.getCompany() != null ? c.getCompany().getId() : "null" %>)"
                                        class="btn btn-primary">
                                    🖊️ Редактирай
                                </button>

                                <form action="${pageContext.request.contextPath}/clients"
                                      method="get"
                                      onsubmit="return confirm('Сигурни ли сте, че искате да изтриете клиента <%= c.getUser() != null ? c.getUser().getFirstName() + " " + c.getUser().getLastName() : "" %>?\n\nВНИМАНИЕ: Това може да повлияе на пратките свързани с този клиент!');">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="<%= c.getId() %>">
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
                        <td colspan="5" class="text-center">Няма регистрирани клиенти.</td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>

        <a href="${pageContext.request.contextPath}/" class="btn btn-outline">← Обратно към началото</a>
    </main>

    <!-- EDIT MODAL -->
    <div id="clientModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h2>Редактирай клиент</h2>
                <span class="close" onclick="closeModal()">&times;</span>
            </div>
            <form action="${pageContext.request.contextPath}/clients" method="post">
                <input type="hidden" name="id" id="clientId">

                <div class="form-group">
                    <label for="companyId">Компания</label>
                    <select id="companyId" name="companyId">
                        <option value="">Без компания</option>
                        <% if (companies != null) {
                            for (Company comp : companies) { %>
                        <option value="<%= comp.getId() %>"><%= comp.getName() %></option>
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

    <footer>
        <p>&copy; 2025 ALVAS Logistics. Всички права запазени.</p>
    </footer>

    <script>
        function openEditModal(clientId, companyId) {
            document.getElementById('clientId').value = clientId;
            document.getElementById('companyId').value = companyId || '';
            document.getElementById('clientModal').style.display = 'flex';
        }

        function closeModal() {
            document.getElementById('clientModal').style.display = 'none';
        }

        window.onclick = function(event) {
            const modal = document.getElementById('clientModal');
            if (event.target == modal) {
                closeModal();
            }
        }
    </script>
</div>
</body>
</html>
