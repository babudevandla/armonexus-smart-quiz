<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>Access Denied</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin.css"></head>
<body style="display:flex;align-items:center;justify-content:center;height:100vh;">
    <div class="card" style="text-align:center;">
        <h2>403 - Access Denied</h2>
        <p>You do not have permission to view this page.</p>
        <a class="btn" href="${pageContext.request.contextPath}/dashboard">Back to Dashboard</a>
    </div>
</body>
</html>
