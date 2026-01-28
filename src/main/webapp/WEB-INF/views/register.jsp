<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="bg">
<head>
    <!-- Page metadata -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Регистрация</title>

    <!-- Global stylesheet -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<div class="container">

    <!-- ================= HEADER ================= -->
    <header>
        <div class="header-content">
            <!-- Logo / Home link -->
            <a href="${pageContext.request.contextPath}/" class="logo">ALVAS Logistics</a>

            <!-- Navigation menu -->
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/">Начало</a></li>
                    <li><a href="${pageContext.request.contextPath}/login">Вход</a></li>
                    <li><a href="${pageContext.request.contextPath}/register">Регистрация</a></li>
                </ul>
            </nav>
        </div>
    </header>

    <!-- ================= MAIN CONTENT ================= -->
    <main>
        <div class="auth-form-container">
            <h2>Регистрация</h2>
            <p>Създайте нов акаунт в системата</p>

            <!-- Display server-side validation / error message -->
            <% String error = (String) request.getAttribute("error"); %>
            <% if (error != null) { %>
                <div class="alert alert-error"><%= error %></div>
            <% } %>

            <!-- ================= REGISTRATION FORM ================= -->
            <form method="post" action="${pageContext.request.contextPath}/register">

                <!-- Basic user information -->
                <label for="firstName">Име *</label>
                <input type="text" id="firstName" name="firstName" required>

                <label for="lastName">Фамилия *</label>
                <input type="text" id="lastName" name="lastName" required>

                <label for="email">Имейл *</label>
                <input type="email" id="email" name="email" required>

                <!-- Password fields -->
                <label for="password">Парола *</label>
                <input type="password" id="password" name="password" required minlength="8">

                <label for="confirmPassword">Потвърди парола *</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required minlength="8">

                <!-- Role selection -->
                <label for="role">Роля *</label>
                <select id="role" name="role" required>
                    <option value="">Избери роля</option>
                    <option value="CLIENT">Клиент</option>
                    <option value="EMPLOYEE">Служител</option>
                </select>

                <!-- ================= EMPLOYEE-ONLY FIELDS ================= -->
                <!-- Visible only when role == EMPLOYEE -->
                <div id="employeeTypeContainer" style="display: none;">
                    <label for="employeeType">Тип служител *</label>
                    <select id="employeeType" name="employeeType">
                        <option value="">-- Изберете тип --</option>
                        <option value="OFFICE_EMPLOYEE">Офис служител</option>
                        <option value="COURIER">Куриер</option>
                    </select>
                </div>

                <!-- ================= CLIENT-ONLY FIELDS ================= -->
                <!-- Company registration checkbox (CLIENT only) -->
                <div id="companyCheckboxContainer" class="checkbox-container" style="display: none;">
                    <label class="checkbox-label">
                        <input type="checkbox" id="isCompany" name="isCompany" value="true">
                        <span>Регистрация като фирма</span>
                    </label>
                </div>

                <!-- Company details (shown only if checkbox is checked) -->
                <div id="companyFields" class="company-fields">
                    <label for="companyName">Име на фирмата</label>
                    <input type="text"
                           id="companyName"
                           name="companyName"
                           placeholder="Въведете име на фирмата">
                    <small>
                        💡 Ако оставите празно, ще бъде създадена фирма с името: "Вашето име - Фирма"
                    </small>
                </div>

                <!-- Submit button -->
                <button type="submit">Регистрирай се</button>

                <!-- Login redirect -->
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

<!-- ================= CLIENT-SIDE LOGIC ================= -->
<script>
    // Validate passwords and employee type before submit
    document.querySelector('form').addEventListener('submit', function(e) {
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;

        // Passwords must match
        if (password !== confirmPassword) {
            e.preventDefault();
            alert('Паролите не съвпадат!');
            return;
        }

        // Employee must have employee type selected
        const role = document.getElementById('role').value;
        const employeeType = document.getElementById('employeeType').value;

        if (role === 'EMPLOYEE' && !employeeType) {
            e.preventDefault();
            alert('Моля изберете тип служител!');
        }
    });

    // Toggle fields based on selected role
    document.getElementById('role').addEventListener('change', function() {
        const companyCheckboxContainer = document.getElementById('companyCheckboxContainer');
        const employeeTypeContainer = document.getElementById('employeeTypeContainer');
        const isCompanyCheckbox = document.getElementById('isCompany');
        const companyFields = document.getElementById('companyFields');
        const companyNameInput = document.getElementById('companyName');
        const employeeTypeSelect = document.getElementById('employeeType');

        if (this.value === 'CLIENT') {
            companyCheckboxContainer.style.display = 'block';
            employeeTypeContainer.style.display = 'none';
            employeeTypeSelect.value = '';
        } else if (this.value === 'EMPLOYEE') {
            employeeTypeContainer.style.display = 'block';
            companyCheckboxContainer.style.display = 'none';
            isCompanyCheckbox.checked = false;
            companyFields.classList.remove('visible');
            companyNameInput.value = '';
        } else {
            companyCheckboxContainer.style.display = 'none';
            employeeTypeContainer.style.display = 'none';
            isCompanyCheckbox.checked = false;
            companyFields.classList.remove('visible');
            companyNameInput.value = '';
            employeeTypeSelect.value = '';
        }
    });

    // Toggle company fields based on checkbox
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
