<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="org.informatics.entity.enums.Role" %>

<%
    // Session data for logged-in user
    String userEmail = (String) session.getAttribute("userEmail");
    String firstName = (String) session.getAttribute("firstName");
    String lastName = (String) session.getAttribute("lastName");
    Role userRole = (Role) session.getAttribute("userRole");

    // Determines whether a user is logged in
    boolean isLoggedIn = (userEmail != null && userRole != null);
%>

<!DOCTYPE html>
<html lang="bg">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ALVAS Logistics - Начало</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>

<body>
<div class="container">

    <!-- ================= HEADER ================= -->
    <header>
        <div class="header-content">
            <a href="${pageContext.request.contextPath}/" class="logo">
                ALVAS Logistics
            </a>

            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/">Начало</a></li>

                    <!-- Navigation for guests -->
                    <% if (!isLoggedIn) { %>
                        <li><a href="${pageContext.request.contextPath}/login">Вход</a></li>
                        <li><a href="${pageContext.request.contextPath}/register">Регистрация</a></li>
                    <% } else { %>

                        <!-- Navigation for clients -->
                        <% if (userRole == Role.CLIENT) { %>
                            <li><a href="${pageContext.request.contextPath}/client-shipments">Моите пратки</a></li>

                        <!-- Navigation for employees -->
                        <% } else if (userRole == Role.EMPLOYEE) { %>
                            <li><a href="${pageContext.request.contextPath}/employee-shipments">Пратки</a></li>
                        <% } %>

                        <!-- Logged-in user info -->
                        <li>
                            <div class="user-info">
                                👤 <%= firstName != null ? firstName + " " + lastName : userEmail %>
                                <span class="user-role">
                                    <%= userRole == Role.CLIENT ? "КЛИЕНТ" : "СЛУЖИТЕЛ" %>
                                </span>
                            </div>
                        </li>

                        <li><a href="${pageContext.request.contextPath}/logout">Изход</a></li>
                    <% } %>
                </ul>
            </nav>
        </div>
    </header>

    <!-- ================= MAIN CONTENT ================= -->
    <main class="fade-in">
        <h1>Добре дошли в ALVAS Logistics</h1>

        <!-- About section -->
        <section class="card">
            <h3>За нас</h3>
            <p>
                ALVAS Logistics е водеща компания в областта на логистиката и доставките.
                Ние предлагаме бързи и сигурни услуги за изпращане и получаване на пратки.
            </p>

            <!-- Services -->
            <h3 style="margin-top: 2rem;">Нашите услуги</h3>
            <ul style="list-style: none; padding: 0;">
                <li style="padding: 0.5rem 0;">✅ Експресни доставки в цялата страна</li>
                <li style="padding: 0.5rem 0;">✅ Проследяване на пратки в реално време</li>
                <li style="padding: 0.5rem 0;">✅ Сигурно съхранение и транспорт</li>
                <li style="padding: 0.5rem 0;">✅ Професионално обслужване 24/7</li>
                <li style="padding: 0.5rem 0;">✅ Конкурентни цени и отстъпки</li>
            </ul>

            <!-- Why choose us -->
            <h3 style="margin-top: 2rem;">Защо да изберете нас?</h3>
            <p>
                С над 10 години опит в логистиката, ние сме изградили репутация на надежден партньор.
                Нашата мисия е да предоставим най-доброто качество на услуга при най-добри условия.
            </p>

            <!-- Message for guests -->
            <% if (!isLoggedIn) { %>
            <div style="margin-top: 2rem; padding: 1.5rem; background: linear-gradient(135deg, #f0f9ff 0%, #e0f2fe 100%); border-radius: 0.75rem; border: 1px solid #bae6fd;">
                <p style="margin: 0; color: #0c4a6e; font-weight: 600;">
                    💡 За да използвате системата, моля влезте във вашия акаунт или се регистрирайте.
                </p>
            </div>

            <!-- Message for clients -->
            <% } else if (userRole == Role.CLIENT) { %>
            <div style="margin-top: 2rem; padding: 1.5rem; background: linear-gradient(135deg, #f0f9ff 0%, #e0f2fe 100%); border-radius: 0.75rem; border: 1px solid #bae6fd;">
                <p style="margin: 0; color: #0c4a6e; font-weight: 600;">
                    💡 Кликнете на "Моите пратки" в горното меню, за да видите вашите доставки.
                </p>
            </div>
            <% } %>
        </section>

        <!-- ================= EMPLOYEE DASHBOARD ================= -->
        <% if (isLoggedIn && userRole == Role.EMPLOYEE) { %>
        <h2 style="margin-top: 2rem;">Управление на системата</h2>

        <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 1rem;">

            <!-- Shipments -->
            <div class="card">
                <div class="card-header">📦 Пратки</div>
                <div class="card-body">
                    <p>Управление на всички пратки в системата</p>
                </div>
                <div class="card-footer">
                    <a href="${pageContext.request.contextPath}/employee-shipments" class="btn btn-primary">
                        Виж пратки
                    </a>
                </div>
            </div>

            <!-- Clients -->
            <div class="card">
                <div class="card-header">👥 Клиенти</div>
                <div class="card-body">
                    <p>Управление на клиенти</p>
                </div>
                <div class="card-footer">
                    <a href="${pageContext.request.contextPath}/clients" class="btn btn-primary">
                        Виж клиенти
                    </a>
                </div>
            </div>

            <!-- Employees -->
            <div class="card">
                <div class="card-header">👔 Служители</div>
                <div class="card-body">
                    <p>Управление на служители</p>
                </div>
                <div class="card-footer">
                    <a href="${pageContext.request.contextPath}/employees" class="btn btn-primary">
                        Виж служители
                    </a>
                </div>
            </div>

            <!-- Companies -->
            <div class="card">
                <div class="card-header">🏢 Компании</div>
                <div class="card-body">
                    <p>Управление на компании</p>
                </div>
                <div class="card-footer">
                    <a href="${pageContext.request.contextPath}/companies" class="btn btn-primary">
                        Виж компании
                    </a>
                </div>
            </div>

            <!-- Offices -->
            <div class="card">
                <div class="card-header">🏛️ Офиси</div>
                <div class="card-body">
                    <p>Управление на офиси</p>
                </div>
                <div class="card-footer">
                    <a href="${pageContext.request.contextPath}/offices" class="btn btn-primary">
                        Виж офиси
                    </a>
                </div>
            </div>

            <!-- Reports -->
            <div class="card">
                <div class="card-header">📊 Справки</div>
                <div class="card-body">
                    <p>Генериране на различни отчети</p>
                </div>
                <div class="card-footer">
                    <a href="${pageContext.request.contextPath}/reports" class="btn btn-primary">
                        Виж справки
                    </a>
                </div>
            </div>

            <!-- Register shipment -->
            <div class="card">
                <div class="card-header">➕ Регистриране</div>
                <div class="card-body">
                    <p>Регистриране на нова пратка</p>
                </div>
                <div class="card-footer">
                    <a href="${pageContext.request.contextPath}/shipment-register" class="btn btn-success">
                        Регистрирай пратка
                    </a>
                </div>
            </div>

        </div>
        <% } %>
    </main>

    <footer>
        <p>&copy; 2025 ALVAS Logistics. Всички права запазени.</p>
    </footer>

</div>
</body>
</html>
