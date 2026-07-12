<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Categories" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="card">
    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:16px;">
        <h3 style="margin:0;">Categories</h3>
        <a class="btn" href="${pageContext.request.contextPath}/categories/new">+ Add Category</a>
    </div>
    <table class="data-table">
        <thead><tr><th>#</th><th>Name</th><th>Subject</th><th>Status</th><th>Actions</th></tr></thead>
        <tbody>
        <c:forEach var="cat" items="${categories}" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${cat.name}</td>
                <td>${cat.subject != null ? cat.subject.name : '-'}</td>
                <td>
                    <c:if test="${cat.active}"><span class="badge badge-approved">Active</span></c:if>
                    <c:if test="${!cat.active}"><span class="badge badge-rejected">Inactive</span></c:if>
                </td>
                <td>
                    <a class="btn btn-sm" href="${pageContext.request.contextPath}/categories/${cat.id}/edit">Edit</a>
                    <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/categories/${cat.id}/delete"
                       onclick="return confirm('Delete this category?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="../layout/page-foot.jsp" %>
