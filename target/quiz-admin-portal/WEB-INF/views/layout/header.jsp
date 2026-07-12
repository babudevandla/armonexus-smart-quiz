<%@ include file="taglibs.jsp" %>
<header class="topbar">
    <button type="button" id="sidebarToggleBtn" class="sidebar-toggle-btn" aria-label="Toggle menu">
        <span></span><span></span><span></span>
    </button>
    <div class="topbar-title">${pageTitle}</div>
    <div class="topbar-user">
        <sec:authentication property="name" var="currentUserEmail"/>
        <span class="topbar-user-email">${currentUserEmail}</span>
        <form action="${pageContext.request.contextPath}/logout" method="post" style="display:inline">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" class="btn-link">Logout</button>
        </form>
    </div>
</header>
