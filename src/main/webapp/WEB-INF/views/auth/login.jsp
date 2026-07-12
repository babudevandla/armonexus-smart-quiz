<%@ include file="../layout/taglibs.jsp" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login | Quiz Admin Portal</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin.css">
    <style>
        .login-wrap {
            display: flex; align-items: center; justify-content: center;
            min-height: 100vh; padding: 20px;
            background: linear-gradient(135deg, var(--brand-navy) 0%, var(--brand-accent-2) 100%);
        }
        .login-box {
            background: #fff; padding: 36px; border-radius: 16px;
            width: 100%; max-width: 400px;
            box-shadow: 0 20px 50px rgba(0,0,0,0.25);
        }
        .login-icon {
            width: 56px; height: 56px; border-radius: 50%;
            background: var(--brand-accent); color: #fff;
            display: flex; align-items: center; justify-content: center;
            font-size: 26px; margin: 0 auto 14px;
        }
    </style>
</head>
<body>
<div class="login-wrap">
    <div class="login-box">
        <div class="login-icon"><i class="bi bi-mortarboard-fill"></i></div>
        <h2 class="text-center mb-1">Quiz Admin Portal</h2>
        <p class="text-center text-muted mb-4" style="font-size:14px;">Sign in to continue</p>

        <c:if test="${param.error != null}">
            <div class="alert alert-danger py-2"><i class="bi bi-exclamation-triangle-fill me-1"></i>Invalid email or password.</div>
        </c:if>
        <c:if test="${param.logout != null}">
            <div class="alert alert-success py-2"><i class="bi bi-check-circle-fill me-1"></i>You have been logged out.</div>
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
            <button type="submit" class="btn btn-primary w-100">
                <i class="bi bi-box-arrow-in-right me-1"></i>Sign In
            </button>
        </form>

        <div class="mt-4 pt-3 border-top" style="font-size:12px; color:#6b7280;">
            <strong>Demo accounts (seeded on first run):</strong><br>
            <i class="bi bi-shield-lock-fill text-primary"></i> Admin: admin@quizapp.com / Admin@123<br>
            <i class="bi bi-easel-fill text-primary"></i> Instructor: instructor@quizapp.com / Instructor@123<br>
            <i class="bi bi-clipboard-check-fill text-primary"></i> Reviewer: reviewer@quizapp.com / Reviewer@123<br>
            <i class="bi bi-person-fill text-primary"></i> Student: student@quizapp.com / Student@123
        </div>
    </div>
</div>
</body>
</html>
