<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Available Quizzes" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="card">
    <h3>Available Quizzes</h3>
    <c:choose>
        <c:when test="${empty quizzes}">
            <p style="color:#6b7280;">No quizzes have been assigned to you yet.</p>
        </c:when>
        <c:otherwise>
            <table class="data-table">
                <thead><tr><th>Title</th><th>Description</th><th>Duration</th><th>Total Marks</th><th>Passing Marks</th><th>Status</th><th>Action</th></tr></thead>
                <tbody>
                <c:forEach var="q" items="${quizzes}">
                    <c:set var="status" value="${quizStatusMap[q.id]}"/>
                    <c:set var="startable" value="${status == 'OPEN' || status == 'NOT_SCHEDULED'}"/>
                    <tr>
                        <td>${q.title}</td>
                        <td>${q.description}</td>
                        <td>${q.durationMinutes} min</td>
                        <td>${q.totalMarks}</td>
                        <td>${q.passingMarks}</td>
                        <td>
                            <c:choose>
                                <c:when test="${status == 'OPEN'}"><span class="badge badge-approved">Open</span></c:when>
                                <c:when test="${status == 'NOT_SCHEDULED'}"><span class="badge badge-approved">Open</span></c:when>
                                <c:when test="${status == 'UPCOMING'}"><span class="badge badge-pending">Not started yet</span></c:when>
                                <c:when test="${status == 'EXPIRED'}"><span class="badge badge-rejected">Closed</span></c:when>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${startable}">
                                    <a class="btn btn-sm" href="${pageContext.request.contextPath}/student/quizzes/${q.id}/take">Start Quiz</a>
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

<%@ include file="../layout/page-foot.jsp" %>
