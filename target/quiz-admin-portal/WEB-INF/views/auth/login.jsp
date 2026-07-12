<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login | Quiz Admin Portal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin.css">
    <style>
        .login-wrap { display:flex; align-items:center; justify-content:center; height:100vh; background:#1f2937; }
        .login-box { background:#fff; padding:32px; border-radius:10px; width:380px; }
        .login-box h2 { margin-top:0; text-align:center; }
    </style>
</head>
<body>
<div class="login-wrap">
    <div class="login-box">
        <h2>ARMONEXUS SMART QUIZ</h2>

        <c:if test="${param.error != null}">
            <div class="alert alert-danger">Invalid email or password.</div>
        </c:if>
        <c:if test="${param.logout != null}">
            <div class="alert alert-success">You have been logged out.</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/login">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="username" class="form-control" required autofocus>
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" class="form-control" required>
            </div>
            <button type="submit" class="btn" style="width:100%">Sign In</button>
        </form>

        <div style="margin-top:20px; padding-top:16px; border-top:1px solid #e5e7eb; font-size:12px; color:#6b7280;">
            <strong>Demo accounts (seeded on first run):</strong><br>
            Admin: admin@quizapp.com / Admin@123<br>
            Instructor: instructor@quizapp.com / Instructor@123<br>
            Reviewer: reviewer@quizapp.com / Reviewer@123<br>
            Student: student@quizapp.com / Student@123
        </div>
    </div>
</div>
</body>
</html>
