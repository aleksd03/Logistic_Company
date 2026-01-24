<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="bg">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Моите пратки - ALVAS Logistics</title>
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
                    <li><a href="${pageContext.request.contextPath}/logout">Изход</a></li>
                </ul>
            </nav>
        </div>
    </header>

    <!-- Main Content -->
    <main class="fade-in">
        <h2>📦 Моите пратки</h2>

        <div class="alert alert-info">
            <strong>ℹ️ Информация:</strong> Тук можете да видите всички пратки, които сте изпратили или получили.
        </div>

        <c:choose>
            <c:when test="${empty shipments}">
                <div class="card">
                    <div class="card-body text-center">
                        <h3>Няма намерени пратки</h3>
                        <p>Все още нямате регистрирани пратки в системата.</p>
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
            </c:otherwise>
        </c:choose>

        <div style="margin-top: 2rem;">
            <a href="${pageContext.request.contextPath}/" class="btn-outline">
                ← Обратно към началото
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
