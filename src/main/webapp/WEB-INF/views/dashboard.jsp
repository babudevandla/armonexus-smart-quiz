<%@ include file="layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Dashboard" scope="request"/>
<%@ include file="layout/page-head.jsp" %>

<div class="stat-grid">
    <div class="stat-card">
        <div class="stat-value">${totalUsers}</div>
        <div class="stat-label">Total Users</div>
    </div>
    <div class="stat-card">
        <div class="stat-value">${totalQuestions}</div>
        <div class="stat-label">Total Questions</div>
    </div>
    <div class="stat-card">
        <div class="stat-value">${totalQuizzes}</div>
        <div class="stat-label">Total Quizzes</div>
    </div>
    <div class="stat-card">
        <div class="stat-value">${totalAttempts}</div>
        <div class="stat-label">Quiz Attempts</div>
    </div>
</div>

<div class="card">
    <h3>Welcome to the Quiz Admin Portal</h3>
    <p>Use the sidebar to manage users, master data, question bank, quizzes, results, reports, notifications and settings.</p>
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

<%@ include file="layout/page-foot.jsp" %>
