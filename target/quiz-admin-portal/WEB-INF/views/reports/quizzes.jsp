<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Quiz Reports" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="card">
    <h3>Quiz Report</h3>
    <table class="data-table">
        <thead><tr><th>#</th><th>Quiz</th><th>Attempts</th><th>Pass Rate</th></tr></thead>
        <tbody>
        <c:forEach var="q" items="${quizzes}" varStatus="i">
            <c:set var="attemptCount" value="0"/>
            <c:set var="passCount" value="0"/>
            <c:forEach var="r" items="${allResults}">
                <c:if test="${r.quiz.id == q.id}">
                    <c:set var="attemptCount" value="${attemptCount + 1}"/>
                    <c:if test="${r.passed}"><c:set var="passCount" value="${passCount + 1}"/></c:if>
                </c:if>
            </c:forEach>
            <tr>
                <td>${i.index + 1}</td>
                <td>${q.title}</td>
                <td>${attemptCount}</td>
                <td>${attemptCount > 0 ? (passCount * 100 / attemptCount) : 0}%</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="../layout/page-foot.jsp" %>
