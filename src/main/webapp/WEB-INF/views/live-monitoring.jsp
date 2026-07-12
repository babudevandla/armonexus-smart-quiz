<%@ include file="layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Live Monitoring" scope="request"/>
<%@ include file="layout/page-head.jsp" %>

<div class="card">
    <div style="display:flex; justify-content:space-between; align-items:center;">
        <h3 style="margin:0;">Quiz Attempts In Progress</h3>
        <span style="color:#6b7280; font-size:13px;">Auto-refreshes every 10s</span>
    </div>
    <c:choose>
        <c:when test="${empty inProgressAttempts}">
            <p style="color:#6b7280;">No quiz attempts are currently in progress.</p>
        </c:when>
        <c:otherwise>
            <table class="data-table">
                <thead><tr><th>#</th><th>User</th><th>Quiz</th><th>Started At</th><th>Elapsed</th></tr></thead>
                <tbody>
                <c:forEach var="r" items="${inProgressAttempts}" varStatus="i">
                    <tr>
                        <td>${i.index + 1}</td>
                        <td>${r.user.fullName}</td>
                        <td>${r.quiz.title}</td>
                        <td>${r.startedAt}</td>
                        <td class="elapsed-cell" data-started="${r.startedAt}">—</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
    <p style="color:#9ca3af; font-size:13px; margin-top:12px;">
        A row appears here the moment a student opens a quiz (an "in progress" record is written
        with no submission time yet) and disappears the moment they submit. Left-open/abandoned
        attempts will stay listed here indefinitely in this reference build — add a cutoff based on
        <code>startedAt + quiz.durationMinutes</code> if you want stale attempts to auto-expire from this view.
    </p>
</div>

<script>
    // Simple page auto-refresh so this behaves like a live view without
    // needing WebSockets/AJAX polling infrastructure.
    setTimeout(function () { window.location.reload(); }, 10000);

    // Client-side "elapsed" ticker for a nicer at-a-glance view.
    document.querySelectorAll('.elapsed-cell').forEach(function (cell) {
        var started = new Date(cell.getAttribute('data-started'));
        if (isNaN(started.getTime())) return;
        var seconds = Math.floor((Date.now() - started.getTime()) / 1000);
        var m = Math.floor(seconds / 60);
        var s = seconds % 60;
        cell.textContent = m + "m " + s + "s ago";
    });
</script>

<%@ include file="layout/page-foot.jsp" %>
