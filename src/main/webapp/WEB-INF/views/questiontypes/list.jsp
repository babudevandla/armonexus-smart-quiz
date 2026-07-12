<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Question Types" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="card">
    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:16px;">
        <h3 style="margin:0;">Question Types</h3>
        <a class="btn" href="${pageContext.request.contextPath}/question-types/new">+ Add Type</a>
    </div>
    <table class="data-table">
        <thead><tr><th>#</th><th>Name</th><th>Actions</th></tr></thead>
        <tbody>
        <c:forEach var="t" items="${types}" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${t.name}</td>
                <td>
                    <a class="btn btn-sm" href="${pageContext.request.contextPath}/question-types/${t.id}/edit">Edit</a>
                    <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/question-types/${t.id}/delete"
                       onclick="return confirm('Delete this type?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="../layout/page-foot.jsp" %>
