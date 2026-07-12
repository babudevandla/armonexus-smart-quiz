<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Reviewer Dashboard" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="stat-grid">
    <div class="stat-card">
        <div class="stat-value">${pendingCount}</div>
        <div class="stat-label">Pending Review</div>
    </div>
    <div class="stat-card">
        <div class="stat-value">${approvedCount}</div>
        <div class="stat-label">Approved</div>
    </div>
    <div class="stat-card">
        <div class="stat-value">${rejectedCount}</div>
        <div class="stat-label">Rejected</div>
    </div>
</div>

<div class="card">
    <h3>Welcome, Reviewer</h3>
    <p>Questions submitted by instructors wait here for your approval before they can be
       added to a quiz. Review the question text, options, and marked correct answer,
       then approve or reject with optional comments.</p>
    <a class="btn" href="${pageContext.request.contextPath}/question-approval">Go to Pending Review Queue</a>
</div>

<%@ include file="../layout/page-foot.jsp" %>
