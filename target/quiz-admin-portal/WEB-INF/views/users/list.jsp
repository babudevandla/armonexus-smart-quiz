<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Users" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="card">
    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:16px;">
        <h3 style="margin:0;">All Users</h3>
        <a class="btn" href="${pageContext.request.contextPath}/users/new">+ Add User</a>
    </div>

    <table class="data-table">
        <thead>
        <tr>
            <th>#</th><th>Full Name</th><th>Email</th><th>Phone</th>
            <th>Roles</th><th>Status</th><th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${user.fullName}</td>
                <td>${user.email}</td>
                <td>${user.phone}</td>
                <td>
                    <c:forEach var="role" items="${user.roles}">
                        <span class="badge badge-draft">${role.name}</span>
                    </c:forEach>
                </td>
                <td>
                    <c:if test="${user.enabled}"><span class="badge badge-approved">Active</span></c:if>
                    <c:if test="${!user.enabled}"><span class="badge badge-rejected">Disabled</span></c:if>
                </td>
                <td>
                    <a class="btn btn-sm" href="${pageContext.request.contextPath}/users/${user.id}/edit">Edit</a>
                    <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/users/${user.id}/toggle">Toggle</a>
                    <a class="btn btn-sm btn-danger"
                       href="${pageContext.request.contextPath}/users/${user.id}/delete"
                       onclick="return confirm('Delete this user?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="../layout/page-foot.jsp" %>
