<%@ include file="../layout/taglibs.jsp" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Not Found</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin.css">
</head>
<body class="d-flex align-items-center justify-content-center" style="min-height:100vh; background:var(--surface);">
<div class="card text-center p-5" style="max-width:420px;">
    <i class="bi bi-search text-warning" style="font-size:48px;"></i>
    <h2 class="mt-3">404 - Not Found</h2>
    <p class="text-muted">${errorMessage}</p>
    <a class="btn btn-primary" href="${pageContext.request.contextPath}/dashboard">
        <i class="bi bi-house-fill me-1"></i>Back to Dashboard
    </a>
</div>
</body>
</html>
