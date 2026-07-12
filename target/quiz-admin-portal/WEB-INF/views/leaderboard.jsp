<%@ include file="layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Leaderboard" scope="request"/>
<%@ include file="layout/page-head.jsp" %>

<div class="card">
    <h3>Leaderboard</h3>
    <table class="data-table">
        <thead><tr><th>Rank</th><th>User</th><th>Quiz</th><th>Score</th><th>Total</th></tr></thead>
        <tbody>
        <c:forEach var="r" items="${results}" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${r.user.fullName}</td>
                <td>${r.quiz.title}</td>
                <td>${r.scoreObtained}</td>
                <td>${r.totalMarks}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="layout/page-foot.jsp" %>
