<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Announcements" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="card">
    <h3>Publish Announcement</h3>
    <form method="post" action="${pageContext.request.contextPath}/notifications/announcements/save">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group"><label>Title</label>
            <input type="text" name="title" class="form-control" required></div>
        <div class="form-group"><label>Message</label>
            <textarea name="message" class="form-control" rows="3" required></textarea></div>
        <button type="submit" class="btn">Publish</button>
    </form>
</div>
<div class="card">
    <h3>Announcements</h3>
    <table class="data-table">
        <thead><tr><th>#</th><th>Title</th><th>Message</th><th>Created</th><th>Actions</th></tr></thead>
        <tbody>
        <c:forEach var="a" items="${items}" varStatus="i">
            <tr>
                <td>${i.index + 1}</td><td>${a.title}</td><td>${a.message}</td><td>${a.createdAt}</td>
                <td><a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/notifications/announcements/${a.id}/delete"
                       onclick="return confirm('Delete this announcement?');">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="../layout/page-foot.jsp" %>
