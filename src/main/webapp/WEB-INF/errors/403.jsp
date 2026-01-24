<%@ page contentType="text/html; charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="bg">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>403 - Достъп отказан</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<div class="auth-container">
  <div class="auth-card fade-in">
    <div class="auth-header">
      <h1 style="border: none; color: var(--danger-color); font-size: 5rem;">403</h1>
      <h2>Достъп отказан</h2>
      <p>Нямате права за достъп до тази страница.</p>
    </div>

    <div class="alert alert-error">
      🚫 Тази страница изисква специални права за достъп.
    </div>

    <div class="card-body">
      <p><strong>Възможни причини:</strong></p>
      <ul style="margin-left: 1.5rem; color: var(--text-muted);">
        <li>Нямате необходимата роля (Служител/Клиент)</li>
        <li>Сесията ви е изтекла</li>
        <li>Опитвате се да достъпите защитен ресурс</li>
      </ul>
    </div>

    <div class="auth-footer">
      <a href="${pageContext.request.contextPath}/" class="btn-primary">
        ← Към началото
      </a>
      <a href="${pageContext.request.contextPath}/login" class="btn-outline">
        Влез отново
      </a>
    </div>
  </div>
</div>
</body>
</html>
