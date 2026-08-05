<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Subjects" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>


<div class="card">
    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:16px;">
        <h3 style="margin:0;">Subjects</h3>
        <a class="btn" href="${pageContext.request.contextPath}/subjects/new">+ Add Subject</a>
    </div>
    <table class="data-table">
        <thead><tr><th>#</th><th>Name</th><th>Description</th><th>Status</th><th>Actions</th></tr></thead>
        <tbody>
        <c:forEach var="subject" items="${subjects}" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${subject.name}</td>
                <td>${subject.description}</td>
                <td>
                    <c:if test="${subject.active}"><span class="badge badge-approved">Active</span></c:if>
                    <c:if test="${!subject.active}"><span class="badge badge-rejected">Inactive</span></c:if>
                </td>
                <td>
                    <a class="btn btn-sm" href="${pageContext.request.contextPath}/subjects/${subject.id}/edit">Edit</a>
                    <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/subjects/${subject.id}/delete"
                       onclick="return confirm('Delete this subject?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="../layout/page-foot.jsp" %>
