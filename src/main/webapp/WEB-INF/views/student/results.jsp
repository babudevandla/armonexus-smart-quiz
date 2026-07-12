<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="My Results" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="card">
    <h3>My Quiz Results</h3>
    <c:choose>
        <c:when test="${empty results}">
            <p style="color:#6b7280;">You haven't taken any quizzes yet.</p>
        </c:when>
        <c:otherwise>
            <table class="data-table">
                <thead><tr><th>#</th><th>Quiz</th><th>Score</th><th>Total</th><th>Result</th><th>Submitted At</th></tr></thead>
                <tbody>
                <c:forEach var="r" items="${results}" varStatus="i">
                    <tr>
                        <td>${i.index + 1}</td>
                        <td>${r.quiz.title}</td>
                        <td>${r.scoreObtained}</td>
                        <td>${r.totalMarks}</td>
                        <td>
                            <c:if test="${r.passed}"><span class="badge badge-approved">Passed</span></c:if>
                            <c:if test="${!r.passed}"><span class="badge badge-rejected">Failed</span></c:if>
                        </td>
                        <td>${r.submittedAt}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="../layout/page-foot.jsp" %>
