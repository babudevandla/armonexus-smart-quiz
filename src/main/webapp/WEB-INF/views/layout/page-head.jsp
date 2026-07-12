<%@ include file="../layout/taglibs.jsp" %>
<%@ include file="taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${pageTitle} | Quiz Admin Portal</title>

    <!-- Bootstrap 5 (loaded first so existing .btn/.card/.form-control/.alert
         markup across every JSP picks up real Bootstrap styling automatically) -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <!-- Bootstrap Icons, used for sidebar chevrons/icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <!-- DataTables (adds search/sort/pagination to every table.data-table automatically, see page-foot.jsp) -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.8/css/dataTables.bootstrap5.min.css">

    <!-- App theme overrides, loaded LAST so our color palette wins over Bootstrap/DataTables defaults -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin.css">
</head>
<body>
<div class="layout">
    <div class="sidebar-backdrop" id="sidebarBackdrop"></div>
     <c:import url="/WEB-INF/views/layout/sidebar.jsp"/>
    <div class="main">
         <c:import url="/WEB-INF/views/layout/header.jsp"/>
        <div class="content">
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <i class="bi bi-check-circle-fill me-2"></i>${successMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="bi bi-exclamation-triangle-fill me-2"></i>${errorMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
