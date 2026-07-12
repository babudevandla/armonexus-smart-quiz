<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Practice Sets" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="card">
    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:16px;">
        <h3 style="margin:0;">Practice Sets</h3>
        <a class="btn" href="${pageContext.request.contextPath}/practice-sets/new">+ Add Practice Set</a>
    </div>
    <table class="data-table">
        <thead><tr><th>#</th><th>Title</th><th>Subject</th><th>Status</th><th>Actions</th></tr></thead>
        <tbody>
        <c:forEach var="p" items="${practiceSets}" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${p.title}</td>
                <td>${p.subject != null ? p.subject.name : '-'}</td>
                <td>
                    <c:if test="${p.active}"><span class="badge badge-approved">Active</span></c:if>
                    <c:if test="${!p.active}"><span class="badge badge-rejected">Inactive</span></c:if>
                </td>
                <td>
                    <a class="btn btn-sm" href="${pageContext.request.contextPath}/practice-sets/${p.id}/edit">Edit</a>
                    <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/practice-sets/${p.id}/delete"
                       onclick="return confirm('Delete this practice set?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="../layout/page-foot.jsp" %>
