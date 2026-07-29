<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="User Roles" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="card">
    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:16px;">
        <h3 style="margin:0;">User Roles</h3>
        <a class="btn" href="${pageContext.request.contextPath}/roles/new">+ Add Role</a>
    </div>
    <table class="data-table">
        <thead><tr><th>#</th><th>Name</th><th>Description</th><th>Assigned</th><th>Actions</th></tr></thead>
        <tbody>
        <c:forEach var="role" items="${roles}" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${role.name}</td>
                <td>${role.description}</td>
                <td>${assignedCount}</td>
                <td>
                    <a class="btn btn-sm" href="${pageContext.request.contextPath}/roles/${role.id}/edit">Edit</a>
                    <c:set var="assignedCount" value="${userCounts[role.id] != null ? userCounts[role.id] : 0}" />
                    <c:choose>
                        <c:when test="${assignedCount == 0}">
                            <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/roles/${role.id}/delete"
                               onclick="return confirm('Delete this role?');">Delete</a>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-sm btn-danger" disabled title="Assigned to ${assignedCount} user(s), unassign first">Delete</button>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="../layout/page-foot.jsp" %>
