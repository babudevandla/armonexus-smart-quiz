<%@ include file="layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Profile" scope="request"/>
<%@ include file="layout/page-head.jsp" %>

<div class="card" style="max-width:500px;">
    <h3>My Profile</h3>

    <!-- Success Message -->
    <c:if test="${not empty success}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${success}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <!-- Error Message -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <p><strong>Name:</strong> ${user.fullName}</p>
    <p><strong>Email:</strong> ${user.email}</p>
    <p><strong>Phone:</strong> ${user.phone}</p>
    <p><strong>Roles:</strong>
        <c:forEach var="r" items="${user.roles}">
            <span class="badge badge-draft">${r.name}</span>
        </c:forEach>
    </p>
    <!-- Edit Button -->
    <div style="margin-top:20px;">
        <a href="${pageContext.request.contextPath}/profile/edit"
           class="btn btn-primary">
            Edit Profile
        </a>
    </div>
</div>


<%@ include file="layout/page-foot.jsp" %>
