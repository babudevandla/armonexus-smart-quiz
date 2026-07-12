<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="User Reports" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="card">
    <h3>User Report — Total Users: ${totalUsers}</h3>
    <table class="data-table">
        <thead><tr><th>#</th><th>Name</th><th>Email</th><th>Roles</th><th>Status</th></tr></thead>
        <tbody>
        <c:forEach var="u" items="${users}" varStatus="i">
            <tr>
                <td>${i.index + 1}</td><td>${u.fullName}</td><td>${u.email}</td>
                <td><c:forEach var="r" items="${u.roles}">${r.name} </c:forEach></td>
                <td>${u.enabled ? 'Active' : 'Disabled'}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="../layout/page-foot.jsp" %>
