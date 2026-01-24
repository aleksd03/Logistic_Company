<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="bg">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Вход - ALVAS Logistics</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<div class="auth-container">
    <div class="auth-card fade-in">
        <div class="auth-header">
            <h1 style="border: none;">📦 ALVAS Logistics</h1>
            <h2>Вход в системата</h2>
            <p>Добре дошли обратно! Моля влезте във вашия акаунт.</p>
        </div>

        <% String error = (String) request.getAttribute("error");
            if (error != null) { %>
        <div class="alert alert-error">
            ⚠️ <%= error %>
        </div>
        <% } %>

        <form method="post" action="${pageContext.request.contextPath}/login">
            <label for="email">
                Email адрес
                <input
                        type="email"
                        id="email"
                        name="email"
                        placeholder="vash@email.com"
                        required
                        autofocus>
            </label>

            <label for="password">
                Парола
                <input
                        type="password"
                        id="password"
                        name="password"
                        placeholder="Въведете паролата си"
                        required>
            </label>

            <button type="submit" class="btn-primary">
                Влез в системата
            </button>
        </form>

        <div class="auth-footer">
            <p>Нямате акаунт?</p>
            <a href="${pageContext.request.contextPath}/register" class="btn-outline">
                Регистрирайте се
            </a>
        </div>
    </div>
</div>
</body>
</html>
