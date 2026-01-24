<%@ page contentType="text/html; charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="bg">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 - Страницата не е намерена</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<div class="auth-container">
    <div class="auth-card fade-in">
        <div class="auth-header">
            <h1 style="border: none; color: var(--warning-color); font-size: 5rem;">404</h1>
            <h2>Страницата не е намерена</h2>
            <p>Търсената от вас страница не съществува или е била преместена.</p>
        </div>

        <div class="alert alert-warning">
            ⚠️ URL адресът, който търсите, не е намерен в нашата система.
        </div>

        <div class="card-body">
            <p><strong>Какво можете да направите:</strong></p>
            <ul style="margin-left: 1.5rem; color: var(--text-muted);">
                <li>Проверете URL адреса за грешки</li>
                <li>Използвайте бутона по-долу за връщане към началото</li>
                <li>Свържете се с администратор, ако проблемът продължава</li>
            </ul>
        </div>

        <div class="auth-footer">
            <a href="${pageContext.request.contextPath}/" class="btn-primary">
                ← Към началото
            </a>
        </div>
    </div>
</div>
</body>
</html>
