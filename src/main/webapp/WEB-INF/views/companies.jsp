<%@ page contentType="text/html; charset=UTF-8" %>

<%-- Imports used inside JSP scriptlets --%>
<%@ page import="java.util.List" %>
<%@ page import="org.informatics.entity.Company" %>
<%@ page import="org.informatics.entity.enums.Role" %>

<%
    // =========================
    // SESSION DATA
    // =========================
    String userEmail = (String) session.getAttribute("userEmail");
    String firstName = (String) session.getAttribute("firstName");
    String lastName = (String) session.getAttribute("lastName");
    Role userRole = (Role) session.getAttribute("userRole");

    // =========================
    // REQUEST DATA
    // =========================
    List<Company> companies = (List<Company>) request.getAttribute("companies");
    Company editCompany = (Company) request.getAttribute("editCompany");

    // Messages
    String success = request.getParameter("success");
    String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html lang="bg">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Компании - ALVAS Logistics</title>

    <%-- Main stylesheet --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<div class="container">

    <%-- =========================
         HEADER / NAVIGATION
         ========================= --%>
    <header>
        <div class="header-content">
            <a href="${pageContext.request.contextPath}/" class="logo">ALVAS Logistics</a>

            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/">Начало</a></li>
                    <li><a href="${pageContext.request.contextPath}/employee-dashboard">Пратки</a></li>

                    <%-- Logged-in employee info --%>
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

        <%-- Page header with add-company action --%>
        <div class="page-header">
            <div>
                <h1>🏢 Управление на компании</h1>
            </div>
            <button onclick="openAddModal()" class="btn btn-success">
                ➕ Добави компания
            </button>
        </div>

        <%-- Success message (after redirect) --%>
        <% if (success != null) { %>
            <div class="alert alert-success"><%= success %></div>
        <% } %>

        <%-- Error message (forwarded) --%>
        <% if (error != null) { %>
            <div class="alert alert-error"><%= error %></div>
        <% } %>

        <%-- =========================
             COMPANIES TABLE
             ========================= --%>
        <div class="card">
            <div class="table-container">
                <table>

                    <%-- Table header --%>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>ИМЕ НА КОМПАНИЯТА</th>
                        <th>ДЕЙСТВИЯ</th>
                    </tr>
                    </thead>

                    <tbody>
                    <%-- If companies exist --%>
                    <% if (companies != null && !companies.isEmpty()) { %>

                        <%-- Loop through all companies --%>
                        <% for (Company c : companies) { %>
                        <tr>
                            <td><%= c.getId() %></td>

                            <%-- Company name --%>
                            <td><%= c.getName() %></td>

                            <%-- Action buttons: edit / delete --%>
                            <td>
                                <div class="action-buttons">

                                    <%-- Open modal for editing company --%>
                                    <button
                                        onclick="openEditModal(
                                                <%= c.getId() %>,
                                                '<%= c.getName().replace("'", "\\'") %>'
                                        )"
                                        class="btn btn-primary">
                                        🖊️ Редактирай
                                    </button>

                                        <%-- Delete company (GET with confirmation) --%>
                                        <%
                                            String companyName = c.getName().replace("'", "\\'");
                                        %>
                                        <form action="${pageContext.request.contextPath}/companies"
                                              method="get"
                                              onsubmit="return confirm('Сигурни ли сте, че искате да изтриете <%= companyName %>?');">
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

                    <%-- No companies case --%>
                    <% } else { %>
                        <tr>
                            <td colspan="3" class="text-center">
                                Няма добавени компании.
                            </td>
                        </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>

        <%-- Back button --%>
        <div style="margin-top: 1.5rem;">
            <a href="${pageContext.request.contextPath}/employee-dashboard"
               class="btn btn-outline">
                ← Обратно към началото
            </a>
        </div>
    </main>

    <%-- Footer --%>
    <footer>
        <p>&copy; 2025 ALVAS Logistics. Всички права запазени.</p>
    </footer>
</div>

<!-- =========================
     ADD / EDIT COMPANY MODAL
     ========================= -->
<div id="companyModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2 id="modalTitle">Добави компания</h2>
            <span class="close" onclick="closeModal()">&times;</span>
        </div>

        <%-- Form used for both add and edit operations --%>
        <form action="${pageContext.request.contextPath}/companies" method="post">
            <input type="hidden" name="id" id="companyId">

            <div class="form-group">
                <label for="name">Име на компанията *</label>
                <input type="text" id="name" name="name" required>
            </div>

            <div class="modal-actions">
                <button type="button"
                        onclick="closeModal()"
                        class="btn btn-outline">Откажи</button>
                <button type="submit"
                        class="btn btn-success">Запази</button>
            </div>
        </form>
    </div>
</div>

<%-- =========================
     MODAL JAVASCRIPT LOGIC
     ========================= --%>
<script>
    // Open modal for adding a new company
    function openAddModal() {
        document.getElementById('modalTitle').textContent = 'Добави компания';
        document.getElementById('companyId').value = '';
        document.getElementById('name').value = '';
        document.getElementById('companyModal').style.display = 'flex';
    }

    // Open modal for editing an existing company
    function openEditModal(id, name) {
        document.getElementById('modalTitle').textContent = 'Редактирай компания';
        document.getElementById('companyId').value = id;
        document.getElementById('name').value = name;
        document.getElementById('companyModal').style.display = 'flex';
    }

    // Close modal window
    function closeModal() {
        document.getElementById('companyModal').style.display = 'none';
    }

    // Close modal when clicking outside of it
    window.onclick = function(event) {
        const modal = document.getElementById('companyModal');
        if (event.target == modal) {
            closeModal();
        }
    }
</script>
</body>
</html>
