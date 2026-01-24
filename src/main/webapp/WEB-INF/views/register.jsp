<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="bg">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Регистрация - ALVAS Logistics</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        .password-requirements {
            background: var(--bg-color);
            padding: 1rem;
            border-radius: var(--radius-md);
            margin-bottom: 1rem;
            font-size: 0.875rem;
        }
        .password-requirements ul {
            margin: 0.5rem 0 0 1.5rem;
        }
        .password-requirements li {
            color: var(--text-muted);
            margin: 0.25rem 0;
        }
        .requirement-met {
            color: var(--success-color) !important;
        }
        .password-match-indicator {
            font-size: 0.875rem;
            margin-top: -0.5rem;
            margin-bottom: 1rem;
        }
        .match-success {
            color: var(--success-color);
        }
        .match-error {
            color: var(--danger-color);
        }
    </style>
</head>
<body>
<div class="auth-container">
    <div class="auth-card fade-in">
        <div class="auth-header">
            <h1 style="border: none;">📦 ALVAS Logistics</h1>
            <h2>Регистрация</h2>
            <p>Създайте своя акаунт в логистичната система.</p>
        </div>

        <% String error = (String) request.getAttribute("error");
            if (error != null) { %>
        <div class="alert alert-error">
            ⚠️ <%= error %>
        </div>
        <% } %>

        <form method="post" action="${pageContext.request.contextPath}/register" id="registerForm">
            <label for="firstName">
                Име
                <input
                        type="text"
                        id="firstName"
                        name="firstName"
                        placeholder="Вашето име"
                        required
                        autofocus>
            </label>

            <label for="lastName">
                Фамилия
                <input
                        type="text"
                        id="lastName"
                        name="lastName"
                        placeholder="Вашата фамилия"
                        required>
            </label>

            <label for="email">
                Email адрес
                <input
                        type="email"
                        id="email"
                        name="email"
                        placeholder="vash@email.com"
                        required>
            </label>

            <div class="password-requirements">
                <strong>Изисквания за паролата:</strong>
                <ul>
                    <li id="req-length">Минимум 8 символа</li>
                </ul>
            </div>

            <label for="password">
                Парола
                <input
                        type="password"
                        id="password"
                        name="password"
                        placeholder="Минимум 8 символа"
                        required
                        minlength="8">
            </label>

            <label for="confirm">
                Потвърдете паролата
                <input
                        type="password"
                        id="confirm"
                        name="confirm"
                        placeholder="Въведете отново паролата"
                        required>
            </label>

            <div id="passwordMatch" class="password-match-indicator"></div>

            <label for="role">
                Роля
                <select id="role" name="role" required>
                    <option value="">-- Изберете роля --</option>
                    <option value="CLIENT">Клиент</option>
                    <option value="EMPLOYEE">Служител</option>
                </select>
            </label>

            <button type="submit" class="btn-primary" id="submitBtn">
                Регистрирай се
            </button>
        </form>

        <div class="auth-footer">
            <p>Вече имате акаунт?</p>
            <a href="${pageContext.request.contextPath}/login" class="btn-outline">
                Влезте
            </a>
        </div>
    </div>
</div>

<script>
    // Password validation and matching
    const password = document.getElementById('password');
    const confirm = document.getElementById('confirm');
    const form = document.getElementById('registerForm');
    const submitBtn = document.getElementById('submitBtn');
    const matchIndicator = document.getElementById('passwordMatch');
    const reqLength = document.getElementById('req-length');

    // Check password requirements
    password.addEventListener('input', function() {
        if (password.value.length >= 8) {
            reqLength.classList.add('requirement-met');
        } else {
            reqLength.classList.remove('requirement-met');
        }
        checkPasswordMatch();
    });

    // Check password match
    confirm.addEventListener('input', checkPasswordMatch);

    function checkPasswordMatch() {
        if (confirm.value === '') {
            matchIndicator.textContent = '';
            return;
        }

        if (password.value === confirm.value) {
            matchIndicator.textContent = '✓ Паролите съвпадат';
            matchIndicator.className = 'password-match-indicator match-success';
        } else {
            matchIndicator.textContent = '✗ Паролите не съвпадат';
            matchIndicator.className = 'password-match-indicator match-error';
        }
    }

    // Form submission validation
    form.addEventListener('submit', function(e) {
        if (password.value !== confirm.value) {
            e.preventDefault();
            alert('Паролите не съвпадат! Моля, проверете отново.');
            confirm.focus();
            return false;
        }

        if (password.value.length < 8) {
            e.preventDefault();
            alert('Паролата трябва да бъде минимум 8 символа!');
            password.focus();
            return false;
        }
    });
</script>
</body>
</html>
