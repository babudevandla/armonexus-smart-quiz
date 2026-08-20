<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Instructor Dashboard" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="stat-grid">
    <div class="stat-card">
        <div class="stat-value">${myQuestionCount}</div>
        <div class="stat-label">My Questions</div>
    </div>
    <div class="stat-card">
        <div class="stat-value">${myApprovedCount}</div>
        <div class="stat-label">Approved</div>
    </div>
    <div class="stat-card">
        <div class="stat-value">${myPendingCount}</div>
        <div class="stat-label">Pending Review</div>
    </div>
    <div class="stat-card">
        <div class="stat-value">${myQuizCount}</div>
        <div class="stat-label">My Quizzes</div>
    </div>
</div>

<div class="card">
    <h3>Welcome, Instructor</h3>
    <p>Create questions and quizzes for your candidates. New questions you submit go to
       <strong>Pending Review</strong> until a reviewer approves them — only approved
       questions can be added to a quiz.</p>
    <div style="display:flex; gap:12px; margin-top:16px;">
        <a class="btn" href="${pageContext.request.contextPath}/questions/new">+ New Question</a>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/instructor/questions">My Questions</a>
        <a class="btn" href="${pageContext.request.contextPath}/quizzes/new">+ New Quiz</a>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/instructor/quizzes">My Quizzes</a>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/questions/bulk-upload">Bulk Upload</a>
    </div>
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
