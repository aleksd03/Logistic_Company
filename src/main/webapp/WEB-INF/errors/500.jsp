<%@ page contentType="text/html; charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="bg">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>500 - Сървърна грешка</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<div class="auth-container">
  <div class="auth-card fade-in">
    <div class="auth-header">
      <h1 style="border: none; color: var(--danger-color); font-size: 5rem;">500</h1>
      <h2>Вътрешна сървърна грешка</h2>
      <p>Възникна проблем при обработката на вашата заявка.</p>
    </div>

    <div class="alert alert-error">
      ❌ Нещо се обърка от наша страна. Извиняваме се за неудобството!
    </div>

    <div class="card-body">
      <p><strong>Какво можете да направите:</strong></p>
      <ul style="margin-left: 1.5rem; color: var(--text-muted);">
        <li>Опитайте да презаредите страницата</li>
        <li>Изчакайте няколко минути и опитайте отново</li>
        <li>Свържете се с технически екип, ако проблемът продължава</li>
      </ul>

      <% if (exception != null) { %>
      <details style="margin-top: 1rem; padding: 1rem; background: var(--bg-color); border-radius: var(--radius-md);">
        <summary style="cursor: pointer; font-weight: 600;">Технически детайли (за разработчици)</summary>
        <pre style="margin-top: 1rem; overflow-x: auto; font-size: 0.875rem;"><%= exception.getMessage() %></pre>
      </details>
      <% } %>
    </div>

    <div class="auth-footer">
      <a href="${pageContext.request.contextPath}/" class="btn-primary">
        ← Към началото
      </a>
      <button onclick="window.location.reload()" class="btn-outline">
        🔄 Презареди страницата
      </button>
    </div>
  </div>
</div>
</body>
</html>