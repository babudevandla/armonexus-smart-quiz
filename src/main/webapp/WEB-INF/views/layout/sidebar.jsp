<%@ include file="taglibs.jsp" %>
<nav class="sidebar">
    <div class="sidebar-brand">
        <i class="bi bi-mortarboard-fill"></i>
        <span>Quiz Admin</span>
    </div>

    <div class="sidebar-scroll">
    <ul class="nav-menu" id="sidebarAccordion">

        <!-- ============ ADMIN MENU ============ -->
        <sec:authorize access="hasAuthority('ROLE_ADMIN')">
            <li class="nav-link-item">
                <a class="nav-link-top" href="${pageContext.request.contextPath}/dashboard">
                    <i class="bi bi-speedometer2"></i><span>Dashboard</span>
                </a>
            </li>

            <li class="nav-group">
                <a href="#grp-usermgmt" class="nav-group-title" data-bs-toggle="collapse" aria-expanded="false">
                    <i class="bi bi-people-fill"></i><span>User Management</span>
                    <i class="bi bi-chevron-down chevron"></i>
                </a>
                <div class="collapse" id="grp-usermgmt">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/roles"><i class="bi bi-dot"></i>User Roles</a></li>
                        <li><a href="${pageContext.request.contextPath}/user-groups"><i class="bi bi-dot"></i>User Groups</a></li>
                        <li><a href="${pageContext.request.contextPath}/users"><i class="bi bi-dot"></i>Users</a></li>
                    </ul>
                </div>
            </li>

            <li class="nav-group">
                <a href="#grp-masterdata" class="nav-group-title" data-bs-toggle="collapse" aria-expanded="false">
                    <i class="bi bi-database-fill"></i><span>Master Data</span>
                    <i class="bi bi-chevron-down chevron"></i>
                </a>
                <div class="collapse" id="grp-masterdata">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/subjects"><i class="bi bi-dot"></i>Subjects</a></li>
                        <li><a href="${pageContext.request.contextPath}/categories"><i class="bi bi-dot"></i>Categories</a></li>
                        <li><a href="${pageContext.request.contextPath}/difficulty-levels"><i class="bi bi-dot"></i>Difficulty Levels</a></li>
                        <li><a href="${pageContext.request.contextPath}/question-types"><i class="bi bi-dot"></i>Question Types</a></li>
                        <li><a href="${pageContext.request.contextPath}/tags"><i class="bi bi-dot"></i>Tags</a></li>
                    </ul>
                </div>
            </li>

            <li class="nav-group">
                <a href="#grp-qbank" class="nav-group-title" data-bs-toggle="collapse" aria-expanded="false">
                    <i class="bi bi-journal-text"></i><span>Question Bank</span>
                    <i class="bi bi-chevron-down chevron"></i>
                </a>
                <div class="collapse" id="grp-qbank">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/questions"><i class="bi bi-dot"></i>Questions</a></li>
                        <li><a href="${pageContext.request.contextPath}/question-attachments"><i class="bi bi-dot"></i>Question Attachments</a></li>
                        <li><a href="${pageContext.request.contextPath}/questions/bulk-upload"><i class="bi bi-dot"></i>Bulk Upload</a></li>
                        <li><a href="${pageContext.request.contextPath}/question-approval"><i class="bi bi-dot"></i>Review / Approval</a></li>
                    </ul>
                </div>
            </li>

            <li class="nav-group">
                <a href="#grp-practice" class="nav-group-title" data-bs-toggle="collapse" aria-expanded="false">
                    <i class="bi bi-pencil-square"></i><span>Practice</span>
                    <i class="bi bi-chevron-down chevron"></i>
                </a>
                <div class="collapse" id="grp-practice">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/practice-sets"><i class="bi bi-dot"></i>Practice Sets</a></li>
                        <li><a href="${pageContext.request.contextPath}/practice-history"><i class="bi bi-dot"></i>Practice History</a></li>
                    </ul>
                </div>
            </li>

            <li class="nav-group">
                <a href="#grp-quizmgmt" class="nav-group-title" data-bs-toggle="collapse" aria-expanded="false">
                    <i class="bi bi-card-checklist"></i><span>Quiz Management</span>
                    <i class="bi bi-chevron-down chevron"></i>
                </a>
                <div class="collapse" id="grp-quizmgmt">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/quizzes"><i class="bi bi-dot"></i>Quizzes</a></li>
                        <li><a href="${pageContext.request.contextPath}/quiz-schedules"><i class="bi bi-dot"></i>Quiz Schedules</a></li>
                        <li><a href="${pageContext.request.contextPath}/quiz-assignments/users"><i class="bi bi-dot"></i>Assign Users</a></li>
                        <li><a href="${pageContext.request.contextPath}/quiz-assignments/groups"><i class="bi bi-dot"></i>Assign Groups</a></li>
                        <li><a href="${pageContext.request.contextPath}/invitations"><i class="bi bi-dot"></i>Invitations</a></li>
                        <li><a href="${pageContext.request.contextPath}/live-monitoring"><i class="bi bi-dot"></i>Live Monitoring</a></li>
                    </ul>
                </div>
            </li>

            <li class="nav-group">
                <a href="#grp-results" class="nav-group-title" data-bs-toggle="collapse" aria-expanded="false">
                    <i class="bi bi-bar-chart-fill"></i><span>Results</span>
                    <i class="bi bi-chevron-down chevron"></i>
                </a>
                <div class="collapse" id="grp-results">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/results"><i class="bi bi-dot"></i>Quiz Results</a></li>
                        <li><a href="${pageContext.request.contextPath}/leaderboard"><i class="bi bi-dot"></i>Leaderboard</a></li>
                        <li><a href="${pageContext.request.contextPath}/certificates"><i class="bi bi-dot"></i>Certificates</a></li>
                        <li><a href="${pageContext.request.contextPath}/export-reports"><i class="bi bi-dot"></i>Export Reports</a></li>
                    </ul>
                </div>
            </li>

            <li class="nav-group">
                <a href="#grp-reports" class="nav-group-title" data-bs-toggle="collapse" aria-expanded="false">
                    <i class="bi bi-file-earmark-bar-graph-fill"></i><span>Reports</span>
                    <i class="bi bi-chevron-down chevron"></i>
                </a>
                <div class="collapse" id="grp-reports">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/reports/users"><i class="bi bi-dot"></i>User Reports</a></li>
                        <li><a href="${pageContext.request.contextPath}/reports/quizzes"><i class="bi bi-dot"></i>Quiz Reports</a></li>
                        <li><a href="${pageContext.request.contextPath}/reports/questions"><i class="bi bi-dot"></i>Question Reports</a></li>
                        <li><a href="${pageContext.request.contextPath}/reports/performance"><i class="bi bi-dot"></i>Performance Analytics</a></li>
                        <li><a href="${pageContext.request.contextPath}/reports/revenue"><i class="bi bi-dot"></i>Revenue Reports</a></li>
                    </ul>
                </div>
            </li>

            <li class="nav-group">
                <a href="#grp-notifications" class="nav-group-title" data-bs-toggle="collapse" aria-expanded="false">
                    <i class="bi bi-bell-fill"></i><span>Notifications</span>
                    <i class="bi bi-chevron-down chevron"></i>
                </a>
                <div class="collapse" id="grp-notifications">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/notifications/sms"><i class="bi bi-dot"></i>SMS</a></li>
                        <li><a href="${pageContext.request.contextPath}/notifications/email"><i class="bi bi-dot"></i>Email</a></li>
                        <li><a href="${pageContext.request.contextPath}/notifications/push"><i class="bi bi-dot"></i>Push Notifications</a></li>
                        <li><a href="${pageContext.request.contextPath}/notifications/announcements"><i class="bi bi-dot"></i>Announcements</a></li>
                    </ul>
                </div>
            </li>

            <li class="nav-group">
                <a href="#grp-settings" class="nav-group-title" data-bs-toggle="collapse" aria-expanded="false">
                    <i class="bi bi-gear-fill"></i><span>Settings</span>
                    <i class="bi bi-chevron-down chevron"></i>
                </a>
                <div class="collapse" id="grp-settings">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/settings/application"><i class="bi bi-dot"></i>Application Settings</a></li>
                        <li><a href="${pageContext.request.contextPath}/settings/security"><i class="bi bi-dot"></i>Security</a></li>
                        <li><a href="${pageContext.request.contextPath}/settings/otp"><i class="bi bi-dot"></i>OTP Settings</a></li>
                        <li><a href="${pageContext.request.contextPath}/settings/payment-gateway"><i class="bi bi-dot"></i>Payment Gateway</a></li>
                        <li><a href="${pageContext.request.contextPath}/settings/email-configuration"><i class="bi bi-dot"></i>Email Configuration</a></li>
                        <li><a href="${pageContext.request.contextPath}/audit-logs"><i class="bi bi-dot"></i>Audit Logs</a></li>
                    </ul>
                </div>
            </li>
        </sec:authorize>

        <!-- ============ INSTRUCTOR MENU ============ -->
        <sec:authorize access="hasAuthority('ROLE_INSTRUCTOR')">
            <li class="nav-link-item">
                <a class="nav-link-top" href="${pageContext.request.contextPath}/instructor/dashboard">
                    <i class="bi bi-speedometer2"></i><span>Dashboard</span>
                </a>
            </li>
            <li class="nav-group">
                <a href="#grp-i-qbank" class="nav-group-title" data-bs-toggle="collapse" aria-expanded="false">
                    <i class="bi bi-journal-text"></i><span>Question Bank</span>
                    <i class="bi bi-chevron-down chevron"></i>
                </a>
                <div class="collapse" id="grp-i-qbank">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/instructor/questions"><i class="bi bi-dot"></i>My Questions</a></li>
                        <li><a href="${pageContext.request.contextPath}/questions/new"><i class="bi bi-plus-circle"></i>Add Question</a></li>
                        <li><a href="${pageContext.request.contextPath}/question-attachments"><i class="bi bi-dot"></i>Question Attachments</a></li>
                        <li><a href="${pageContext.request.contextPath}/questions/bulk-upload"><i class="bi bi-dot"></i>Bulk Upload</a></li>
                    </ul>
                </div>
            </li>
            <li class="nav-group">
                <a href="#grp-i-quizmgmt" class="nav-group-title" data-bs-toggle="collapse" aria-expanded="false">
                    <i class="bi bi-card-checklist"></i><span>Quiz Management</span>
                    <i class="bi bi-chevron-down chevron"></i>
                </a>
                <div class="collapse" id="grp-i-quizmgmt">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/instructor/quizzes"><i class="bi bi-dot"></i>My Quizzes</a></li>
                        <li><a href="${pageContext.request.contextPath}/quizzes/new"><i class="bi bi-plus-circle"></i>Create Quiz</a></li>
                    </ul>
                </div>
            </li>
        </sec:authorize>

        <!-- ============ REVIEWER MENU ============ -->
        <sec:authorize access="hasAuthority('ROLE_REVIEWER')">
            <li class="nav-link-item">
                <a class="nav-link-top" href="${pageContext.request.contextPath}/reviewer/dashboard">
                    <i class="bi bi-speedometer2"></i><span>Dashboard</span>
                </a>
            </li>
            <li class="nav-group">
                <a href="#grp-r-qbank" class="nav-group-title" data-bs-toggle="collapse" aria-expanded="false">
                    <i class="bi bi-journal-text"></i><span>Question Bank</span>
                    <i class="bi bi-chevron-down chevron"></i>
                </a>
                <div class="collapse" id="grp-r-qbank">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/question-approval"><i class="bi bi-dot"></i>Review / Approval</a></li>
                    </ul>
                </div>
            </li>
        </sec:authorize>

        <!-- ============ CANDIDATE MENU ============ -->
        <sec:authorize access="hasAuthority('ROLE_CANDIDATE')">
            <li class="nav-link-item">
                <a class="nav-link-top" href="${pageContext.request.contextPath}/candidate/dashboard">
                    <i class="bi bi-speedometer2"></i><span>Dashboard</span>
                </a>
            </li>
            <li class="nav-group">
                <a href="#grp-s-quizzes" class="nav-group-title" data-bs-toggle="collapse" aria-expanded="false">
                    <i class="bi bi-card-checklist"></i><span>Quizzes</span>
                    <i class="bi bi-chevron-down chevron"></i>
                </a>
                <div class="collapse" id="grp-s-quizzes">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/candidate/quizzes"><i class="bi bi-dot"></i>Available Quizzes</a></li>
                        <li><a href="${pageContext.request.contextPath}/candidate/results"><i class="bi bi-dot"></i>My Results</a></li>
                    </ul>
                </div>
            </li>
            <li class="nav-group">
                <a href="#grp-s-practice" class="nav-group-title" data-bs-toggle="collapse" aria-expanded="false">
                    <i class="bi bi-pencil-square"></i><span>Practice</span>
                    <i class="bi bi-chevron-down chevron"></i>
                </a>
                <div class="collapse" id="grp-s-practice">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/candidate/practice"><i class="bi bi-dot"></i>Practice Sets</a></li>
                        <li><a href="${pageContext.request.contextPath}/candidate/practice-history"><i class="bi bi-dot"></i>Practice History</a></li>
                    </ul>
                </div>
            </li>
            <li class="nav-group">
                <a href="#grp-s-community" class="nav-group-title" data-bs-toggle="collapse" aria-expanded="false">
                    <i class="bi bi-trophy-fill"></i><span>Community</span>
                    <i class="bi bi-chevron-down chevron"></i>
                </a>
                <div class="collapse" id="grp-s-community">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/leaderboard"><i class="bi bi-dot"></i>Leaderboard</a></li>
                        <li><a href="${pageContext.request.contextPath}/certificates"><i class="bi bi-dot"></i>Certificates</a></li>
                    </ul>
                </div>
            </li>
        </sec:authorize>

        <li class="nav-link-item">
            <a class="nav-link-top" href="${pageContext.request.contextPath}/profile">
                <i class="bi bi-person-badge-fill"></i><span>Profile</span>
            </a>
        </li>
    </ul>
    </div>
</nav>
