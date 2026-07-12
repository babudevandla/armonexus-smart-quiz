<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Assign Users" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="card">
    <h3>Assign Quiz to a User</h3>
    <form method="post" action="${pageContext.request.contextPath}/quiz-assignments/users/save">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group" style="display:flex; gap:12px; align-items:flex-end;">
            <div style="flex:1;">
                <label>Quiz</label>
                <select name="quizId" class="form-control" required>
                    <c:forEach var="q" items="${quizzes}"><option value="${q.id}">${q.title}</option></c:forEach>
                </select>
            </div>
            <div style="flex:1;">
                <label>User</label>
                <select name="userId" class="form-control" required>
                    <c:forEach var="u" items="${users}"><option value="${u.id}">${u.fullName} (${u.email})</option></c:forEach>
                </select>
            </div>
            <div><button type="submit" class="btn">Assign</button></div>
        </div>
    </form>
</div>

<div class="card">
    <h3>Current User Assignments</h3>
    <table class="data-table">
        <thead><tr><th>#</th><th>Quiz</th><th>User</th><th>Actions</th></tr></thead>
        <tbody>
        <c:forEach var="a" items="${assignments}" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${a.quiz.title}</td>
                <td>${a.user.fullName}</td>
                <td>
                    <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/quiz-assignments/${a.id}/delete"
                       onclick="return confirm('Remove this assignment?');">Remove</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="../layout/page-foot.jsp" %>
