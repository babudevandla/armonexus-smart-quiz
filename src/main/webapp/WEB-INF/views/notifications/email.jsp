<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Email Notifications" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="card">
    <h3>Send Email</h3>
    <form method="post" action="${pageContext.request.contextPath}/notifications/email/send">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group"><label>Recipient Email</label>
            <input type="email" name="recipientEmail" class="form-control" required></div>
        <div class="form-group"><label>Subject</label>
            <input type="text" name="subject" class="form-control" required></div>
        <div class="form-group"><label>Body</label>
            <textarea name="body" class="form-control" rows="3" required></textarea></div>
        <button type="submit" class="btn">Send</button>
    </form>
</div>
<div class="card">
    <h3>Sent Emails</h3>
    <table class="data-table">
        <thead><tr><th>#</th><th>Recipient</th><th>Subject</th><th>Status</th><th>Sent At</th></tr></thead>
        <tbody>
        <c:forEach var="e" items="${items}" varStatus="i">
            <tr><td>${i.index + 1}</td><td>${e.recipientEmail}</td><td>${e.subject}</td><td>${e.status}</td><td>${e.sentAt}</td></tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="../layout/page-foot.jsp" %>
