<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="bg">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Начало - ALVAS Logistics</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<%
    String email = (String) session.getAttribute("email");
    String role = (String) session.getAttribute("role");
    boolean isLoggedIn = email != null;
    boolean isEmployee = "EMPLOYEE".equals(role);
    boolean isClient = "CLIENT".equals(role);
%>

<div class="container">
    <!-- Header -->
    <header>
        <div class="header-content">
            <a href="${pageContext.request.contextPath}/" class="logo">
                ALVAS Logistics
            </a>

            <nav>
                <ul>
                    <% if (!isLoggedIn) { %>
                    <li><a href="${pageContext.request.contextPath}/register">Регистрация</a></li>
                    <li><a href="${pageContext.request.contextPath}/login">Вход</a></li>
                    <% } else { %>
                    <li><a href="${pageContext.request.contextPath}/logout">Изход</a></li>
                    <% } %>
                </ul>
            </nav>
        </div>
    </header>

    <!-- Main Content -->
    <main class="fade-in">
        <h1>Добре дошли в ALVAS Logistics</h1>

        <% if (!isLoggedIn) { %>
        <!-- Not logged in -->
        <div class="card">
            <div class="card-body">
                <h3>За нас</h3>
                <p>
                    ALVAS Logistics е водеща компания в областта на логистиката и доставките.
                    Ние предлагаме бързи и сигурни услуги за изпращане и получаване на пратки.
                </p>
                <p>
                    <strong>За да използвате системата, моля влезте във вашия акаунт или се регистрирайте.</strong>
                </p>
            </div>
            <div class="card-footer">
                <a href="${pageContext.request.contextPath}/register" class="btn-primary">Регистрация</a>
                <a href="${pageContext.request.contextPath}/login" class="btn-outline">Вход</a>
            </div>
        </div>
        <% } else { %>
        <!-- Logged in -->
        <div class="card">
            <div class="card-body">
                <div class="user-info">
                    <span>👤 <%= email %></span>
                    <span class="user-role"><%= isEmployee ? "Служител" : "Клиент" %></span>
                </div>
            </div>
        </div>

        <% if (isEmployee) { %>
        <!-- Employee Menu -->
        <h2>Меню за служители</h2>

        <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(280px, 1fr)); gap: 1.5rem;">
            <div class="card">
                <div class="card-header">📦 Пратки</div>
                <div class="card-body">
                    <p>Преглед и управление на всички пратки в системата.</p>
                </div>
                <div class="card-footer">
                    <a href="${pageContext.request.contextPath}/employee-shipments" class="btn-primary">
                        Виж всички пратки
                    </a>
                </div>
            </div>

            <div class="card">
                <div class="card-header">📊 Справки</div>
                <div class="card-body">
                    <p>Генериране на различни видове справки и отчети.</p>
                </div>
                <div class="card-footer">
                    <a href="${pageContext.request.contextPath}/reports" class="btn-secondary">
                        Виж справки
                    </a>
                </div>
            </div>

            <div class="card">
                <div class="card-header">👥 Клиенти</div>
                <div class="card-body">
                    <p>Управление на клиентите на компанията.</p>
                </div>
                <div class="card-footer">
                    <a href="${pageContext.request.contextPath}/clients" class="btn-secondary">
                        Управление
                    </a>
                </div>
            </div>

            <div class="card">
                <div class="card-header">👨‍💼 Служители</div>
                <div class="card-body">
                    <p>Преглед и управление на служителите.</p>
                </div>
                <div class="card-footer">
                    <a href="${pageContext.request.contextPath}/employees" class="btn-secondary">
                        Управление
                    </a>
                </div>
            </div>

            <div class="card">
                <div class="card-header">🏢 Компании</div>
                <div class="card-body">
                    <p>Информация за логистичните компании.</p>
                </div>
                <div class="card-footer">
                    <a href="${pageContext.request.contextPath}/companies" class="btn-secondary">
                        Управление
                    </a>
                </div>
            </div>

            <div class="card">
                <div class="card-header">🏪 Офиси</div>
                <div class="card-body">
                    <p>Управление на офисите на компанията.</p>
                </div>
                <div class="card-footer">
                    <a href="${pageContext.request.contextPath}/offices" class="btn-secondary">
                        Управление
                    </a>
                </div>
            </div>

            <div class="card">
                <div class="card-header">📦 Управление на пратки</div>
                <div class="card-body">
                    <p>CRUD операции за пратки.</p>
                </div>
                <div class="card-footer">
                    <a href="${pageContext.request.contextPath}/shipments" class="btn-secondary">
                        Управление
                    </a>
                </div>
            </div>

            <div class="card">
                <div class="card-header">✍️ Регистриране на пратки</div>
                <div class="card-body">
                    <p>Регистрирай нова пратка или маркирай като получена.</p>
                </div>
                <div class="card-footer">
                    <a href="${pageContext.request.contextPath}/shipment-register" class="btn-success">
                        Регистрирай пратка
                    </a>
                </div>
            </div>
        </div>

        <% } else if (isClient) { %>
        <!-- Client Menu -->
        <h2>Моите пратки</h2>

        <div class="card">
            <div class="card-body">
                <h3>📦 Преглед на моите пратки</h3>
                <p>Вижте всички пратки, които сте изпратили или получили.</p>
            </div>
            <div class="card-footer">
                <a href="${pageContext.request.contextPath}/client-shipments" class="btn-primary">
                    Виж моите пратки
                </a>
            </div>
        </div>

        <div class="alert alert-info">
            <strong>ℹ️ Информация:</strong> Като клиент можете да виждате само вашите собствени пратки.
            За повече информация се свържете с наш служител.
        </div>
        <% } %>
        <% } %>
    </main>

    <!-- Footer -->
    <footer style="text-align: center; padding: 2rem 0; color: var(--text-muted);">
        <p>&copy; 2025 ALVAS Logistics. Всички права запазени.</p>
    </footer>
</div>
</body>
</html>
