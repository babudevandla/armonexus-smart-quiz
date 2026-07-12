<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Invitations" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="card">
    <h3>Send Invitation</h3>
    <form method="post" action="${pageContext.request.contextPath}/invitations/save">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group" style="display:flex; gap:12px; align-items:flex-end;">
            <div style="flex:1;">
                <label>Quiz</label>
                <select name="quizId" class="form-control" required>
                    <c:forEach var="q" items="${quizzes}"><option value="${q.id}">${q.title}</option></c:forEach>
                </select>
            </div>
            <div style="flex:1;">
                <label>Invitee Email</label>
                <input type="email" name="email" class="form-control" required>
            </div>
            <div><button type="submit" class="btn">Send Invite</button></div>
        </div>
    </form>
</div>

<div class="card">
    <h3>Sent Invitations</h3>
    <table class="data-table">
        <thead><tr><th>#</th><th>Quiz</th><th>Email</th><th>Status</th><th>Sent At</th><th>Actions</th></tr></thead>
        <tbody>
        <c:forEach var="inv" items="${invitations}" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${inv.quiz.title}</td>
                <td>${inv.email}</td>
                <td>
                    <c:choose>
                        <c:when test="${inv.status == 'ACCEPTED'}"><span class="badge badge-approved">Accepted</span></c:when>
                        <c:when test="${inv.status == 'EXPIRED'}"><span class="badge badge-rejected">Expired</span></c:when>
                        <c:otherwise><span class="badge badge-pending">Pending</span></c:otherwise>
                    </c:choose>
                </td>
                <td>${inv.sentAt}</td>
                <td>
                    <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/invitations/${inv.id}/delete"
                       onclick="return confirm('Remove this invitation?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="../layout/page-foot.jsp" %>
