<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="bg">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Всички пратки - ALVAS Logistics</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<div class="container">
    <!-- Header -->
    <header>
        <div class="header-content">
            <a href="${pageContext.request.contextPath}/" class="logo">
                ALVAS Logistics
            </a>

            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/">Начало</a></li>
                    <li><a href="${pageContext.request.contextPath}/shipment-register">Регистрирай пратка</a></li>
                    <li><a href="${pageContext.request.contextPath}/reports">Справки</a></li>
                    <li><a href="${pageContext.request.contextPath}/logout">Изход</a></li>
                </ul>
            </nav>
        </div>
    </header>

    <!-- Main Content -->
    <main class="fade-in">
        <h2>📦 Всички пратки в системата</h2>

        <div class="alert alert-info">
            <strong>ℹ️ Информация:</strong> Като служител можете да виждате всички пратки регистрирани в системата.
        </div>

        <c:choose>
            <c:when test="${empty shipments}">
                <div class="card">
                    <div class="card-body text-center">
                        <h3>Няма регистрирани пратки</h3>
                        <p>Все още няма пратки в системата.</p>
                        <a href="${pageContext.request.contextPath}/shipment-register" class="btn-success">
                            Регистрирай първа пратка
                        </a>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="card">
                    <div class="card-header">
                        Общо пратки: <strong>${shipments.size()}</strong>
                    </div>

                    <table>
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Подател</th>
                            <th>Получател</th>
                            <th>Статус</th>
                            <th>Цена (лв.)</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="shipment" items="${shipments}">
                            <tr>
                                <td><strong>#${shipment.id}</strong></td>
                                <td>${shipment.sender}</td>
                                <td>${shipment.receiver}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${shipment.status == 'SENT'}">
                                            <span class="status status-sent">Изпратена</span>
                                        </c:when>
                                        <c:when test="${shipment.status == 'RECEIVED'}">
                                            <span class="status status-received">Получена</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="status status-pending">${shipment.status}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td><strong>${shipment.price}</strong> лв.</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

                <!-- Statistics -->
                <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 1.5rem; margin-top: 2rem;">
                    <div class="card">
                        <div class="card-body text-center">
                            <h3 style="color: var(--primary-color); font-size: 2.5rem; margin-bottom: 0.5rem;">
                                    ${shipments.size()}
                            </h3>
                            <p style="font-weight: 600;">Общо пратки</p>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-body text-center">
                            <h3 style="color: var(--warning-color); font-size: 2.5rem; margin-bottom: 0.5rem;">
                                <c:set var="sentCount" value="0"/>
                                <c:forEach var="s" items="${shipments}">
                                    <c:if test="${s.status == 'SENT'}">
                                        <c:set var="sentCount" value="${sentCount + 1}"/>
                                    </c:if>
                                </c:forEach>
                                    ${sentCount}
                            </h3>
                            <p style="font-weight: 600;">Изпратени</p>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-body text-center">
                            <h3 style="color: var(--success-color); font-size: 2.5rem; margin-bottom: 0.5rem;">
                                <c:set var="receivedCount" value="0"/>
                                <c:forEach var="s" items="${shipments}">
                                    <c:if test="${s.status == 'RECEIVED'}">
                                        <c:set var="receivedCount" value="${receivedCount + 1}"/>
                                    </c:if>
                                </c:forEach>
                                    ${receivedCount}
                            </h3>
                            <p style="font-weight: 600;">Получени</p>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>

        <div style="margin-top: 2rem;">
            <a href="${pageContext.request.contextPath}/" class="btn-outline">
                ← Обратно към началото
            </a>
            <a href="${pageContext.request.contextPath}/shipment-register" class="btn-success">
                ✍️ Регистрирай нова пратка
            </a>
        </div>
    </main>

    <!-- Footer -->
    <footer style="text-align: center; padding: 2rem 0; color: var(--text-muted);">
        <p>&copy; 2025 ALVAS Logistics. Всички права запазени.</p>
    </footer>
</div>
</body>
</html>
