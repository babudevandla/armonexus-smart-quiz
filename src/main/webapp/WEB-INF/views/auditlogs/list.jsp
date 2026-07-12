<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Audit Logs" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="card">
    <h3>Audit Logs</h3>
    <c:choose>
        <c:when test="${empty logs}">
            <p style="color:#6b7280;">No audit log entries yet.</p>
        </c:when>
        <c:otherwise>
            <table class="data-table">
                <thead><tr><th>#</th><th>User</th><th>Action</th><th>Entity</th><th>Details</th><th>Timestamp</th></tr></thead>
                <tbody>
                <c:forEach var="log" items="${logs}" varStatus="i">
                    <tr>
                        <td>${i.index + 1}</td>
                        <td>${log.user != null ? log.user.fullName : '-'}</td>
                        <td>${log.action}</td>
                        <td>${log.entityName} #${log.entityId}</td>
                        <td>${log.details}</td>
                        <td>${log.timestamp}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>
<%@ include file="../layout/page-foot.jsp" %>
