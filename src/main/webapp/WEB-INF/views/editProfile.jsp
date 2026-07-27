<%@ include file="layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Edit Profile" scope="request"/>
<%@ include file="layout/page-head.jsp" %>

<form action="${pageContext.request.contextPath}/profile/update" method="post">
    <input type="hidden"
           name="${_csrf.parameterName}"
           value="${_csrf.token}" />
    <div style="margin-bottom:15px;">
        <label><strong>Name:</strong></label><br>
        <input type="text" name="fullName" value="${user.fullName}" class="form-control"/>
    </div>

    <div style="margin-bottom:15px;">
        <label><strong>Email:</strong></label><br>
        <input type="email" name="email" value="${user.email}" class="form-control" readonly/>
    </div>

    <div style="margin-bottom:15px;">
        <label><strong>Phone:</strong></label><br>
        <input type="tel" name="phone" value="${user.phone}" class="form-control" maxlength="10" pattern="[0-9]{10}" inputmode="numeric" required oninput="this.value=this.value.replace(/[^0-9]/g,'').slice(0,10);"/>






    </div>

    <div style="margin-bottom:15px;">
        <label><strong>Roles:</strong></label><br>
        <c:forEach var="r" items="${user.roles}">
            <input type="text" value="${r.name}" class="form-control" readonly/>
        </c:forEach>
    </div>

    <button type="submit" class="btn btn-primary">
        Update Profile
    </button>

</form>

<%@ include file="layout/page-foot.jsp" %>