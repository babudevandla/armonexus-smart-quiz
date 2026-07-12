<%@ include file="taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${pageTitle} | ARMONEXUS SMART QUIZ</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin.css">
    <!-- DataTables (adds search/sort/pagination to every table.data-table automatically, see page-foot.jsp) -->
    <link rel="stylesheet" href="https://cdn.datatables.net/2.3.8/css/dataTables.dataTables.css">
</head>
<body>
<div class="layout">
    <div class="sidebar-backdrop" id="sidebarBackdrop"></div>
    <c:import url="/WEB-INF/views/layout/sidebar.jsp"/>
    <div class="main">
         <c:import url="/WEB-INF/views/layout/header.jsp"/>
        <div class="content">
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success">${successMessage}</div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">${errorMessage}</div>
            </c:if>
