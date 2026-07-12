<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Quizzes" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="card">
    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:16px;">
        <h3 style="margin:0;">Quizzes</h3>
        <a class="btn" href="${pageContext.request.contextPath}/quizzes/new">+ Create Quiz</a>
    </div>
    <table class="data-table">
        <thead><tr><th>#</th><th>Title</th><th>Subject</th><th>Duration</th><th>Total Marks</th><th>Passing Marks</th><th>Active</th><th>Schedule</th><th>Actions</th></tr></thead>
        <tbody>
        <c:forEach var="quiz" items="${quizzes}" varStatus="i">
            <c:set var="status" value="${quizStatusMap[quiz.id]}"/>
            <tr>
                <td>${i.index + 1}</td>
                <td>${quiz.title}</td>
                <td>${quiz.subject != null ? quiz.subject.name : '-'}</td>
                <td>${quiz.durationMinutes} min</td>
                <td>${quiz.totalMarks}</td>
                <td>${quiz.passingMarks}</td>
                <td>
                    <c:if test="${quiz.active}"><span class="badge badge-approved">Active</span></c:if>
                    <c:if test="${!quiz.active}"><span class="badge badge-rejected">Inactive</span></c:if>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${status == 'NOT_SCHEDULED'}"><span class="badge badge-draft">Not Scheduled</span></c:when>
                        <c:when test="${status == 'OPEN'}"><span class="badge badge-approved">Open Now</span></c:when>
                        <c:when test="${status == 'UPCOMING'}"><span class="badge badge-pending">Upcoming</span></c:when>
                        <c:when test="${status == 'EXPIRED'}"><span class="badge badge-rejected">Closed</span></c:when>
                    </c:choose>
                </td>
                <td>
                    <a class="btn btn-sm" href="${pageContext.request.contextPath}/quizzes/${quiz.id}/edit">Edit</a>
                    <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/quiz-schedules/new">Schedule</a>
                    <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/quizzes/${quiz.id}/delete"
                       onclick="return confirm('Delete this quiz?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="../layout/page-foot.jsp" %>
