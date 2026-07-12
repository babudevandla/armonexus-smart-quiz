<%@ include file="layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Dashboard" scope="request"/>
<%@ include file="layout/page-head.jsp" %>

<!-- Existing 4 stats -->
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

<!-- NEW ENHANCED STATS SECTION -->
<div class="stat-grid">
    <div class="stat-card">
        <div class="stat-value">${activeUsers}</div>
        <div class="stat-label">Active Users</div>
    </div>
    <div class="stat-card">
        <div class="stat-value">${pendingQuestions}</div>
        <div class="stat-label">Pending Questions</div>
    </div>
    <div class="stat-card">
        <div class="stat-value">${inProgressAttempts}</div>
        <div class="stat-label">In-Progress Attempts</div>
    </div>
    <div class="stat-card">
        <div class="stat-value">${instructorCount}</div>
        <div class="stat-label">Instructors</div>
    </div>
</div>

<!-- ADDITIONAL STATS ROW -->
<div class="stat-grid">
    <div class="stat-card">
        <div class="stat-value">${studentCount}</div>
        <div class="stat-label">Students</div>
    </div>
    <div class="stat-card">
        <div class="stat-value">${activePracticeSets}</div>
        <div class="stat-label">Practice Sets</div>
    </div>
    <div class="stat-card">
        <div class="stat-value">${quizAssignments}</div>
        <div class="stat-label">Quiz Assignments</div>
    </div>
    <div class="stat-card">
        <div class="stat-value">${totalUsers - activeUsers}</div>
        <div class="stat-label">Inactive Users</div>
    </div>
</div>

<div class="card">
    <h3>Welcome to the ArmoNexus Quiz Admin Portal</h3>
    <p>Use the sidebar to manage users, master data, question bank, quizzes, results, reports, notifications and settings.</p>
</div>

<%@ include file="layout/page-foot.jsp" %>
