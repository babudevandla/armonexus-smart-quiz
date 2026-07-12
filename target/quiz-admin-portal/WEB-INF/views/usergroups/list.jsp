<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="User Groups" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="card">
    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:16px;">
        <h3 style="margin:0;">User Groups</h3>
        <a class="btn" href="${pageContext.request.contextPath}/user-groups/new">+ Add Group</a>
    </div>
    <table class="data-table">
        <thead><tr><th>#</th><th>Name</th><th>Description</th><th>Actions</th></tr></thead>
        <tbody>
        <c:forEach var="g" items="${groups}" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${g.name}</td>
                <td>${g.description}</td>
                <td>
                    <a class="btn btn-sm" href="${pageContext.request.contextPath}/user-groups/${g.id}/edit">Edit</a>
                    <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/user-groups/${g.id}/delete"
                       onclick="return confirm('Delete this group?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="../layout/page-foot.jsp" %>
