<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Tags" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="card">
    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:16px;">
        <h3 style="margin:0;">Tags</h3>
        <a class="btn" href="${pageContext.request.contextPath}/tags/new">+ Add Tag</a>
    </div>
    <table class="data-table">
        <thead><tr><th>#</th><th>Name</th><th>Actions</th></tr></thead>
        <tbody>
        <c:forEach var="tag" items="${tags}" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${tag.name}</td>
                <td>
                    <a class="btn btn-sm" href="${pageContext.request.contextPath}/tags/${tag.id}/edit">Edit</a>
                    <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/tags/${tag.id}/delete"
                       onclick="return confirm('Delete this tag?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="../layout/page-foot.jsp" %>
