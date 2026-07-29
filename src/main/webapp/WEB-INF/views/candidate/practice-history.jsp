<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Practice History" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="card">
    <h3>My Practice History</h3>
    <c:choose>
        <c:when test="${empty attempts}">
            <p style="color:#6b7280;">You haven't attempted any practice sets yet.</p>
        </c:when>
        <c:otherwise>
            <table class="data-table">
                <thead><tr><th>#</th><th>Practice Set</th><th>Score</th><th>Attempted At</th></tr></thead>
                <tbody>
                <c:forEach var="a" items="${attempts}" varStatus="i">
                    <tr>
                        <td>${i.index + 1}</td>
                        <td>${a.practiceSet.title}</td>
                        <td><fmt:formatNumber value="${a.scoreObtained}" maxFractionDigits="1"/>%</td>
                        <td>${a.attemptedAt}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="../layout/page-foot.jsp" %>
