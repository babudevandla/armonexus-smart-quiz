<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="SMS Notifications" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="card">
    <h3>Send SMS</h3>
    <form method="post" action="${pageContext.request.contextPath}/notifications/sms/send">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group" style="display:flex; gap:12px; align-items:flex-end;">
            <div style="flex:1;"><label>Recipient Phone</label>
                <input type="text" name="recipientPhone" class="form-control" required></div>
            <div style="flex:2;"><label>Message</label>
                <input type="text" name="message" class="form-control" required></div>
            <div><button type="submit" class="btn">Send</button></div>
        </div>
    </form>
</div>
<div class="card">
    <h3>Sent Messages</h3>
    <table class="data-table">
        <thead><tr><th>#</th><th>Phone</th><th>Message</th><th>Status</th><th>Sent At</th></tr></thead>
        <tbody>
        <c:forEach var="s" items="${items}" varStatus="i">
            <tr><td>${i.index + 1}</td><td>${s.recipientPhone}</td><td>${s.message}</td><td>${s.status}</td><td>${s.sentAt}</td></tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="../layout/page-foot.jsp" %>
