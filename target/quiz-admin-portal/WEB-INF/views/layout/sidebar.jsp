<%@ include file="taglibs.jsp" %>
<nav class="sidebar">
    <div class="sidebar-brand">ArmoNexus Quiz </div>
    <ul class="nav-menu">

        <!-- ============ ADMIN MENU ============ -->
        <sec:authorize access="hasAuthority('ROLE_ADMIN')">
            <li><a href="${pageContext.request.contextPath}/dashboard">Dashboard</a></li>

            <li class="nav-group">
                <span class="nav-group-title">User Management</span>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/roles">User Roles</a></li>
                    <li><a href="${pageContext.request.contextPath}/user-groups">User Groups</a></li>
                    <li><a href="${pageContext.request.contextPath}/users">Users</a></li>
                </ul>
            </li>

            <li class="nav-group">
                <span class="nav-group-title">Master Data</span>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/subjects">Subjects</a></li>
                    <li><a href="${pageContext.request.contextPath}/categories">Categories</a></li>
                    <li><a href="${pageContext.request.contextPath}/difficulty-levels">Difficulty Levels</a></li>
                    <li><a href="${pageContext.request.contextPath}/question-types">Question Types</a></li>
                    <li><a href="${pageContext.request.contextPath}/tags">Tags</a></li>
                </ul>
            </li>

            <li class="nav-group">
                <span class="nav-group-title">Question Bank</span>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/questions">Questions</a></li>
                    <li><a href="${pageContext.request.contextPath}/question-attachments">Question Attachments</a></li>
                    <li><a href="${pageContext.request.contextPath}/questions/bulk-upload">Bulk Upload</a></li>
                    <li><a href="${pageContext.request.contextPath}/question-approval">Review / Approval</a></li>
                </ul>
            </li>

            <li class="nav-group">
                <span class="nav-group-title">Practice</span>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/practice-sets">Practice Sets</a></li>
                    <li><a href="${pageContext.request.contextPath}/practice-history">Practice History</a></li>
                </ul>
            </li>

            <li class="nav-group">
                <span class="nav-group-title">Quiz Management</span>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/quizzes">Quizzes</a></li>
                    <li><a href="${pageContext.request.contextPath}/quiz-schedules">Quiz Schedules</a></li>
                    <li><a href="${pageContext.request.contextPath}/quiz-assignments/users">Assign Users</a></li>
                    <li><a href="${pageContext.request.contextPath}/quiz-assignments/groups">Assign Groups</a></li>
                    <li><a href="${pageContext.request.contextPath}/invitations">Invitations</a></li>
                    <li><a href="${pageContext.request.contextPath}/live-monitoring">Live Monitoring</a></li>
                </ul>
            </li>

            <li class="nav-group">
                <span class="nav-group-title">Results</span>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/results">Quiz Results</a></li>
                    <li><a href="${pageContext.request.contextPath}/leaderboard">Leaderboard</a></li>
                    <li><a href="${pageContext.request.contextPath}/certificates">Certificates</a></li>
                    <li><a href="${pageContext.request.contextPath}/export-reports">Export Reports</a></li>
                </ul>
            </li>

            <li class="nav-group">
                <span class="nav-group-title">Reports</span>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/reports/users">User Reports</a></li>
                    <li><a href="${pageContext.request.contextPath}/reports/quizzes">Quiz Reports</a></li>
                    <li><a href="${pageContext.request.contextPath}/reports/questions">Question Reports</a></li>
                    <li><a href="${pageContext.request.contextPath}/reports/performance">Performance Analytics</a></li>
                    <li><a href="${pageContext.request.contextPath}/reports/revenue">Revenue Reports</a></li>
                </ul>
            </li>

            <li class="nav-group">
                <span class="nav-group-title">Notifications</span>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/notifications/sms">SMS</a></li>
                    <li><a href="${pageContext.request.contextPath}/notifications/email">Email</a></li>
                    <li><a href="${pageContext.request.contextPath}/notifications/push">Push Notifications</a></li>
                    <li><a href="${pageContext.request.contextPath}/notifications/announcements">Announcements</a></li>
                </ul>
            </li>

            <li class="nav-group">
                <span class="nav-group-title">Settings</span>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/settings/application">Application Settings</a></li>
                    <li><a href="${pageContext.request.contextPath}/settings/security">Security</a></li>
                    <li><a href="${pageContext.request.contextPath}/settings/otp">OTP Settings</a></li>
                    <li><a href="${pageContext.request.contextPath}/settings/payment-gateway">Payment Gateway</a></li>
                    <li><a href="${pageContext.request.contextPath}/settings/email-configuration">Email Configuration</a></li>
                    <li><a href="${pageContext.request.contextPath}/audit-logs">Audit Logs</a></li>
                </ul>
            </li>
        </sec:authorize>

        <!-- ============ INSTRUCTOR MENU ============ -->
        <sec:authorize access="hasAuthority('ROLE_INSTRUCTOR')">
            <li><a href="${pageContext.request.contextPath}/instructor/dashboard">Dashboard</a></li>
            <li class="nav-group">
                <span class="nav-group-title">Question Bank</span>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/instructor/questions">My Questions</a></li>
                    <li><a href="${pageContext.request.contextPath}/questions/new">+ Add Question</a></li>
                    <li><a href="${pageContext.request.contextPath}/question-attachments">Question Attachments</a></li>
                    <li><a href="${pageContext.request.contextPath}/questions/bulk-upload">Bulk Upload</a></li>
                </ul>
            </li>
            <li class="nav-group">
                <span class="nav-group-title">Quiz Management</span>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/instructor/quizzes">My Quizzes</a></li>
                    <li><a href="${pageContext.request.contextPath}/quizzes/new">+ Create Quiz</a></li>
                </ul>
            </li>
        </sec:authorize>

        <!-- ============ REVIEWER MENU ============ -->
        <sec:authorize access="hasAuthority('ROLE_REVIEWER')">
            <li><a href="${pageContext.request.contextPath}/reviewer/dashboard">Dashboard</a></li>
            <li class="nav-group">
                <span class="nav-group-title">Question Bank</span>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/question-approval">Review / Approval</a></li>
                </ul>
            </li>
        </sec:authorize>

        <!-- ============ STUDENT MENU ============ -->
        <sec:authorize access="hasAuthority('ROLE_STUDENT')">
            <li><a href="${pageContext.request.contextPath}/student/dashboard">Dashboard</a></li>
            <li class="nav-group">
                <span class="nav-group-title">Quizzes</span>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/student/quizzes">Available Quizzes</a></li>
                    <li><a href="${pageContext.request.contextPath}/student/results">My Results</a></li>
                </ul>
            </li>
            <li class="nav-group">
                <span class="nav-group-title">Practice</span>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/student/practice">Practice Sets</a></li>
                    <li><a href="${pageContext.request.contextPath}/student/practice-history">Practice History</a></li>
                </ul>
            </li>
            <li class="nav-group">
                <span class="nav-group-title">Community</span>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/leaderboard">Leaderboard</a></li>
                    <li><a href="${pageContext.request.contextPath}/certificates">Certificates</a></li>
                </ul>
            </li>
        </sec:authorize>

        <li><a href="${pageContext.request.contextPath}/profile">Profile</a></li>
    </ul>
</nav>
