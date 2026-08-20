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

<%@ include file="../layout/page-foot.jsp" %>
