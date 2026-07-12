<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Push Notifications" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="card">
    <h3>Send Push Notification</h3>
    <form method="post" action="${pageContext.request.contextPath}/notifications/push/send">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group"><label>Title</label>
            <input type="text" name="title" class="form-control" required></div>
        <div class="form-group"><label>Body</label>
            <textarea name="body" class="form-control" rows="2" required></textarea></div>
        <button type="submit" class="btn">Send</button>
    </form>
</div>
<div class="card">
    <h3>Sent Push Notifications</h3>
    <table class="data-table">
        <thead><tr><th>#</th><th>Title</th><th>Body</th><th>Status</th><th>Sent At</th></tr></thead>
        <tbody>
        <c:forEach var="p" items="${items}" varStatus="i">
            <tr><td>${i.index + 1}</td><td>${p.title}</td><td>${p.body}</td><td>${p.status}</td><td>${p.sentAt}</td></tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="../layout/page-foot.jsp" %>
