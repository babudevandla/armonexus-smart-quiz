<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Candidate Dashboard" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="card">
    <h3>Welcome!</h3>
    <p>Here are your assigned quizzes and available practice sets.</p>
</div>

<c:if test="${not empty announcements}">
    <div class="card">
        <h3>Announcements</h3>
        <c:forEach var="announcement" items="${announcements}">
            <div style="padding: 14px 16px; border: 1px solid #dfe4ea; border-radius: 8px; background: #f8fafc; margin-bottom: 12px;">
                <div style="font-weight: 700; margin-bottom: 6px;">${announcement.title}</div>
                <div style="white-space: pre-wrap; color: #374151;">${announcement.message}</div>
                <small style="display:block; margin-top: 8px; color: #6b7280;">${announcement.createdAt}</small>
            </div>
        </c:forEach>
    </div>
</c:if>

<div class="card">
    <h3>Assigned Quizzes</h3>
    <c:choose>
        <c:when test="${empty assignedQuizzes}">
            <p style="color:#6b7280;">No quizzes have been assigned to you yet.</p>
        </c:when>
        <c:otherwise>
            <table class="data-table">
                <thead><tr><th>Title</th><th>Duration</th><th>Total Marks</th><th>Status</th><th>Action</th></tr></thead>
                <tbody>
                <c:forEach var="q" items="${assignedQuizzes}">
                    <c:set var="status" value="${quizStatusMap[q.id]}"/>
                    <c:set var="startable" value="${status == 'OPEN' || status == 'NOT_SCHEDULED'}"/>
                    <tr>
                        <td>${q.title}</td>
                        <td>${q.durationMinutes} min</td>
                        <td>${q.totalMarks}</td>
                        <td>
                            <c:choose>
                                <c:when test="${status == 'OPEN' || status == 'NOT_SCHEDULED'}"><span class="badge badge-approved">Open</span></c:when>
                                <c:when test="${status == 'UPCOMING'}"><span class="badge badge-pending">Not started yet</span></c:when>
                                <c:when test="${status == 'EXPIRED'}"><span class="badge badge-rejected">Closed</span></c:when>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${startable}">
                                    <a class="btn btn-sm" href="${pageContext.request.contextPath}/candidate/quizzes/${q.id}/take">Start</a>
                                </c:when>
                                <c:otherwise>
                                    <button class="btn btn-sm btn-secondary" disabled style="opacity:0.6; cursor:not-allowed;">
                                        <c:if test="${status == 'UPCOMING'}">Not Open Yet</c:if>
                                        <c:if test="${status == 'EXPIRED'}">Closed</c:if>
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<div class="card">
    <h3>Practice Sets</h3>
    <c:choose>
        <c:when test="${empty practiceSets}">
            <p style="color:#6b7280;">No practice sets are available right now.</p>
        </c:when>
        <c:otherwise>
            <table class="data-table">
                <thead><tr><th>Title</th><th>Subject</th><th>Action</th></tr></thead>
                <tbody>
                <c:forEach var="p" items="${practiceSets}">
                    <tr>
                        <td>${p.title}</td>
                        <td>${p.subject != null ? p.subject.name : '-'}</td>
                        <td><a class="btn btn-sm" href="${pageContext.request.contextPath}/candidate/practice/${p.id}/take">Practice</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<div class="card">
    <h3>Recent Results</h3>
    <c:choose>
        <c:when test="${empty recentResults}">
            <p style="color:#6b7280;">You haven't taken any quizzes yet.</p>
        </c:when>
        <c:otherwise>
            <table class="data-table">
                <thead><tr><th>Quiz</th><th>Score</th><th>Result</th></tr></thead>
                <tbody>
                <c:forEach var="r" items="${recentResults}">
                    <tr>
                        <td>${r.quiz.title}</td>
                        <td>${r.scoreObtained} / ${r.totalMarks}</td>
                        <td>
                            <c:if test="${r.passed}"><span class="badge badge-approved">Passed</span></c:if>
                            <c:if test="${!r.passed}"><span class="badge badge-rejected">Failed</span></c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="../layout/page-foot.jsp" %>
