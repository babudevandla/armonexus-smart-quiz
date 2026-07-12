<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Question Reports" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="stat-grid">
    <div class="stat-card"><div class="stat-value">${totalQuestions}</div><div class="stat-label">Total Questions</div></div>
    <div class="stat-card"><div class="stat-value">${approved}</div><div class="stat-label">Approved</div></div>
    <div class="stat-card"><div class="stat-value">${pending}</div><div class="stat-label">Pending</div></div>
    <div class="stat-card"><div class="stat-value">${rejected}</div><div class="stat-label">Rejected</div></div>
</div>
<%@ include file="../layout/page-foot.jsp" %>
