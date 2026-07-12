<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Quiz Schedules" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="card">
    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:16px;">
        <h3 style="margin:0;">Quiz Schedules</h3>
        <a class="btn" href="${pageContext.request.contextPath}/quiz-schedules/new">+ Add Schedule</a>
    </div>
    <table class="data-table">
        <thead><tr><th>#</th><th>Quiz</th><th>Start</th><th>End</th><th>Status</th><th>Actions</th></tr></thead>
        <tbody>
        <c:forEach var="s" items="${schedules}" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${s.quiz.title}</td>
                <td>${s.startTime}</td>
                <td>${s.endTime}</td>
                <td>
                    <c:if test="${s.active}"><span class="badge badge-approved">Active</span></c:if>
                    <c:if test="${!s.active}"><span class="badge badge-rejected">Inactive</span></c:if>
                </td>
                <td>
                    <a class="btn btn-sm" href="${pageContext.request.contextPath}/quiz-schedules/${s.id}/edit">Edit</a>
                    <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/quiz-schedules/${s.id}/delete"
                       onclick="return confirm('Delete this schedule?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="../layout/page-foot.jsp" %>
