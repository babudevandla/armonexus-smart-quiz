<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Assign Groups" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="card">
    <h3>Assign Quiz to a Group</h3>
    <form method="post" action="${pageContext.request.contextPath}/quiz-assignments/groups/save">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group" style="display:flex; gap:12px; align-items:flex-end;">
            <div style="flex:1;">
                <label>Quiz</label>
                <select name="quizId" class="form-control" required>
                    <c:forEach var="q" items="${quizzes}"><option value="${q.id}">${q.title}</option></c:forEach>
                </select>
            </div>
            <div style="flex:1;">
                <label>Group</label>
                <select name="groupId" class="form-control" required>
                    <c:forEach var="g" items="${groups}"><option value="${g.id}">${g.name}</option></c:forEach>
                </select>
            </div>
            <div><button type="submit" class="btn">Assign</button></div>
        </div>
    </form>
</div>

<div class="card">
    <h3>Current Group Assignments</h3>
    <table class="data-table">
        <thead><tr><th>#</th><th>Quiz</th><th>Group</th><th>Actions</th></tr></thead>
        <tbody>
        <c:forEach var="a" items="${assignments}" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${a.quiz.title}</td>
                <td>${a.group.name}</td>
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
