<%@ include file="layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Profile" scope="request"/>
<%@ include file="layout/page-head.jsp" %>

<div class="card" style="max-width:500px;">
    <h3>My Profile</h3>
    <p><strong>Name:</strong> ${user.fullName}</p>
    <p><strong>Email:</strong> ${user.email}</p>
    <p><strong>Phone:</strong> ${user.phone}</p>
    <p><strong>Roles:</strong>
        <c:forEach var="r" items="${user.roles}">
            <span class="badge badge-draft">${r.name}</span>
        </c:forEach>
    </p>
</div>

<%@ include file="layout/page-foot.jsp" %>
