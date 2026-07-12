<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Performance Analytics" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="stat-grid">
    <div class="stat-card"><div class="stat-value">${totalAttempts}</div><div class="stat-label">Total Attempts</div></div>
    <div class="stat-card"><div class="stat-value">${passedCount}</div><div class="stat-label">Passed</div></div>
    <div class="stat-card"><div class="stat-value"><fmt:formatNumber value="${passRate}" maxFractionDigits="1"/>%</div><div class="stat-label">Pass Rate</div></div>
</div>
<%@ include file="../layout/page-foot.jsp" %>
