<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="bg">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Регистрация</title>
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
                    <li><a href="${pageContext.request.contextPath}/login">Вход</a></li>
                    <li><a href="${pageContext.request.contextPath}/register">Регистрация</a></li>
                </ul>
            </nav>
        </div>
    </header>

    <main>
        <div class="auth-form-container">
            <h2>Регистрация</h2>
            <p>Създайте нов акаунт в системата</p>

            <% String error = (String) request.getAttribute("error"); %>
            <% if (error != null) { %>
            <div class="alert alert-error"><%= error %></div>
            <% } %>

            <form method="post" action="${pageContext.request.contextPath}/register">
                <label for="firstName">Име *</label>
                <input type="text" id="firstName" name="firstName" required>

                <label for="lastName">Фамилия *</label>
                <input type="text" id="lastName" name="lastName" required>

                <label for="email">Имейл *</label>
                <input type="email" id="email" name="email" required>

                <label for="password">Парола *</label>
                <input type="password" id="password" name="password" required minlength="8">

                <label for="confirmPassword">Потвърди парола *</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required minlength="8">

                <label for="role">Роля *</label>
                <select id="role" name="role" required>
                    <option value="">Избери роля</option>
                    <option value="CLIENT">Клиент</option>
                    <option value="EMPLOYEE">Служител</option>
                </select>

                <!-- EMPLOYEE TYPE (показва се само за EMPLOYEE) -->
                <div id="employeeTypeContainer" style="display: none;">
                    <label for="employeeType">Тип служител *</label>
                    <select id="employeeType" name="employeeType">
                        <option value="">-- Изберете тип --</option>
                        <option value="OFFICE_EMPLOYEE">Офис служител</option>
                        <option value="COURIER">Куриер</option>
                    </select>
                </div>

                <!-- COMPANY CHECKBOX (показва се само за CLIENT) -->
                <div id="companyCheckboxContainer" class="checkbox-container" style="display: none;">
                    <label class="checkbox-label">
                        <input type="checkbox" id="isCompany" name="isCompany" value="true">
                        <span>Регистрация като фирма</span>
                    </label>
                </div>

                <div id="companyFields" class="company-fields">
                    <label for="companyName">Име на фирмата</label>
                    <input type="text" id="companyName" name="companyName"
                           placeholder="Въведете име на фирмата">
                    <small>
                        💡 Ако оставите празно, ще бъде създадена фирма с името: "Вашето име - Фирма"
                    </small>
                </div>

                <button type="submit">Регистрирай се</button>

                <div class="text-center">
                    <p>Вече имате акаунт?</p>
                    <a href="${pageContext.request.contextPath}/login" class="btn-outline">Влезте</a>
                </div>
            </form>
        </div>
    </main>

    <footer>
        <p>&copy; 2025 ALVAS Logistics. Всички права запазени.</p>
    </footer>
</div>

<script>
    // Password validation
    document.querySelector('form').addEventListener('submit', function(e) {
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;

        if (password !== confirmPassword) {
            e.preventDefault();
            alert('Паролите не съвпадат!');
            return;
        }

        // Validate employee type if EMPLOYEE role is selected
        const role = document.getElementById('role').value;
        const employeeType = document.getElementById('employeeType').value;

        if (role === 'EMPLOYEE' && !employeeType) {
            e.preventDefault();
            alert('Моля изберете тип служител!');
            return;
        }
    });

    // Show/hide fields based on role
    document.getElementById('role').addEventListener('change', function() {
        const companyCheckboxContainer = document.getElementById('companyCheckboxContainer');
        const employeeTypeContainer = document.getElementById('employeeTypeContainer');
        const isCompanyCheckbox = document.getElementById('isCompany');
        const companyFields = document.getElementById('companyFields');
        const companyNameInput = document.getElementById('companyName');
        const employeeTypeSelect = document.getElementById('employeeType');

        if (this.value === 'CLIENT') {
            // Show company checkbox for CLIENT
            companyCheckboxContainer.style.display = 'block';
            employeeTypeContainer.style.display = 'none';
            employeeTypeSelect.value = '';
        } else if (this.value === 'EMPLOYEE') {
            // Show employee type for EMPLOYEE
            employeeTypeContainer.style.display = 'block';
            companyCheckboxContainer.style.display = 'none';
            isCompanyCheckbox.checked = false;
            companyFields.classList.remove('visible');
            companyNameInput.value = '';
        } else {
            // Hide both if no role selected
            companyCheckboxContainer.style.display = 'none';
            employeeTypeContainer.style.display = 'none';
            isCompanyCheckbox.checked = false;
            companyFields.classList.remove('visible');
            companyNameInput.value = '';
            employeeTypeSelect.value = '';
        }
    });

    // Show/hide company fields based on checkbox
    document.getElementById('isCompany').addEventListener('change', function() {
        const companyFields = document.getElementById('companyFields');
        const companyNameInput = document.getElementById('companyName');

        if (this.checked) {
            companyFields.classList.add('visible');
        } else {
            companyFields.classList.remove('visible');
            companyNameInput.value = '';
        }
    });
</script>
</body>
</html>