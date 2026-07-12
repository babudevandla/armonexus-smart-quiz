<%@ include file="taglibs.jsp" %>
<header class="topbar navbar navbar-expand navbar-light">
    <button type="button" id="sidebarToggleBtn" class="navbar-toggler sidebar-toggle-btn" aria-label="Toggle menu">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="topbar-title">${pageTitle}</div>
    <div class="topbar-user ms-auto d-flex align-items-center gap-3">
        <sec:authentication property="name" var="currentUserEmail"/>
        <span class="topbar-user-email badge text-bg-light border">
            <i class="bi bi-person-circle me-1"></i>${currentUserEmail}
        </span>
        <form action="${pageContext.request.contextPath}/logout" method="post" class="m-0">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-sm btn-outline-danger">
                <i class="bi bi-box-arrow-right me-1"></i>Logout
            </button>
        </form>
    </div>
</header>
