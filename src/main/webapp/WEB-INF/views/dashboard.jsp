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

<%@ include file="layout/page-foot.jsp" %>
